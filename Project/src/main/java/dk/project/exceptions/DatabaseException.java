// Package
package dk.project.exceptions;

// Imports

public class DatabaseException extends Exception {

    // Attributes

    // _________________________________________________________

    public DatabaseException(String userMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
    }

    // _________________________________________________________

    public DatabaseException(String userMessage, String systemMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
        System.out.println("errorMessage: " + systemMessage);
    }

} // DatabaseException End