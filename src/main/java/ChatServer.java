import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends Thread {

    private String hostname;
    private int port;
    private int connectors;

    public ChatServer(String hostname, Integer port) {
        if ((hostname == null) || (port == null)) throw new NullPointerException();
        this.hostname = hostname;
        this.port = port;
        connectors = 0;
    }

    public ChatServer() {

    }

    @Override
    public void run() {
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> lines = new ArrayList<>();
        List<Boolean> mark = new ArrayList<>();
        mark.add(true);
        Path file = Paths.get("src/main/resources/chat.txt");
        List<Thread> threads = new ArrayList<>();
        ArrayList<ArrayList<String>> newMessagesList = new ArrayList<ArrayList<String>>();
        System.out.println("Сервер запущен");
        while (true) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mark.get(0)) {
                this.connectors++;
                String name = "Con" + this.connectors;
                ArrayList<String> newMessages = new ArrayList<>();
                newMessagesList.add(newMessages);
                Thread connector1 = new Thread(null, new Connector(serverChannel, lines, name, mark, newMessages));
                connector1.start();
                mark.set(0, false);
            }
            if (!lines.isEmpty()) {
                try {
                    Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String newGlobalMess : lines) {
                    for (ArrayList<String> newMess : newMessagesList) {
                        newMess.add(newGlobalMess);
                    }
                }
                lines.clear();
            }
        }

    }

}