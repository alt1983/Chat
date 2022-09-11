import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatServerTest {

    @org.junit.jupiter.api.Test
    public void testConcat_ChatServer_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> {
            new ChatServer(null, null);
        });
    }

    @org.junit.jupiter.api.Test
    public void testConcat_ChatServer_validType_success() {
        final Integer PORT = 0;
        final String HOSTNAME = "localhost";
        ChatServer chat = new ChatServer(HOSTNAME, PORT);
        Assertions.assertEquals(chat.getClass(), (new ChatServer()).getClass());

    }

}
