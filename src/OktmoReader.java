import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 2.2.	OktmoReader считывает текстовые файлы .txt и добавляет их содержимое в разобранном виде в OktmoData
 */
public class OktmoReader {
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
                    System.out.println("Это не попадёт в базу (не соответствие формату):\nНомер позиции: " + lineCount + "\nТекст: " + s + "\n");
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
}
