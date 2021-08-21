public class OktmoMain {
    public static void main(String[] args) {
        OktmoData data = new OktmoData();

        OktmoReader.readPlaces("data-201710.csv", data);

        data.sortPlacesByName();

        data.outputToFileAllStatuses();
        data.outputToFileSortedPlacesByName();
    }

}
