package net.ukrtel.ddns.ff.organizer.exceptions;

public class NoTaskFoundException extends RuntimeException {
    public NoTaskFoundException() {
    }

    public NoTaskFoundException(String message) {
        super(message);
    }
}
