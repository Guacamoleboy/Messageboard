// Package
package dk.project;

// Imports
import dk.project.server.Server;
import java.sql.SQLException;

public class Main {

    // Attributes

    // _____________________________________________________

    public static void main(String[] args) {

        Server server = new Server();

        // To fix SQL Exception bug
        try {
            server.start(7000);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

} // Main end