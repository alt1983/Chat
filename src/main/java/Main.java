import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> configServer = Files.readAllLines(Paths.get("src/main/resources/config.txt"), UTF_8);
        final String HOSTNAME_SERVER = configServer.get(0);
        final int PORT_SERVER = Integer.parseInt(configServer.get(1));
        final Thread server = new Thread(null, new ChatServer(HOSTNAME_SERVER, PORT_SERVER));
        server.start();
        List<String> configClient = Files.readAllLines(Paths.get("src/main/resources/configClient.txt"), UTF_8);
        final String HOSTNAME_CLIENT = configClient.get(0);
        final int PORT_CLIENT = Integer.parseInt(configClient.get(1));
        String name = "";
        System.out.println("Введите имя для чата:");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine() + ":";
        final Thread client = new Thread(null, new ChatClient(HOSTNAME_CLIENT, PORT_CLIENT, name));
        client.start();

    }

}
