package utils;

import models.Place;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 2.3.	utils.OktmoAnalyzer просматривает объекты в database.OktmoData, и проводит анализ.
 */
public class OktmoAnalyzer {

    /**
     * <b>ЗАДАЧА:</b>
     * С помощью регулярных выражений найдите все НП, название которых содержит меньше 6 букв и заканчиваются на -ово
     */
    public static ArrayList<Place> findPlacesSixOVO (ArrayList<Place> inputPlaces) {
        return searchWordByRegex(inputPlaces, "[А-Яа-яЁё].+ово", 6, false);
    }

    /**
     * <b>ЗАДАЧА:</b>
     * Найдите населённые пункты, с названиями, которые начинаются и заканчиваются на одну и ту же согласную букву
     */
    public static ArrayList<Place> findPlacesByFirstAndLastEqualsChar (ArrayList<Place> inputPlaces) {
        ArrayList<Place> foundPlaces = new ArrayList<>();
        for (int i = 1040; i < 1072; i++) {
            foundPlaces.addAll(searchWordByRegex(inputPlaces,
                    "^" + (char)i + "\\W+" + (char)(i + 32) + "$", 0, true));
            if (i == 1045) {
                foundPlaces.addAll(searchWordByRegex(inputPlaces,
                        "^" + (char)1025 + "\\W+" + (char)1105 + "$", 0, true));
            }
        }
        return foundPlaces;
    }

    public static ArrayList<Place> removeUnknownStatusPlaces (ArrayList<Place> inputPlaces) {
        inputPlaces.removeIf(place -> Objects.equals(place.getStatus(), "Неизвестный тип"));

        return inputPlaces;
    }

    /**
     * Поиск слов с помощью регулярных выражений
     * @param inputPlaces входящий списка мест
     * @param regex регулярное выражение
     * @param byLength поиск по длине слова. (если 0, то поиск не производится)
     *                 (Пример: если введено цифра 6, то поиск будет производиться до 6 не включительно)
     * @param caseSensitive включить или выключить чувствительность к регистру.
     * @return список найденных мест по регулярному выражению
     */
    private static ArrayList<Place> searchWordByRegex(ArrayList<Place> inputPlaces, String regex, long byLength,
                                                      boolean caseSensitive) {
        Pattern pattern;
        if (caseSensitive) {
            pattern = Pattern.compile(regex);
        } else {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        ArrayList<Place> foundPlaces = new ArrayList<>();

        for (Place currentPlace : inputPlaces) {
            if (byLength == 0) {
                if (pattern.matcher(currentPlace.getName()).find()) {
                    foundPlaces.add(currentPlace);
                }
            } else {
                if (pattern.matcher(currentPlace.getName()).find() && currentPlace.getName().length() < byLength) {
                    foundPlaces.add(currentPlace);
                }
            }

        }

        return foundPlaces;
    }
}
