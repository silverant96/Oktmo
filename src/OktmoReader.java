import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2.2.	OktmoReader считывает текстовые файлы .txt и добавляет их содержимое в разобранном виде в OktmoData
 */
public class OktmoReader {

    public static OktmoData readPlacesOldVersion(String fileName, OktmoData data) {
        int lineCount = 0;

        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileName)
                                , "cp1251")
                        // читаем файл из двоичного потока
                        // в виде текста с нужной кодировкой
                )
        ) {
            String s;
            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                String[] splitString = s.split(";");

                // Convert Array String to Long
                long code;
                StringBuilder tempString = new StringBuilder();

                // Remove "" from String
                for (int i = 0; i < splitString.length; i++) {
                    if (splitString[i].length() > 2) {
                        splitString[i] = splitString[i].substring(1, splitString[i].length() - 1);
                    }
                }

                // Parse long from DB
                for (int i = 0; i < 6; i++) {
                    tempString.append(splitString[i]);
                }
                code = Long.parseLong(tempString.toString());

                // Find last index of name & status;
                int nameIndex = 6;
                if (!splitString[7].isEmpty()) {
                    try {
                        Long.parseLong(splitString[7]);
                    } catch (NumberFormatException ex) {
                        nameIndex = 7;
                    }
                }

                // Convert String to Status and Name
                StringBuilder convertStringForStatus = new StringBuilder();
                StringBuilder convertStringForName = new StringBuilder();

                boolean flagLowerCase = false;
                boolean flagFirstWord = true;

                for (String inputString : splitString[nameIndex].split(" ")) {
                    if (flagFirstWord) {
                        flagFirstWord = false;
                        convertStringForStatus.append(inputString).append(" ");
                    } else {
                        if (!flagLowerCase) {
                            if (Character.isLowerCase(inputString.charAt(0))) {
                                convertStringForStatus.append(inputString).append(" ");
                                flagLowerCase = true;
                            } else {
                                convertStringForName.append(inputString).append(" ");
                            }
                        } else {
                            convertStringForName.append(inputString).append(" ");
                        }
                    }
                }


                if (convertStringForName.toString().isEmpty()) {
                    System.out.println("Это не попадёт в базу (не соответствие формату):\nНомер позиции: "
                            + lineCount + "\nТекст: " + s + "\n");
                } else {
                    // Add to OktmoData object Place;
                    convertStringForName.delete(convertStringForName.length() - 1, convertStringForName.length());
                    data.addPlace(new Place(code, convertStringForName.toString(), convertStringForStatus.toString()));
                }

                lineCount++;
            }
        }

        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }

        return data;
    }

    /**
     * Чтение из файла БД НП с помощью регулярных выражений
     * @param fileName имя файла, с которым мы будем работать
     * @param data объект в который мы будем складывать то, что прочитали
     * @return объект типа OktmoData
     */
    public static OktmoData readPlaces(String fileName, OktmoData data) {
        int lineCount = 0;

        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileName)
                                , "cp1251")
                        // читаем файл из двоичного потока
                        // в виде текста с нужной кодировкой
                )
        ) {
            String s;
            String regexLongNumber = "^\"\\d{2}\";\"\\d{3}\";\"\\d{3}\";\"\\d{3}\";\"\\d\";\"\\d\"";
            String regexName = "\"[A-ZА-Яа-яЁё№\\d,.\\/\\-\\(\\)\\s]+\";;;";
            String regexName2 = "\"[A-ZА-Яа-яЁё№\\d,.\\/\\-\\(\\)\\s]+\";;";
            String regexStatus = "[А-Яа-яЁё.]+\\s";

            Pattern patternLongNumber = Pattern.compile(regexLongNumber);
            Pattern patternName = Pattern.compile(regexName);
            Pattern patternName2 = Pattern.compile(regexName2);
            Pattern patternStatus = Pattern.compile(regexStatus);

            while ((s = br.readLine()) != null ) { // пока readLine() возвращает не null

                // Parsing long number
                Matcher matcherLongNumber = patternLongNumber.matcher(s);
                long code = 0L;
                if (matcherLongNumber.find()) {
                    code = Long.parseLong(RemoveUnnecessaryChar(matcherLongNumber.group()));
                }

                // Parsing name
                String name;
                Matcher matcherName = patternName.matcher(s);
                if (matcherName.find()) {
                    name = RemoveFirstAndLastQuotes(matcherName.group());
                } else {
                    Matcher matcherName2 = patternName2.matcher(s);
                    if (matcherName2.find()) {
                        name = RemoveFirstAndLastQuotes(matcherName2.group());
                    } else {
                        Matcher matcherAllNames = Pattern.compile("[А-Яа-яЁё\\s]+").matcher(s);
                        if (matcherAllNames.find()) {
                            name = RemoveFirstAndLastQuotes(matcherAllNames.group());
                        } else {
                            name = "";
                        }
                    }
                }

                // Parsing status
                String status;
                Matcher matcherStatus = patternStatus.matcher(name);
                if (matcherStatus.find()) {
                    status = matcherStatus.group();
                } else {
                    status = "";
                }

                if (name.isEmpty()) {
                    System.out.println("Это не попадёт в базу (не соответствие формату):\nНомер позиции: "
                            + lineCount + "\nТекст: " + s + "\n");
                } else {
                    data.addPlace(new Place(code, name, status));
                }

                lineCount++;
            }
        }

        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }

        return data;
    }

    /**
     * Удаляет все кавычки и точки с запятой.
     * @param inputString
     * @return
     */
    private static String RemoveUnnecessaryChar(String inputString) {
        return inputString.replace("\"", "").replace(";", "");
    }

    /**
     * Удаляет первую и последнюю кавычку в строке
     * @param inputString
     * @return
     */
    private static String RemoveFirstAndLastQuotes(String inputString) {
        inputString = inputString.replace(";", "");
        return inputString.substring(1, inputString.length() - 1);
    }

    /**
     * Возвращает второе название в строке
     * (пример: "Усвятский муниципальный район";"рп Усвяты" вернёт "рп Усвяты", ну только без кавычек)
     * @param inputString
     * @return
     */
    private static String ReturnLastStringFromTwoNames(String inputString) {
        return inputString.substring(inputString.indexOf(";") + 2, inputString.length() - 1);
    }
}
