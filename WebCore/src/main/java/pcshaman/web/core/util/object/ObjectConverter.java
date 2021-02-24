package pcshaman.web.core.util.object;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jjurczak
 */
class ObjectConverter {

    public static String FORMAT_T_DATE = "yy-MM-dd'T'HH:mm:ss.SSSz";
    public static String FORMAT_SQL_DATE = "yyyy-MM-dd";
    public static String FORMAT_DISPLAY_DATE_TIME = "dd.MM.yyyy HH:mm";
    public static String FORMAT_DISPLAY_DATE = "dd.MM.yyyy";

    public static String toString(String formatString, Date value) {
        String string = null;
        if (ObjectTool.existAll(formatString, value)) {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            string = format.format(value);
        }
        return string;
    }

    public static String toString(Object object) {
        String string = null;
        if (ObjectTool.exist(object)) {
            if (ObjectTool.exist(object)) {
                if (object instanceof String) {
                    string = String.valueOf(object);

                } else if (object instanceof Boolean) {
                    Boolean value = (Boolean) object;
                    string = value ? "Tak" : "Nie";

                } else if (object instanceof Date) {
                    Date value = (Date) object;
                    if (toString("HH:mm", value).equals("00:00")) {
                        string = toString(FORMAT_DISPLAY_DATE, value);
                    } else {
                        string = toString(FORMAT_DISPLAY_DATE_TIME, value);
                    }

                } else if (object instanceof Number) {
                    BigDecimal value = new BigDecimal(String.valueOf(object));
                    value = value.setScale(2, RoundingMode.HALF_UP);

                    Double part = value.doubleValue() - value.intValue();
                    if (part.compareTo(0d) == 0) {
                        string = String.valueOf(value.intValue());
                    } else {
                        string = String.valueOf(value.doubleValue()).replace('.', ',');
                    }

                } else {
                    string = String.valueOf(object);
                }

            }
        }

        return string;

    }
}
