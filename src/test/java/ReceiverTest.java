import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReceiverTest {

    @org.junit.jupiter.api.Test
    public void testConcat_Receiver_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> {
            new Receiver(null, null, null);
        });
    }

    @org.junit.jupiter.api.Test
    public void testConcat_Receiver_validType_success() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();;
        final List<String> lines = new ArrayList<>();
        final String name = "name";
        Receiver receiver = new Receiver(name, socketChannel, lines);
        Assertions.assertEquals(receiver.getClass(), (new Receiver()).getClass());
    }

}
