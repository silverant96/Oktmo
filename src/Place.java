import java.util.Locale;

/**
 * Place - населенный пункт
 * code - код населенного пункта
 * name - наименование населенного пункта
 * status - тип населенного пункта
 */
public class Place implements Comparable<Place> {
    long code;
    String name;
    String status;

    public Place(long code, String name, String status) {
        this.code = code;
        this.name = name;
        this.status = status;
        fixStatus(status);
    }

    public void fixStatus(String status) {
        String s = status.toLowerCase(Locale.ROOT);

        if (s.contains("сельсовет")) {
            this.status = "Сельсовет";
        }
        if (s.contains("зато")) {
            this.status = "ЗАТО";
        }
        if (s.contains("муниципальный район")) {
            this.status = "Муниципальный район";
        }
        if (s.contains("муниципальный округ")) {
            this.status = "Муниципальный округ";
        }
        if (s.contains("город") ||
                s.contains("г ")) {
            this.status = "Город";
        }
        if (s.contains("городской поселок") ||
                s.contains("пгт ")) {
            this.status = "Поселок городского типа";
        }
        if (s.contains("сельское поселение")) {
            this.status = "Сельское поселение";
        }
        if (s.contains("д ") ||
                s.contains("д.") ||
                s.contains("деревня")) {
            this.status = "Деревня";
        }
        if (s.contains("рп ") ||
                s.contains("рп.") ||
                s.contains("рабочий посёлок") ||
                s.contains("рабочий поселок")) {
            this.status = "Рабочий поселок";
        }
        if (s.contains("н ") ||
                s.contains("н.") ||
                s.contains("наслег")) {
            this.status = "Наслег";
        }
        if (s.contains("с ") ||
                s.contains("с.") ||
                s.contains("село")) {
            this.status = "Село";
        }
        if (s.contains("ст ") ||
                s.contains("ст.") ||
                s.contains("ст-ца") ||
                s.contains("станица")) {
            this.status = "Станица";
        }
        if (s.contains("а ") ||
                s.contains("а.") ||
                s.contains("аул")) {
            this.status = "Аул";
        }
        if (s.contains("х ") ||
                s.contains("х.") ||
                s.contains("хутор")) {
            this.status = "Хутор";
        }
        if (s.contains("к ") ||
                s.contains("к.") ||
                s.contains("кишлак")) {
            this.status = "Кишлак";
        }
        if (s.contains("п ") ||
                s.contains("п.") ||
                s.contains("поселок") ||
                s.contains("посёлок") ) {
            this.status = "Поселок";
        }
        if (s.contains("шахты ")) {
            this.status = "Шахты";
        }
        if (s.contains("лесничество")) {
            this.status = "Лесничество";
        }
        if (s.contains("санаторий")) {
            this.status = "Санаторий";
        }
        if (s.contains("район")) {
            this.status = "Район";
        }
    }

    @Override
    public String toString() {
        return "Place{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                "}\n";
    }

    @Override
    public int compareTo(Place o) {
        int minLengthOfString = Math.min(this.name.length(), o.name.length());
        String internalString = this.name.toLowerCase(Locale.ROOT);
        String externalString = o.name.toLowerCase(Locale.ROOT);

        for (int i = 0; i < minLengthOfString; i++) {
            char internalCharOfString = internalString.charAt(i);
            char externalCharOfString = externalString.charAt(i);
            if (internalCharOfString > externalCharOfString) {
                return 1;
            } else {
                if (internalCharOfString < externalCharOfString) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
