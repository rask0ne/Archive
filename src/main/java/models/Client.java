package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by rask on 15.04.2017.
 */
public class Client {

    Socket toServer;
    ObjectOutputStream serializer;
    ObjectInputStream deserializer;

    public Object sendToServer(Object o) throws IOException, ClassNotFoundException {

        toServer = new Socket("",8000);
        serializer = new ObjectOutputStream(toServer.getOutputStream());

        serializer.writeObject(o);
        serializer.flush();
        deserializer = new ObjectInputStream(toServer.getInputStream());
        Object object = deserializer.readObject();
        return object;
    }

    public void getFromServer() throws IOException, ClassNotFoundException {

        //toServer = new Socket("",8000);


    }
}
