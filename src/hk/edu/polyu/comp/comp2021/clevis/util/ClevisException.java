package hk.edu.polyu.comp.comp2021.clevis.util;

/**
 * Custom checked exception for Clevis to handle expected error conditions
 * such as invalid commands, undefined names, or name conflicts.
 */
public class ClevisException extends Exception {
    /**
     * Constructs a ClevisException with the specified detail message.
     * @param message The detail message.
     */
    public ClevisException(String message) {
        super(message);
    }

    /**
     * Constructs a ClevisException with the specified detail message and cause.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public ClevisException(String message, Throwable cause) {
        super(message, cause);
    }
}
