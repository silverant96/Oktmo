import database.OktmoData;
import utils.OktmoAnalyzer;
import utils.OktmoReader;
import utils.OutputWriterToFiles;

public class OktmoMain {
    public static void main(String[] args) {
        OktmoData data = new OktmoData();

        //utils.OktmoReader.readPlacesOldVersion("data-201710.csv", data);
        OktmoReader.readPlaces("data-201710.csv", data);


        data.sortPlacesByName();

        OktmoAnalyzer.removeUnknownStatusPlaces(data.getPlaces());

        data.outputToFileAllStatuses();
        data.outputToFileSortedPlacesByName();

        OutputWriterToFiles.outputAllPlacesNamesToFile(OktmoAnalyzer.findPlacesSixOVO(data.getPlaces()),
                "findPlacesSixOVO.txt");
        OutputWriterToFiles.outputAllPlacesNamesToFile(OktmoAnalyzer.findPlacesByFirstAndLastEqualsChar(data.getPlaces()),
                "findPlacesByFirstAndLastEqualsChar.txt");
        OutputWriterToFiles.outputAllPlacesStatusToFile(data.getPlaces(),
                "outputStatus.txt");
        OutputWriterToFiles.outputAllPlacesToFile(data.getPlaces(),
                "allplaces.txt");

    }

}
