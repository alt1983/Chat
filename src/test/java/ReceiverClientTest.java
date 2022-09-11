import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReceiverClientTest {

    @org.junit.jupiter.api.Test
    public void testConcat_ReceiverClient_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> {
            new ReceiverClient(null, null, null, null);
        });
    }

    @org.junit.jupiter.api.Test
    public void testConcat_Receiver_validType_success() throws IOException {
        final SocketChannel socketChannel = SocketChannel.open();
        final List<String> message = new ArrayList<>();
        final List<Boolean> mark = new ArrayList<>();
        final String name = "name";
        ReceiverClient receiverClient = new ReceiverClient(message, name, socketChannel, mark);
        Assertions.assertEquals(receiverClient.getClass(), (new ReceiverClient()).getClass());
    }

}
