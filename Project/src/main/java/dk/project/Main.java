// Package
package dk.project;

// Imports
import dk.project.server.Server;

public class Main {

    // Attributes

    // _____________________________________________________

    public static void main(String[] args) {

        Server server = new Server();
        server.start(7000);

    }

} // Main end