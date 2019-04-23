package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    @FXML
    TextArea textArea;
    @FXML
    private Button disconnect;
    @FXML
    private Button connect;
    private Socket socket;
    private ConnectServer connectServer;
    private Thread thread;

    public void connect()
    {
        try {
            socket = new Socket("127.0.0.1", 4242);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectServer=new ConnectServer(socket,textArea);
        thread=new Thread(connectServer);
        thread.start();
        connect.setDisable(true);
        disconnect.setDisable(false);


    }
    public void disconnect() throws IOException {
        thread.stop();
        disconnect.setDisable(true);
        connect.setDisable(false);
     socket.close();


    }



}
