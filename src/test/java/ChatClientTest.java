import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatClientTest {

    @org.junit.jupiter.api.Test
    public void testConcat_ChatClient_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> {
            new ChatClient(null, null, null);
        });
    }

    @org.junit.jupiter.api.Test
    public void testConcat_ChatServer_validType_success() {
        final Integer PORT = 0;
        final String HOSTNAME = "localhost";
        final String NAME = "name";
        ChatClient chat = new ChatClient(HOSTNAME, PORT, NAME);
        Assertions.assertEquals(chat.getClass(), (new ChatClient()).getClass());

    }

}
