import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestReadPlaces {

    @Test
    public void firstTest() {
        OktmoData data = new OktmoData();

        data = OktmoReader.readPlaces("data-201710.csv", data);

        Assertions.assertEquals((Long)data.places.get(9).code, (Long)160140210192L);
        Assertions.assertEquals(data.places.get(9).name, "Алейский");
        Assertions.assertEquals(data.places.get(9).status, "Поселок");

        int lastIndexOfArrayOfPlaces = data.getSizeOfArrayOfPlaces() - 1;

        Assertions.assertEquals((Long)data.places.get(lastIndexOfArrayOfPlaces).code, (Long)9970100000192L);
        Assertions.assertEquals(data.places.get(lastIndexOfArrayOfPlaces).name, "Биробиджан");
        Assertions.assertEquals(data.places.get(lastIndexOfArrayOfPlaces).status, "Город");
    }
}
