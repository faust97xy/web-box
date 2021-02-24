package pcshaman.web.core.util.text;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import pcshaman.web.core.util.MessageColection;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author pcShaman
 */
public class TextTool {

    public static String toText(String html) {
        String toText = html.replaceAll("<\\p{Alpha}+>", "");
        toText = toText.replaceAll("</\\p{Alpha}+>", "");
        toText = toText.replace("<br/>", "\n");
        return toText;
    }

    public static String superTrim(String text) {
        String out = null;
        if (ObjectTool.exist(text)) {
            out = text.replaceAll(" +", " ");
            out = out.trim();
        }
        return out;

    }

    public static String cleanChar(String text, String[] words) {
        String clean = null;
        if (ObjectTool.exist(text)) {
            clean = text;
            for (String word : words) {
                String string = (word == null) ? "" : word.trim();
                clean = clean.replace(string, "");
            }
        }
        return clean;
    }

    public static String cut1Lines(String text) {
        String string = null;
        if (ObjectTool.exist(text)) {
            if (text.contains(TextConstant.HTML_END_OF_LINE)) {
                String[] split = text.split(TextConstant.HTML_END_OF_LINE);
                string = split[0] + " ...";
            } else if (text.contains(TextConstant.TEXT_END_OF_LINE)) {
                String[] split = text.split(TextConstant.TEXT_END_OF_LINE);
                string = split[0] + " ...";
            } else {
                string = text;
            }
        }
        return string;
    }

    public static String cutWords(String text, int noWards) {
        String string = "";
        if (ObjectTool.exist(text)) {
            String[] split = text.split(" ");
            int stop = split.length < noWards ? split.length : noWards;
            for (int i = 0; i < stop; i++) {
                string += split[i] + " ...";
            }
        }
        return string;
    }

    public static int countWords(String text, String word) {
        int count = 0;

        StringTokenizer st = new StringTokenizer(text);
        while (st.hasMoreElements()) {
            count += (st.nextToken().equals(word)) ? 1 : 0;
        }

        return count;
    }

    public static int countSegment(String text, String segment) {
        return text.split(segment).length - 1;
    }

    public static String replaceAll(String text, String nword, String... owords) {
        String out = null;
        if (ObjectTool.existAll(text, nword, owords)) {
            out = text;
            for (String oword : owords) {
                out = out.replace(oword, nword);
            }
        }
        return out;
    }

    public static String replaceAll(String string, String[] stringOld, String stringNew) {
        if (ObjectTool.existAll(string, stringOld)) {
            String replace = string;
            for (String string1 : stringOld) {
                replace = replace.replace(string1, stringNew);
            }

            return replace.trim();

        } else {
            return string;
        }
    }

    public static String replaceWords(String string, Map<String, String> map) {
        if (ObjectTool.existAll(string, map)) {
            for (Map.Entry<String, String> point : map.entrySet()) {
                string = string.replace(point.getKey(), point.getValue());
            }
        }

        return string;
    }

    public static String replaceBadCoding(String string) {
        if (ObjectTool.exist(string)) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("Ä„", "Ą");
            map.put("Ä…", "ą");
            map.put("Ä†", "Ć");
            map.put("Ä‡", "ć");
            map.put("Ä", "Ę");
            map.put("Ä™", "ę");
            map.put("Ĺ", "Ł");
            map.put("Ĺ‚", "ł");
            map.put("Ĺ", "Ń");
            map.put("Ĺ„", "ń");
            map.put("Ă“", "Ó");
            map.put("Ăł", "ó");
            map.put("Ĺš", "Ś");
            map.put("Ĺ›", "ś");
            map.put("Ĺ»", "Ż");
            map.put("ĹĽ", "ż");
            map.put("Ĺą", "Ź");
            map.put("Ĺş", "ź");

            string = replaceWords(string, map);
        }

        return string;
    }

    public static String replacePlChar(String string) {
        if (ObjectTool.exist(string)) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("Ą", "A");
            map.put("ą", "a");
            map.put("Ć", "C");
            map.put("ć", "c");
            map.put("Ę", "E");
            map.put("ę", "e");
            map.put("Ł", "L");
            map.put("ł", "l");
            map.put("Ń", "N");
            map.put("ń", "n");
            map.put("Ó", "O");
            map.put("ó", "o");
            map.put("Ś", "S");
            map.put("ś", "s");
            map.put("Ż", "Z");
            map.put("ż", "z");
            map.put("Ź", "Z");
            map.put("ź", "z");

            string = replaceWords(string, map);
        }

        return string;
    }

    public static String toUnderscoreCase(String string) {
        String out = string;
        if (ObjectTool.exist(out)) {
            out = replacePlChar(out.replaceAll("\\p{Punct}", "").toLowerCase()).replaceAll(" +", "_");
        }

        return out;
    }

    public static String splitCamelCase(String s) {
        String format = String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])");
        return s.replaceAll(format, " ");
    }

    public static String toCamelCase(String string) {
        String out = string;
        if (ObjectTool.exist(out)) {
            out = out.replaceAll("\\p{Punct}", "");
            out = out.replaceAll(" +", " ").trim();

            StringTokenizer tokenizer = new StringTokenizer(out, " ");
            StringBuilder builder = new StringBuilder();
            while (tokenizer.hasMoreElements()) {
                String token = tokenizer.nextToken();
                builder.append(String.valueOf(token.charAt(0)).toUpperCase()).append(token.substring(1, token.length()));
            }
            out = builder.toString();

        }
        return out;
    }

    public static String firstToUpperCase(String string) {
        StringBuilder builder = new StringBuilder();
        builder.append(string.substring(0, 1).toUpperCase()).append(string.substring(1));
        return builder.toString();
    }

    public static String firstToLowerCase(String string) {
        StringBuilder builder = new StringBuilder();
        builder.append(string.substring(0, 1).toLowerCase()).append(string.substring(1));
        return builder.toString();
    }

    public static String breakRow(String string, Integer maxLength) {
        if (ObjectTool.existAll(string, maxLength)) {
            StringBuilder builder = new StringBuilder();

            String tmp = superTrim(string);
            if (maxLength.compareTo(tmp.length()) == 1) {
                int count = 0;
                String[] split = tmp.split(" ");
                for (String word : split) {
                    if (count + word.length() < maxLength) {
                        builder.append(" ").append(word);
                        count += word.length();
                    } else {
                        builder.append(" ").append(word).append("\n");
                        count = 0;
                    }
                }
            }

            return superTrim(builder.toString());

        } else {
            throw new NullPointerException(MessageColection.PARENT_IS_NULL.getMessage());
        }

    }

    public static int countWord(String text, String word) {
        if (ObjectTool.existAll(text, word)) {
            int count = 0;

            StringTokenizer st = new StringTokenizer(text);
            while (st.hasMoreElements()) {
                count += (st.nextToken().equals(word)) ? 1 : 0;
            }

            return count;

        } else {
            throw new NullPointerException(MessageColection.PARENT_IS_NULL.getMessage());
        }

    }

}
