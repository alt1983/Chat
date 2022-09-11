import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Receiver extends Thread {

    private String name;
    private SocketChannel socketChannel;
    private List<String> lines;

    public Receiver(String name, SocketChannel socketChannel, List<String> lines) {
        if ((socketChannel == null) || (lines == null) || (name == null)) throw new NullPointerException();
        this.name = name;
        this.socketChannel = socketChannel;
        this.lines = lines;
    }

    public Receiver() {

    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            while (true) {
                int bytesCount = socketChannel.read(inputBuffer);
                if (bytesCount == -1) break;
                String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                inputBuffer.clear();
                lines.add(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

