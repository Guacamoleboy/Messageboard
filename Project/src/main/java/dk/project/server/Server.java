// Package
package dk.project.server;

// Imports
import io.javalin.Javalin;
import java.util.Map;

public class Server {

    // Attributes
    private Javalin app;

    // _____________________________________________________

    public void start(int port) {

        // Resource folder
        app = Javalin.create(config -> {
            config.staticFiles.add("/static"); // folder i resources/static
        }).start(port);

        // Login
        app.get("/", ctx -> {
            String html = ThymeleafSetup.render("login.html", null);
            ctx.html(html);
        });

        app.error(404, ctx -> {
            String html = ThymeleafSetup.render("404.html", null);
            ctx.html(html);
        });

        // Handles errors directly to 404.html

        System.out.println("http://localhost:" + port + " | I din URL bby girl");
    }

    // _____________________________________________________

    public void stop() {
        if (app != null) {
            app.stop();
        }
    }

} // Server End