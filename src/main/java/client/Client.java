package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by rask on 15.04.2017.
 */
public class Client {

    public void sendToServer(Object o) throws IOException {
        Socket toServer;
        toServer = new Socket("",8000);
        ObjectOutputStream serializer = new ObjectOutputStream(toServer.getOutputStream());
        serializer.writeObject(o);
        serializer.flush();
    }

    public Object getFromServer() throws IOException, ClassNotFoundException {
        Socket toServer;
        toServer = new Socket("",8000);
        ObjectInputStream deserializer = new ObjectInputStream(toServer.getInputStream());
        Object object = (deserializer.readObject());
        return object;
    }
}
