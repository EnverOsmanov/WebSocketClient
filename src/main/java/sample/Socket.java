package sample;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class Socket {
    private final String MESSAGE = "hello";
    private final CountDownLatch closeLatch;

    @SuppressWarnings("unused")
    private Session session;

    public Socket() {
        this.closeLatch = new CountDownLatch(1);
    }

    //На время держит соединение открытым
    public void awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        try {
            session.getRemote().sendString(MESSAGE);
            session.getRemote().flush();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println(message);
    }
}
