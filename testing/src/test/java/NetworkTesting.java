import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sonicxd2.socket.client.SocketClient;
import ru.sonicxd2.socket.server.SocketServer;

import java.io.IOException;
import java.util.Arrays;

public class NetworkTesting extends Assert {
    @Test
    public void createConnect() {
        SocketServer server = new SocketServer(1000, (t, u, v) -> {}, (t,u,v) -> null);
        new Thread(() -> {
            try {
                server.bind();
            } catch (IOException e) {
                Assert.fail(e.getMessage());
                e.printStackTrace();
            }
        }).start();
        SocketClient client = new SocketClient("127.0.0.1", 1000);
        try {
            client.connect();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
        client.closeConnect();
        server.closeServer();
    }

    @Test
    public void sendTestData() {
        SocketServer server = new SocketServer(1000, (t, u, v) -> {
            System.out.println(Arrays.toString(t));
            System.out.println(Arrays.toString(u));
            System.out.println(Arrays.toString(v));
        }, (t, u, v) -> new byte[]{0});
        new Thread(() -> {
            try {
                server.bind();
            } catch (IOException e) {
                Assert.fail(e.getMessage());
                e.printStackTrace();
            }
        }).start();
        SocketClient client = new SocketClient("127.0.0.1", 1000);
        try {
            client.connect();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
        try {
            System.out.println(Arrays.toString(client.sendDataAndGetAnswer(new byte[]{0, 1, 0, 1}, new byte[]{0, 2, 0, 1}, new byte[]{0, 0, 0, 23})));
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

        client.closeConnect();
        server.closeServer();
    }
}
