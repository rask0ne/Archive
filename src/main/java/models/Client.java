package models;

import design.Register;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by rask on 15.04.2017.
 */

/**
 * Model of client in client-server app
 */
public class Client {

    private final Logger logger = Logger.getLogger(Register.class);

    Socket toServer;
    ObjectOutputStream serializer;
    ObjectInputStream deserializer;

    /**
     * Sending object to server and waiting for its response
     * @param o - object
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object sendToServer(Object o) throws IOException, ClassNotFoundException {

        toServer = new Socket("",8000);
        serializer = new ObjectOutputStream(toServer.getOutputStream());
        serializer.writeObject(o);
        serializer.flush();
        logger.info("Object sent to server");

        deserializer = new ObjectInputStream(toServer.getInputStream());
        Object object = deserializer.readObject();
        logger.info("Got object from server");
        return object;
    }

}
