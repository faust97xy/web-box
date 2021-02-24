package pcshaman.web.core.util.object;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.TableModel;
import pcshaman.web.core.util.text.TextBuilder;

/**
 *
 * @author pcshaman
 */
public class ObjectScriber {

    public static String getLp(Integer i, Integer size) {
        String lp = "";
        Integer value = i + 1;
        Integer length = size.toString().length() - value.toString().length();
        for (int ix = 0; ix < length; ix++) {
            lp += "0";
        }
        return lp + value;
    }

    public static String rewriteMap(Map map) {
        ObjectConverter converter = new ObjectConverter();
        TextBuilder builder = new TextBuilder();

        Map<Object, Object> data = new LinkedHashMap<>(map);
        for (Map.Entry<Object, Object> point : data.entrySet()) {
            Object key = point.getKey();
            Object value = point.getValue();

            builder.addWord(String.valueOf(key)).addChar(':');
            builder.addWord(converter.toString(value));
            builder.addEndLine();

        }

        return builder.toText();
    }

    public static String rewriteArray(Object[] array) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (ObjectTool.existAll(array)) {
            for (int i = 0; i < array.length; i++) {
                Object data = array[i];
                map.put(getLp(i, array.length), data);
            }
        }
        return rewriteMap(map);

    }

    public static String rewriteList(List list) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (ObjectTool.exist(list)) {
            for (int i = 0; i < list.size(); i++) {
                Object data = list.get(i);
                map.put(getLp(i, list.size()), data);
            }
        }
        return rewriteMap(map);

    }

    public static String rewriteTable(TableModel table) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (ObjectTool.exist(table)) {
            for (int ci = 0; ci < table.getColumnCount(); ci++) {
                String key = table.getColumnName(ci);
                Object value = table.getValueAt(0, ci);
                map.put(key, value);
            }
        }
        return rewriteMap(map);
    }

    public static String rewriteObject(Object object, String... paths) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (ObjectTool.existAll(object, paths)) {
            ObjectManager manager = new ObjectManager(object.getClass());
            manager.setObject(object);
            for (String path : paths) {
                map.put(path, manager.getValue(path));
            }

        }
        return rewriteMap(map);
    }

    public static String rewriteObject(Object object) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (ObjectTool.exist(object)) {
            ObjectManager manager = new ObjectManager(object.getClass());
            manager.setObject(object);

            Set<String> paths = manager.getFields().keySet();
            for (String path : paths) {
                Object value = manager.getValue(path);
                map.put(path, value);
            }
        }
        return rewriteMap(map);
    }

    public static Map<String, Object> creatMapFields(Object object) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (object != null) {
            ObjectManager manager = new ObjectManager(object.getClass());
            manager.setObject(object);
            Set<String> paths = manager.getFields().keySet();
            for (String path : paths) {
                Object value = manager.getValue(path);
                map.put(path, value);
            }
        }
        return map;
    }

    public static String rewriteDatPipe(Object object) {
        StringBuilder builder = new StringBuilder();
        if (ObjectTool.exist(object)) {
            ObjectConverter converter = new ObjectConverter();
            ObjectManager manager = new ObjectManager(object.getClass());
            manager.setObject(object);

            Set<String> paths = manager.getFields().keySet();
            for (String path : paths) {
                Object value = manager.getValue(path);
                builder.append(converter.toString(value)).append("|");
            }
        }
        return builder.toString();
    }

}
