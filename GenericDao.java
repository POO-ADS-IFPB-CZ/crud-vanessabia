import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vanessa
 */
public class GenericDao<T> {
    private final String filename;

    public GenericDao(String filename) {
        this.filename = filename;
    }

    public void save(List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
