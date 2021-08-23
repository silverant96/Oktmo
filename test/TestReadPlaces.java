import database.OktmoData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.OktmoReader;

public class TestReadPlaces {

    @Test
    public void testReadCSVByOldAlg() {

        // Old reader
        OktmoData dataOld = new OktmoData();

        OktmoReader.readPlacesOldVersion("data-201710.csv", dataOld);

        Assertions.assertEquals((Long)dataOld.getPlaces().get(9).getCode(), (Long)160140210192L);
        Assertions.assertEquals(dataOld.getPlaces().get(9).getName(), "Алейский");
        Assertions.assertEquals(dataOld.getPlaces().get(9).getStatus(), "Поселок");

        int lastIndexOfArrayOfPlaces = dataOld.getSizeOfArrayOfPlaces() - 1;

        Assertions.assertEquals((Long)dataOld.getPlaces().get(lastIndexOfArrayOfPlaces).getCode(), (Long)9970100000192L);
        Assertions.assertEquals(dataOld.getPlaces().get(lastIndexOfArrayOfPlaces).getName(), "Биробиджан");
        Assertions.assertEquals(dataOld.getPlaces().get(lastIndexOfArrayOfPlaces).getStatus(), "Город");
    }

    @Test
    public void testReadCSVByNewAlg() {
        // New reader
        OktmoData dataNew = new OktmoData();

        OktmoReader.readPlaces("data-201710.csv", dataNew);

        Assertions.assertEquals((Long)dataNew.getPlaces().get(9).getCode(), (Long)160140210192L);
        Assertions.assertEquals(dataNew.getPlaces().get(9).getName(), "Алейский");
        Assertions.assertEquals(dataNew.getPlaces().get(9).getStatus(), "Поселок");

        int lastIndexOfArrayOfPlacesNew = dataNew.getSizeOfArrayOfPlaces() - 1;

        Assertions.assertEquals((Long)dataNew.getPlaces().get(lastIndexOfArrayOfPlacesNew).getCode(), (Long)9970100000192L);
        Assertions.assertEquals(dataNew.getPlaces().get(lastIndexOfArrayOfPlacesNew).getName(), "Биробиджан");
        Assertions.assertEquals(dataNew.getPlaces().get(lastIndexOfArrayOfPlacesNew).getStatus(), "Город");
    }

    @Test
    public void testCompare() {
        OktmoData dataOld = new OktmoData();

        long startTimeOldRead = System.nanoTime();
        OktmoReader.readPlacesOldVersion("data-201710.csv", dataOld);
        long stopTimeOldRead = System.nanoTime();

        OktmoData dataNew = new OktmoData();

        long startTimeNewRead = System.nanoTime();
        OktmoReader.readPlaces("data-201710.csv", dataNew);
        long stopTimeNewRead = System.nanoTime();

        System.out.println("Время затраченное на старый алгоритм чтения - "
                + (stopTimeOldRead - startTimeOldRead) + " наносекунд");
        System.out.println("Время затраченное на новый алгоритм чтения  - "
                + (stopTimeNewRead - startTimeNewRead) + " наносекунд");

        if ((stopTimeNewRead - startTimeNewRead) < (stopTimeOldRead - startTimeOldRead)) {
            System.out.println("Новый алгоритм выиграл по времени");
        } else {
            if ((stopTimeNewRead - startTimeNewRead) > (stopTimeOldRead - startTimeOldRead)){
                System.out.println("Старый алгоритм выиграл по времени");
            } else {
                System.out.println("Скорость алгоритмов равна");
            }
        }

        int sizeOldArray = dataOld.getSizeOfArrayOfPlaces();
        int sizeNewArray = dataNew.getSizeOfArrayOfPlaces();

        if (sizeOldArray > sizeNewArray) {
            System.out.println("В старом алгоритме больше элементов" +
                    "\nстарый - " + sizeOldArray +
                    "\nновый - " + sizeNewArray);
        } else {
            if (sizeOldArray < sizeNewArray) {
                System.out.println("В новом алгоритме больше элементов" +
                        "\nстарый - " + sizeOldArray +
                        "\nновый - " + sizeNewArray);
            } else {
                System.out.println("УРА! У АЛГОРИТОМ ОДИНАКОВОЕ КОЛИЧЕСТВО ЭЛЕМЕНТОВ!!!" +
                        "\nстарый - " + sizeOldArray +
                        "\nновый - " + sizeNewArray);
            }
        }

        comparisonTwoOktmoDataObject(dataOld, dataNew);
        Assertions.assertEquals(dataOld, dataNew);
    }

    private void comparisonTwoOktmoDataObject(OktmoData firstOD, OktmoData secondOD) {
        long minimumElements = Math.min(firstOD.getSizeOfArrayOfPlaces(), secondOD.getSizeOfArrayOfPlaces());

        for (int i = 0; i < minimumElements; i++) {
            if (!firstOD.getPlaces().get(i).equals(secondOD.getPlaces().get(i))) {
                System.out.println("Не равны! Со строки №" + (i + 1));
                break;
            }
        }
    }
}
