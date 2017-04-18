package XMLDAO;

import XMLDAO.Compress.Compressor;
import XMLDAO.Parsers.Parserable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLBEGIN;
import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLEND;
import static XMLDAO.XMLConstants.XMLPARSERCONST.XMLNONE;


/**
 * Created by rask on 15.04.2017.
 * The class for doing logic operations with XML file and Person
 */
public class XMLEditor {

    private Parserable parser;

    private String path;
    private String archivePath;

    private static final String prefix = "temp";

    /**
     * Creating XML Editor for XML file with this path, uses this parser and save result into this archive
     * @param path the path of the XML file for store and editing File
     * @param parser the Object of parser for reading XML file
     * @param archivePath the path of archive for zipping XML file
     */
    public XMLEditor(String path, Parserable parser, String archivePath) {
        this.path = path;
        this.parser = parser;
        this.archivePath = archivePath;
    }

    /**
     * adding this Person into this position in XML file
     * @param person the Person, that was insert into XML file
     * @param index the index of position to insert this Person
     */
    public void add(Person person, int index) {
        Person people[] = parser.parseFromXML(path);
        if (people != null)
        {
            if (index < 0 || index > people.length){
                return;
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(path, false);
            fileWriter.write(Person.parseToXML(null, XMLBEGIN));
            if (people != null && index >= 0 && index <= people.length) {
                for (int i = 0; i < index; i++) {
                    fileWriter.append(people[i].parseToXML(XMLNONE));
                }
            }
            fileWriter.append(person.parseToXML(XMLNONE));
            if (people != null && index >= 0 && index < people.length) {
                for (int i = index; i < people.length; i++) {
                    fileWriter.append(people[i].parseToXML(XMLNONE));
                }
            }
            fileWriter.append(Person.parseToXML(null, XMLEND));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * adding this Person in begin of the XML file
     * @param person the Person, that was been adding in the Begin of the XML file
     */
    public void addInBegin(Person person) {
        this.add(person, 0);
    }

    /**
     * adding this Person in the end of the XML file
     * @param person the Person, that was been adding in the end of the XML file
     */
    public void addInEnd(Person person){
        if (parser.parseFromXML(path) != null){
            this.add(person, parser.parseFromXML(path).length);
        }
        else
        {
            this.add(person,0);
        }
    }

    /**
     * deleting Person in XML file on the this position
     * @param index the index of Person for deleting
     * @return true - if deleting ending successfully
     *        false - if deleting don't ending successfully
     */
    public boolean delete(int index) {
        try {
            Person people[] = parser.parseFromXML(path);
            if (people != null && index >= 0 && index < people.length) {
                FileWriter fileWriter = new FileWriter(prefix + path, false);
                fileWriter.write(Person.parseToXML(null, XMLBEGIN));
                if (index < 0 || index >= people.length) {
                    return false;
                }
                for (int i = 0; i < index; i++) {
                    fileWriter.append(people[i].parseToXML(XMLNONE));
                }
                for (int i = index + 1; i < people.length; i++) {
                    fileWriter.append(people[i].parseToXML(XMLNONE));
                }
                fileWriter.append(Person.parseToXML(null, XMLEND));
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
                if (people == null){
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

    /**
     * change Person on this index in file with this Person
     * @param person the new Person for insert in File
     * @param index the index in file of Old Person
     */
    public void edit(Person person, int index){
        if ( this.delete(index)) {
            this.add(person, index);
        }
    }

    /**
     * getting Person from file on this index
     * @param index the index of Person, that was getting
     * @return the Person on this position in File
     */
    public Person get(int index){
        return parser.parseFromXML(path, index);
    }

    /**
     * getting arrayList from XML file
     * @return arrayList of all Users in XML file
     */
    public ArrayList<Person> getList(){
        ArrayList<Person> list = new ArrayList<Person>();
        Person person;
        int index = 0;
        do {
            person = parser.parseFromXML(path,index++);
            if (person != null) {
                list.add(person);
            }
        } while (person != null);
        return list;
    }

    /**
     * find in the XML file user with this name
     * @param name the name for find
     * @return the index of founded User
     *    -1 - if user can't founded in XML file
     */
    public int findIndexAsName(String name){
        Person person;
        int index = 0;
        do {
            person = parser.parseFromXML(path,index);
            if (person != null){
                if (person.getName().equals(name)){
                    return index;
                }
            }
            index++;
        }while (person != null);
        return -1;
    }

    /**
     * check for validation this XML file with this XML schema
     * @param schemaPath the path of the XML schema for checking
     * @return true - if this XML file validate with this XML schema
     *        false - if this XML file not validate with this XML schema
     */
    public boolean validate(String schemaPath){
        try {
            return XMLSchemaValidator.checkXMLforXSD(path,schemaPath);
        }
        catch (FileNotFoundException e){
            return false;
        }
    }

    /**
     * the count of Users into this XML File
     * @return count of Users into XML File
     */
    public int length(){
        Person[] people = parser.parseFromXML(path);
        if (people != null){
            return people.length;
        }
        else {
            return 0;
        }
    }

    /**
     * transform into String all Users from XMl file
     * @return the result of transform
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        Person[] people = parser.parseFromXML(path);
        if (people != null) {
            for (Person person : parser.parseFromXML(path)) {
                if (person != null) {
                    s.append(person.toString());
                }
            }
        }
        return s.toString();
    }

    /**
     * zipping XML file with this capacity of zipping
     * @param capacity capacity of zipping in Bounds: [0,9]. Other -> default capacity
     */
    public void compress(int capacity){
        Compressor compressor = new Compressor();
        if (archivePath != null) {
            compressor.compress(path, archivePath, capacity);
        }
    }

    /**
     * unzipping zip archive with XML file. IFf don't zipped file with this name is Exist, that this file was REMOVED!
     */
    public void uncompress(){
        Compressor compressor = new Compressor();
        if (archivePath != null) {
            compressor.decompress(archivePath);
        }
    }

    /**
     * getting the current path of the XML file
     * @return the current path of the XML file
     */
    public String getPath() {
        return path;
    }

    /**
     * setting the current path of the XML file
     * @param path the current path of the XML file
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * setting the new parser of the XML file
     * @param  parser new parser of the XML file
     */
    public void setParser(Parserable parser) {
        this.parser = parser;
    }

    /**
     * getting path of zip archive of this XML file
     * @return zip path of the XML file
     */
    public String getArchivePath() {
        return archivePath;
    }

    /**
     * setting path of zip archive of this XML file
     * @param archivePath zip path of the XML file
     */
    public void setArchivePath(String archivePath) {
        this.archivePath = archivePath;
    }
}
