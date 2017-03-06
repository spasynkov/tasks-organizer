package net.ukrtel.ddns.ff.organizer.exceptions;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException() {
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
