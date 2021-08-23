package database;

import models.Place;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * database.OktmoData будет хранить всю считанную информацию (списки) и содержать методы обращения к ним
 */
public class OktmoData {
    private ArrayList<Place> places = new ArrayList<>();
    private TreeSet<String> allStatuses = new TreeSet<>();
    private ArrayList<Place> sortedPlaces = new ArrayList<>();

    public OktmoData() {
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public TreeSet<String> getAllStatuses() {
        return allStatuses;
    }

    public void setAllStatuses(TreeSet<String> allStatuses) {
        this.allStatuses = allStatuses;
    }

    public ArrayList<Place> getSortedPlaces() {
        return sortedPlaces;
    }

    public void setSortedPlaces(ArrayList<Place> sortedPlaces) {
        this.sortedPlaces = sortedPlaces;
    }

    public void addPlace(Place place) {
        places.add(place);
        allStatuses.add(place.getStatus());
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
        return "database.OktmoData {" +
                "places=\n " + places +
                '}';
    }

    public int getSizeOfArrayOfPlaces() {
        return places.size();
    }
}
