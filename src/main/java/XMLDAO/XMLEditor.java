package XMLDAO;

import XMLDAO.Parsers.Parserable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLBEGIN;
import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLEND;
import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLNONE;


/**
 * Created by Alexey on 15.04.2017.
 */
public class XMLEditor {

    private Parserable parser;

    private String path;
    private static final String prefix = "temp";

    public XMLEditor(String path, Parserable parser) {
        this.path = path;
        this.parser = parser;
    }

    public void add(User user, int index) {
        User users[] = parser.parseFromXML(path);
        if (users != null)
        {
            if (index < 0 || index > users.length){
                return;
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(path, false);
            fileWriter.write(User.parseToXML(null, XMLBEGIN));
            if (users != null && index >= 0 && index <= users.length) {
                for (int i = 0; i < index; i++) {
                    fileWriter.append(users[i].parseToXML(XMLNONE));
                }
            }
            fileWriter.append(user.parseToXML(XMLNONE));
            if (users != null && index >= 0 && index < users.length) {
                for (int i = index; i < users.length; i++) {
                    fileWriter.append(users[i].parseToXML(XMLNONE));
                }
            }
            fileWriter.append(User.parseToXML(null, XMLEND));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addInBegin(User user) {
        this.add(user, 0);
    }

    public void addInEnd(User user){
        if (parser.parseFromXML(path) != null){
            this.add(user, parser.parseFromXML(path).length);
        }
        else
        {
            this.add(user,0);
        }
    }

    public boolean delete(int index) {
        try {
            User users[] = parser.parseFromXML(path);
            if (users != null && index >= 0 && index < users.length) {
                FileWriter fileWriter = new FileWriter(prefix + path, false);
                fileWriter.write(User.parseToXML(null, XMLBEGIN));
                if (index < 0 || index >= users.length) {
                    return false;
                }
                for (int i = 0; i < index; i++) {
                    fileWriter.append(users[i].parseToXML(XMLNONE));
                }
                for (int i = index + 1; i < users.length; i++) {
                    fileWriter.append(users[i].parseToXML(XMLNONE));
                }
                fileWriter.append(User.parseToXML(null, XMLEND));
                fileWriter.flush();
                fileWriter.close();

                File file = new File(prefix + path);
                File oldFile = new File(path);
                if (oldFile.delete()) {
                    if (!file.renameTo(oldFile)){
                        System.out.println("Can't rename");
                    }
                }
                else {
                    System.out.println("Can't delete");
                }
                return true;
            }
            else {
                if (users == null){
                    File file = new File(path);
                    file.delete();
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void edit(User user, int index){
        if ( this.delete(index)) {
            this.add(user, index);
        }
    }

    public User get(int index){
        return parser.parseFromXML(path, index);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        User[] users = parser.parseFromXML(path);
        if (users != null) {
            for (User user : parser.parseFromXML(path)) {
                if (user != null) {
                    s.append(user.toString());
                }
            }
        }
        return s.toString();
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setParser(Parserable parser) {
        this.parser = parser;
    }
}
