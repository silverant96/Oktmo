public class OktmoMain {
    public static void main(String[] args) {
        OktmoData data = new OktmoData();

        //OktmoReader.readPlacesOldVersion("data-201710.csv", data);
        OktmoReader.readPlaces("data-201710.csv", data);


        data.sortPlacesByName();

        OktmoAnalyzer.removeUnknownStatusPlaces(data.places);

        data.outputToFileAllStatuses();
        data.outputToFileSortedPlacesByName();

        OutputWriterToFiles.outputAllPlacesNamesToFile(OktmoAnalyzer.findPlacesSixOVO(data.places),
                "findPlacesSixOVO.txt");
        OutputWriterToFiles.outputAllPlacesNamesToFile(OktmoAnalyzer.findPlacesByFirstAndLastEqualsChar(data.places),
                "findPlacesByFirstAndLastEqualsChar.txt");
        OutputWriterToFiles.outputAllPlacesStatusToFile(data.places,
                "outputStatus.txt");
        OutputWriterToFiles.outputAllPlacesToFile(data.places,
                "allplaces.txt");

    }

}
