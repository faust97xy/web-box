package pcshaman.web.core.util.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pcshaman.web.core.util.MessageColection;

/**
 *
 * @author pcshaman
 * @param <T>
 */
public class ObjectManager<T> {

    private final Logger log = LoggerFactory.getLogger(ObjectManager.class);

    private T object;
    private final Class clazz;
    private final Map<String, Field> fields;

    public ObjectManager(Class clazz) {
        if (ObjectTool.exist(clazz)) {
            this.clazz = clazz;
            this.fields = getFields();
        } else {
            throw new NullPointerException(MessageColection.PARAMETER_IS_NULL.getMessage());
        }

    }

    public ObjectManager(Class clazz, T object) {
        if (ObjectTool.existAll(clazz, object)) {
            this.clazz = clazz;
            this.object = object;
            this.fields = getFields();
        } else {
            throw new NullPointerException(MessageColection.PARAMETER_IS_NULL.getMessage());
        }

    }

    public void setObject(T object) {
        if (ObjectTool.exist(object)) {
            this.object = object;
        } else {
            throw new NullPointerException(MessageColection.PARAMETER_IS_NULL.getMessage());
        }
    }

    public final Map<String, Field> getFields() {
        Map<String, Field> map = new LinkedHashMap<>();
        Class point = clazz;
        while (point != null) {
            /// pobranie pól
            Field[] fieldsArray = point.getDeclaredFields();
            for (Field field : fieldsArray) {
                String name = field.getName();
                if (!map.containsKey(name)) {
                    map.put(name, field);
                    field.setAccessible(true);
                }
            }

            /// pobranie pól z clas przodków
            point = point.getSuperclass();
        }

        return map;
    }

    public List<String> getFieldNames() {
        return new ArrayList<>(getFields().keySet());
    }

    public boolean exist(String name) {
        boolean exist = Boolean.FALSE;
        if (name.contains(".")) {
            String[] paths = name.split("\\.");
            ObjectManager smanager = this;
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (smanager.exist(path)) {
                    Class stype = smanager.getType(path);
                    if (i == paths.length - 1) {
                        exist = smanager.exist(path);

                    } else {
                        smanager = new ObjectManager(stype);
                    }
                }
            }

        } else {
            exist = fields.containsKey(name);
        }
        return exist;
    }

    public Class getType(String name) {
        Class type = null;
        if (name.contains(".")) {
            String[] paths = name.split("\\.");
            ObjectManager smanager = this;
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (smanager.exist(path)) {
                    Class stype = smanager.getType(path);
                    if (i == paths.length - 1) {
                        type = smanager.getType(path);

                    } else {
                        smanager = new ObjectManager(stype);
                    }
                }
            }

        } else {
            Field field = fields.get(name);
            if (field != null) {
                type = field.getType();
            }
        }
        return type;
    }

    public Object getValue(String name) {
        Object value = null;
        if (object != null) {
            try {
                if (name.contains(".")) {
                    String[] paths = name.split("\\.");
                    ObjectManager smanager = this;
                    for (int i = 0; i < paths.length; i++) {
                        String path = paths[i];
                        if (smanager.exist(path)) {
                            Class stype = smanager.getType(path);
                            if (i == paths.length - 1) {
                                value = smanager.getValue(path);

                            } else {
                                Object svalue = smanager.getValue(path);
                                smanager = new ObjectManager(stype);
                                smanager.setObject(svalue);
                            }
                        }
                    }

                } else {
                    Field field = fields.get(name);
                    if (field != null) {
                        value = field.get(object);
                    }
                }

            } catch (IllegalArgumentException | IllegalAccessException exception) {
                StringBuilder builder = new StringBuilder();
                builder.append("Problem z odczytasniem wartości z pola: ").append(name.toUpperCase()).append(" ");
                builder.append("dla obiektu typu ").append(clazz.getSimpleName().toUpperCase()).append(". ");
                log.error(builder.toString(), exception);
            }

        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("Brak przekazanego obiektu o typie: ").append(clazz.getSimpleName().toUpperCase()).append(", ");
            builder.append("do pobrania wartości.");
            log.error(builder.toString());
        }
        return value;
    }

    public String getStringValue(String field) {
        return  ObjectConverter.toString(getValue(field));
    }

    public boolean setValue(String name, Object value) {
        boolean isSave = false;
        if (object != null) {
            try {
                if (name.contains(".")) {
                    String[] paths = name.split("\\.");
                    ObjectManager smanager = this;
                    for (int i = 0; i < paths.length; i++) {
                        String path = paths[i];
                        if (smanager.exist(path)) {
                            Class stype = smanager.getType(path);
                            if (i == paths.length - 1) {
                                smanager.setValue(path, value);

                            } else {
                                Object svalue = smanager.getValue(path);
                                smanager = new ObjectManager(stype);
                                smanager.setObject(svalue);
                            }
                        }
                    }

                } else {
                    Field field = fields.get(name);
                    if (field != null) {
                        field.set(object, value);
                        isSave = true;
                    }
                }

            } catch (IllegalArgumentException | IllegalAccessException exception) {
                StringBuilder builder = new StringBuilder();
                builder.append("Problem z zapisem wartości do pola: ").append(name.toUpperCase()).append(" ");
                builder.append("dla obiektu typu ").append(clazz.getSimpleName().toUpperCase()).append(". ");
                log.error(builder.toString(), exception);

            }

        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("Brak przekazanego obiektu o typie: ").append(clazz.getSimpleName().toUpperCase()).append(", ");
            builder.append("do ustawienia wartości.");
            log.error(builder.toString());

        }

        return isSave;
    }

    public String toString(T object) {
        setObject(object);

        StringBuilder builder = new StringBuilder();

        builder.append(clazz.getSimpleName()).append(": ").append("\n");
        Map<String, Field> map = getFields();
        Set<String> names = map.keySet();
        for (String name : names) {
            builder.append(name).append(": ").append(getValue(name)).append("\n");
        }

        return builder.toString();

    }
}
