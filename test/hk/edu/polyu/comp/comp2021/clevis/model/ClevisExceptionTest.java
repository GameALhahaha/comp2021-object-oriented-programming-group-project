package hk.edu.polyu.comp.comp2021.clevis.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClevisExceptionTest {

    @Test
    public void testMessageConstructor() {
        ClevisException ex = new ClevisException("msg");
        assertEquals("msg", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("cause");
        ClevisException ex = new ClevisException("msg", cause);
        assertEquals("msg", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}