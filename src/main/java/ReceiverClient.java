import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReceiverClient extends Thread {

    private List<String> message;
    private String name;
    private List<Boolean> mark;
    private SocketChannel socketChannel;

    public ReceiverClient(List<String> message, String name, SocketChannel socketChannel, List<Boolean> mark) {
        if ((socketChannel == null) || (mark == null) || (message == null) || (name == null))
            throw new NullPointerException();
        this.message = message;
        this.name = name;
        this.socketChannel = socketChannel;
        this.mark = mark;
    }

    public ReceiverClient() {

    }

    private String getTime() {
        return (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(new Date());
    }

    @Override
    public void run() {
        Path file = Paths.get("src/main/resources/chatClient.txt");
        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String msg = getTime() + " " + this.name + "вошел в чат";
            Files.write(file, new ArrayList<>(Arrays.asList(msg)), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            while (true) {
                msg = scanner.nextLine();
                if ("/exit".equals(msg)) {
                    this.mark.set(0, false);
                    msg = getTime() + " " + this.name + "ушел";
                    Files.write(file, new ArrayList<>(Arrays.asList(msg)), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    socketChannel.write(
                            ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                    break;
                }
                msg = getTime() + " " + this.name + msg;
                Files.write(file, new ArrayList<>(Arrays.asList(msg)), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                socketChannel.write(
                        ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

