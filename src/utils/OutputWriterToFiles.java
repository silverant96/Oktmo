package utils;

import models.Place;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OutputWriterToFiles {
    public static void outputAllPlacesToFile(ArrayList<Place> outputPlaces, String fileName) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileName))) {
            for (Place place : outputPlaces) {
                out.print(place);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void outputAllPlacesNamesToFile(ArrayList<Place> outputPlaces, String fileName) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileName))) {
            for (Place place : outputPlaces) {
                out.println(place.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void outputAllPlacesStatusToFile(ArrayList<Place> outputPlaces, String fileName) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileName))) {
            for (Place place : outputPlaces) {
                out.println(place.getStatus());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
