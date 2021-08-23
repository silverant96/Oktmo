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
        this.name = this.name.replace(this.status, "");

        if (s.contains("сельсовет")) {
            this.status = "Сельсовет";
            return;
        }
        if (s.contains("зато")) {
            this.status = "ЗАТО";
            return;
        }
        if (s.contains("муниципальный район")) {
            this.status = "Муниципальный район";
            return;
        }
        if (s.contains("муниципальный округ")) {
            this.status = "Муниципальный округ";
            return;
        }
        if (s.contains("город ") ||
                s.contains("г ")) {
            this.status = "Город";
            return;
        }
        if (s.contains("городской поселок") ||
                s.contains("пгт ")) {
            this.status = "Поселок городского типа";
            return;
        }
        if (s.contains("сельское поселение")) {
            this.status = "Сельское поселение";
            return;
        }
        if (s.contains("д ") ||
                s.contains("д.") ||
                s.contains("деревня")) {
            this.status = "Деревня";
            return;
        }
        if (s.contains("рп ") ||
                s.contains("рп.") ||
                s.contains("рабочий посёлок") ||
                s.contains("рабочий поселок")) {
            this.status = "Рабочий поселок";
            return;
        }
        if (s.contains("н ") ||
                s.contains("н.") ||
                s.contains("наслег")) {
            this.status = "Наслег";
            return;
        }
        if (s.contains("с ") ||
                s.contains("с.") ||
                s.contains("село")) {
            this.status = "Село";
            return;
        }
        if (s.contains("станица")) {
            this.status = "Станица";
            return;
        }

        if (s.contains("ст ") ||
                s.contains("ст.") ||
                s.contains("ст-ца")) {
            this.status = "Станция";
            return;
        }

        if (s.contains("а ") ||
                s.contains("а.") ||
                s.contains("аул")) {
            this.status = "Аул";
            return;
        }
        if (s.contains("х ") ||
                s.contains("х.") ||
                s.contains("хутор")) {
            this.status = "Хутор";
            return;
        }
        if (s.contains("к ") ||
                s.contains("к.") ||
                s.contains("кишлак")) {
            this.status = "Кишлак";
            return;
        }
        if (s.contains("п ") ||
                s.contains("п.") ||
                s.contains("поселок ") ||
                s.contains("посёлок ") ) {
            this.status = "Поселок";
            return;
        }
        if (s.contains("шахты ")) {
            this.status = "Шахты";
            return;
        }
        if (s.contains("лесничество ")) {
            this.status = "Лесничество";
            return;
        }
        if (s.contains("санаторий ")) {
            this.status = "Санаторий";
            return;
        }
        if (s.contains("район")) {
            this.status = "Район";
            return;
        }

        this.status = "Неизвестный тип";
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
