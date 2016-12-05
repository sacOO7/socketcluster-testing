import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import io.github.sac.BasicListener;
import io.github.sac.ReconnectStrategy;
import io.github.sac.Socket;

import java.util.List;
import java.util.Map;

public class Main {

    public static String url="http://192.168.0.18:8000/socketcluster/";

    public static void main(String[] args) {
//        System.out.println("Hello World!");
        Socket socket = new Socket(url);

        socket.setListener(new BasicListener() {

            public void onConnected(Map<String, List<String>> headers) {
                System.out.println("Connected to endpoint");
            }

            public void onDisconnected(WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
                System.out.println("Disconnected from end-point");
            }

            public void onConnectError(WebSocketException exception) {
                System.out.println("Got connect error "+ exception);
            }

            public void onSetAuthToken(String token, Socket socket) {
                socket.setAuthToken(token);
            }

            public void onAuthentication(Boolean status) {
                if (status) {
                    System.out.println("Socket is authenticated");
                } else {
                    System.out.println("Authentication is required (optional)");
                }
            }

        });

        socket.setReconnection(new ReconnectStrategy().setMaxAttempts(30)); //Connect after each 2 seconds for 30 times

        socket.connect();

    }
}
