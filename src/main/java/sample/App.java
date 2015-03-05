package sample;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

//Соединяется с сервером и получает одно сообщение (ждёт 60 секунд на всякий случай).
public class App {
    private static final String URL = "ws://localhost/api/v1/ws";

    public static void main(String[] args) {
        WebSocketClient client = new WebSocketClient();
        Socket socket = new Socket();

        try {
            client.start();
            URI echoUri = new URI(URL);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            socket.awaitClose(60, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
