package ru.spbau.mit;

/**
 * Created by ivan on 17.05.17.
 */

public class ImplementorException extends Exception {
    public ImplementorException(final String message) {
        super(message);
    }

    public ImplementorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}