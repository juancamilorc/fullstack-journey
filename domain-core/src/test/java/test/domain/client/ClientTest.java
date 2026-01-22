package domain.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void constructor_shouldThrow_whenIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Client("   ", "Juan", "123"));
        assertThrows(IllegalArgumentException.class, () -> new Client("", "Juan", "123"));
    }

    @Test
    void constructor_shouldThrow_whenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Client("1", "   ", "123"));
        assertThrows(IllegalArgumentException.class, () -> new Client("1", "", "123"));
    }

    @Test
    void constructor_shouldThrow_whenDocumentIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Client("1", "Juan", "   "));
        assertThrows(IllegalArgumentException.class, () -> new Client("1", "Juan", ""));
    }

    @Test
    void constructor_shouldTrimFields() {
        Client client = new Client("  1  ", "  Juan  ", "  123  ");

        assertEquals("1", client.getId());
        assertEquals("Juan", client.getName());
        assertEquals("123", client.getDocument());
    }
}
