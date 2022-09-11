import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatClient extends Thread {

    private String hostname;
    private int port;
    private String name;

    public ChatClient(String hostname, Integer port, String name) {
        if ((hostname == null) || (port == null) || (name == null)) throw new NullPointerException();
        this.hostname = hostname;
        this.port = port;
        this.name = name;
    }

    public ChatClient() {

    }

    @Override
    public void run() {

        InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> message = new ArrayList<>();
        List<Boolean> mark = new ArrayList<>();
        mark.add(true);
        Thread receiver = new Thread(null, new ReceiverClient(message, this.name, socketChannel, mark));
        receiver.start();
        try {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            while (mark.get(0)) {
                int bytesCount2 = socketChannel.read(inputBuffer);
                System.out.println("" + (new String(inputBuffer.array(), 0, bytesCount2, StandardCharsets.UTF_8)));
                inputBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
