package net.ukrtel.ddns.ff.organizer.exceptions;

/**
 * Throws when attempting to operate some actions on a task with not appropriate state
 */
public class IllegalTaskStateException extends Exception {
    public IllegalTaskStateException() {
    }

    public IllegalTaskStateException(String message) {
        super(message);
    }
}
