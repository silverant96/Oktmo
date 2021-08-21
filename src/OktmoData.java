import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * OktmoData будет хранить всю считанную информацию (списки) и содержать методы обращения к ним
 */
public class OktmoData {
    ArrayList<Place> places = new ArrayList<>();
    TreeSet<String> allStatuses = new TreeSet<>();
    ArrayList<Place> sortedPlaces = new ArrayList<>();

    public OktmoData() {
    }

    public void addPlace(Place place) {
        places.add(place);
        allStatuses.add(place.status);
    }

    public void printAllStatuses() {
        System.out.println(allStatuses);
    }

    public void sortPlacesByName() {
        sortedPlaces.addAll(places);
        Collections.sort(sortedPlaces);
    }

    public void printSortPlacesByName() {
        System.out.println(sortedPlaces);
    }

    public void outputToFileSortedPlacesByName() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("sortedplacesbyname.txt"))) {
            for (Place place : sortedPlaces) {
                out.print(place);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void outputToFileAllStatuses() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("allstatuses.txt"))) {
            for (String status : allStatuses) {
                out.println(status);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "OktmoData {" +
                "places=\n " + places +
                '}';
    }

    public int getSizeOfArrayOfPlaces() {
        return places.size();
    }
}
