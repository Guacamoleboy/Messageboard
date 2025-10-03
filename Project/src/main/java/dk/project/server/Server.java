// Package
package dk.project.server;

// Imports
import io.javalin.Javalin;
import dk.project.User;
import dk.project.mappers.UserMapper;
import dk.project.db.Database;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

        // Login POST
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

        // Index / posts page
        app.get("/index", ctx -> {
            User currentUser = ctx.sessionAttribute("currentUser");
            if (currentUser == null) {
                ctx.redirect("/");
            } else {
                Map<String, Object> model = new HashMap<>();
                model.put("user", currentUser);
                String html = ThymeleafSetup.render("index.html", model);
                ctx.html(html);
            }
        });

        // __________________________________________________

        // Register POST
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

        // Pages
        app.get("/post", ctx -> ctx.html(ThymeleafSetup.render("post.html", null)));
        app.get("/new", ctx -> ctx.html(ThymeleafSetup.render("create-post.html", null)));

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