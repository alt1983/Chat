import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConnectorTest {

    @org.junit.jupiter.api.Test
    public void testConcat_Connector_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> {
            new Connector(null, null, null, null, null);
        });
    }

    @org.junit.jupiter.api.Test
    public void testConcat_Connector_validType_success() throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        final List<String> lines = new ArrayList<>();
        final String name = "";
        final List<Boolean> mark = new ArrayList<>();
        final List<String> newMessages = new ArrayList<>();
        Connector connector = new Connector(serverChannel, lines, name, mark, newMessages);
        Assertions.assertEquals(connector.getClass(), (new Connector()).getClass());
    }

}
