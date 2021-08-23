import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestReadPlaces {

    @Test
    public void firstTest() {

        // Old reader
        OktmoData dataOld = new OktmoData();

        long startTimeOldRead = System.nanoTime();
        dataOld = OktmoReader.readPlacesOldVersion("data-201710.csv", dataOld);
        long stopTimeOldRead = System.nanoTime();

        Assertions.assertEquals((Long)dataOld.places.get(9).code, (Long)160140210192L);
        Assertions.assertEquals(dataOld.places.get(9).name, "Алейский");
        Assertions.assertEquals(dataOld.places.get(9).status, "Поселок");

        int lastIndexOfArrayOfPlaces = dataOld.getSizeOfArrayOfPlaces() - 1;

        Assertions.assertEquals((Long)dataOld.places.get(lastIndexOfArrayOfPlaces).code, (Long)9970100000192L);
        Assertions.assertEquals(dataOld.places.get(lastIndexOfArrayOfPlaces).name, "Биробиджан");
        Assertions.assertEquals(dataOld.places.get(lastIndexOfArrayOfPlaces).status, "Город");

        // New reader
        OktmoData dataNew = new OktmoData();

        long startTimeNewRead = System.nanoTime();
        dataNew = OktmoReader.readPlaces("data-201710.csv", dataNew);
        long stopTimeNewRead = System.nanoTime();

        Assertions.assertEquals((Long)dataNew.places.get(9).code, (Long)160140210192L);
        Assertions.assertEquals(dataNew.places.get(9).name, "Алейский");
        Assertions.assertEquals(dataNew.places.get(9).status, "Поселок");

        int lastIndexOfArrayOfPlacesNew = dataNew.getSizeOfArrayOfPlaces() - 1;

        Assertions.assertEquals((Long)dataNew.places.get(lastIndexOfArrayOfPlacesNew).code, (Long)9970100000192L);
        Assertions.assertEquals(dataNew.places.get(lastIndexOfArrayOfPlacesNew).name, "Биробиджан");
        Assertions.assertEquals(dataNew.places.get(lastIndexOfArrayOfPlacesNew).status, "Город");

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
            if (!firstOD.places.get(i).equals(secondOD.places.get(i))) {
                System.out.println("Не равны! Со строки №" + (i + 1));
                break;
            }
        }
    }
}
