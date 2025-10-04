// Package
package dk.project.server;

// Imports
import dk.project.Comment;
import dk.project.Post;
import dk.project.mappers.CommentMapper;
import dk.project.mappers.PostMapper;
import dk.project.mappers.PostVoteMapper;
import io.javalin.Javalin;
import dk.project.User;
import dk.project.mappers.UserMapper;
import dk.project.db.Database;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Server {

    // Attributes
    private Javalin app;

    // __________________________________________________

    public void start(int port) throws SQLException {

        // Initial instantiates
        Connection connection = Database.getConnection();
        UserMapper userMapper = new UserMapper(connection);

        // __________________________________________________

        // Resource folder
        app = Javalin.create(config -> {
            config.staticFiles.add("/static"); // folder i resources/static
        }).start(port);

        // __________________________________________________

        // Login page
        app.get("/", ctx -> {
            String html = ThymeleafSetup.render("login.html", null);
            ctx.html(html);
        });

        // __________________________________________________

        app.post("/", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            if (username == null || password == null) {
                ctx.status(400).result("Alle felter skal udfyldes!");
                return;
            }

            User user = userMapper.getUserByUsername(username);

            if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
                ctx.status(401).result("Forkert brugernavn eller password!");
                return;
            }

            // Session
            ctx.sessionAttribute("currentUser", user);

            // Redirect if pass
            ctx.redirect("/index");
        });

        // __________________________________________________

        // Register page
        app.get("/register", ctx -> {
            String html = ThymeleafSetup.render("register.html", null);
            ctx.html(html);
        });

        // __________________________________________________

        app.get("/index", ctx -> {
            User currentUser = ctx.sessionAttribute("currentUser");

            if (currentUser == null) {
                ctx.redirect("/");
            } else {
                Map<String, Object> model = new HashMap<>();

                PostMapper postMapper = new PostMapper(connection);
                CommentMapper commentMapper = new CommentMapper(connection);
                PostVoteMapper postVoteMapper = new PostVoteMapper(connection);

                List<Post> posts = postMapper.getAllPosts();

                for (Post post : posts) {
                    int commentCount = commentMapper.getCommentsForPost(post.getId()).size();
                    int upvotes = postVoteMapper.getUpvotes(post.getId());
                    int downvotes = postVoteMapper.getDownvotes(post.getId());

                    post.setCommentCount(commentCount);
                    post.setUpvotes(upvotes);
                    post.setDownvotes(downvotes);
                }

                model.put("posts", posts);
                model.put("user", currentUser);

                String html = ThymeleafSetup.render("index.html", model);
                ctx.html(html);
            }

        });

        // __________________________________________________

        app.post("/register", ctx -> {
            String username = ctx.formParam("username");
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            if (username == null || email == null || password == null) {
                ctx.status(400).result("Alle felter skal udfyldes!");
                return;
            }

            User existingUser = userMapper.getUserByUsername(username);
            if (existingUser != null) {
                ctx.status(409).result("Brugernavn er allerede taget!");
                return;
            }

            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User(0, username, email, passwordHash, LocalDateTime.now());
            userMapper.insertUser(user);

            ctx.redirect("/");

        });

        // __________________________________________________

        app.post("/new", ctx -> {

            User currentUser = ctx.sessionAttribute("currentUser");
            if (currentUser == null) {
                ctx.status(401).result("Du skal være logget ind!");
                return;
            }

            String title = ctx.formParam("title");
            String description = ctx.formParam("description");

            if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
                ctx.status(400).result("Titel og beskrivelse skal udfyldes!");
                return;
            }

            PostMapper postMapper = new PostMapper(connection);
            Post post = new Post(0, currentUser, title, description, java.time.LocalDateTime.now());

            int postId = postMapper.insertPost(post);

            if(postId > 0) {
                ctx.redirect("/post/" + postId);
            } else {
                ctx.status(500).result("Kunne ikke oprette posten.");
            }
        });

        // __________________________________________________

        app.get("/post/{id}", ctx -> {

            User currentUser = ctx.sessionAttribute("currentUser");
            if (currentUser == null) {
                ctx.redirect("/");
                return;
            }

            int postId = Integer.parseInt(ctx.pathParam("id"));

            PostMapper postMapper = new PostMapper(connection);
            CommentMapper commentMapper = new CommentMapper(connection);
            PostVoteMapper postVoteMapper = new PostVoteMapper(connection);

            Post post = postMapper.getPostById(postId);
            if (post == null) {
                ctx.status(404).result("Post ikke fundet");
                return;
            }

            List<Comment> comments = commentMapper.getCommentsForPost(postId);
            post.setComments(comments);

            post.setUpvotes(postVoteMapper.getUpvotes(postId));
            post.setDownvotes(postVoteMapper.getDownvotes(postId));

            post.setCommentCount(comments.size());

            Map<String, Object> model = new HashMap<>();
            model.put("post", post);
            model.put("user", currentUser);

            String html = ThymeleafSetup.render("post.html", model);
            ctx.html(html);
        });

        // __________________________________________________

        app.post("/post/{id}", ctx -> {

            int postId = Integer.parseInt(ctx.pathParam("id"));
            String content = ctx.formParam("content");

            User user = ctx.sessionAttribute("currentUser");
            if (user == null) {
                ctx.status(401).result("Du skal være logget ind!");
                return;
            }

            CommentMapper commentMapper = new CommentMapper(connection);
            Comment comment = new Comment(0, postId, user, content, LocalDateTime.now(), 0, 0);
            commentMapper.insertComment(comment);

            ctx.redirect(ctx.path());
        });

        // __________________________________________________

        app.get("/search", ctx -> {

            User currentUser = ctx.sessionAttribute("currentUser");
            if (currentUser == null) {
                ctx.redirect("/");
                return;
            }

            String query = Optional.ofNullable(ctx.queryParam("query")).orElse("").trim();

            Map<String, Object> model = new HashMap<>();
            model.put("user", currentUser);
            model.put("query", query);

            if (!query.isEmpty()) {
                PostMapper postMapper = new PostMapper(connection);
                List<Post> posts = postMapper.searchPostsByTitle(query);

                // Tilføj ekstra info til hver post
                PostVoteMapper postVoteMapper = new PostVoteMapper(connection);
                CommentMapper commentMapper = new CommentMapper(connection);

                for (Post post : posts) {
                    post.setUpvotes(postVoteMapper.getUpvotes(post.getId()));
                    post.setDownvotes(postVoteMapper.getDownvotes(post.getId()));
                    post.setCommentCount(commentMapper.getCommentsForPost(post.getId()).size());
                }

                model.put("posts", posts);
            }

            String html = ThymeleafSetup.render("search.html", model);
            ctx.html(html);

        });

        // __________________________________________________

        // Pages
        app.get("/new", ctx -> ctx.html(ThymeleafSetup.render("create-post.html", null)));
        app.get("/post", ctx -> ctx.html(ThymeleafSetup.render("post.html", null)));

        // Error handle
        app.error(404, ctx -> ctx.html(ThymeleafSetup.render("404.html", null)));

        // System print
        System.out.println("http://localhost:" + port + " | I din URL bby girl");
    }

    // __________________________________________________

    public void stop() {
        if (app != null) {
            app.stop();
        }
    }

} // Server end