package data;

import domain.Restaurant;
import java.io.*;

public class PersistenceManager {
    
    private static final String FILE_PATH = "restaurant_state.dat";

    public void saveRestaurant(final Restaurant restaurant) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(restaurant);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public Restaurant loadRestaurant() {
        final File file = new File(FILE_PATH);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Restaurant) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            return null;
        }
    }
}
