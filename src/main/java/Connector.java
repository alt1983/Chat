import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Connector extends Thread {

    private ServerSocketChannel serverChannel;
    private List<String> lines;
    private List<Boolean> mark;
    private String name;
    private List<String> newMessages;

    public Connector(ServerSocketChannel serverChannel, List<String> lines, String name, List<Boolean> mark, List<String> newMessages) {
        if ((serverChannel == null) || (lines == null) || (mark == null) || (name == null) || (newMessages == null))
            throw new NullPointerException();
        this.serverChannel = serverChannel;
        this.lines = lines;
        this.name = name;
        this.mark = mark;
        this.newMessages = newMessages;
    }

    public Connector() {

    }

    @Override
    public void run() {
        while (true) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                this.mark.set(0, true);
                newMessages.clear();
                Thread receiver = new Thread(null, new Receiver(this.name, socketChannel, lines));
                receiver.start();
                while (socketChannel.isConnected()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String result = "";
                    if (!newMessages.isEmpty()) {
                        for (String el : newMessages) {
                            if (result.equals("")) {
                                result = result + el;
                            } else {
                                result = result + "\n" + el;
                            }
                        }
                        socketChannel.write(ByteBuffer.wrap((result).getBytes(StandardCharsets.UTF_8)));
                        newMessages.clear();
                    }
                }
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }

    }
}
