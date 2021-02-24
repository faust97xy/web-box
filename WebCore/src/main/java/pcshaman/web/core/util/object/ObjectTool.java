package pcshaman.web.core.util.object;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pcShaman
 */
public class ObjectTool {

    private static final Logger log = LoggerFactory.getLogger(ObjectTool.class);

    public static boolean isNull(Object... input) {
        return !notNull(input);
    }

    public static boolean notNull(Object... input) {
        boolean exist = Boolean.TRUE;
        for (Object object : input) {
            if (object == null) {
                exist = Boolean.FALSE;
                break;
            } else if (object instanceof String) {
                String string = (String) object;
                exist = !string.toLowerCase().equals("null");
            }

        }
        return exist;
    }

    public static boolean notExist(Object input) {
        return !exist(input);
    }

    public static boolean notExist(Object... input) {
        return !existAll(input);
    }

    public static boolean existAll(Object... input) {
        boolean exist = Boolean.FALSE;
        for (Object object : input) {
            exist = Boolean.TRUE;
            if (notExist(object)) {
                exist = Boolean.FALSE;
                break;
            }

        }
        return exist;
    }

    public static boolean existOne(Object... input) {
        boolean exist = Boolean.FALSE;
        for (Object object : input) {
            if (exist(object)) {
                exist = Boolean.TRUE;
                break;
            }

        }
        return exist;
    }

    public static boolean exist(Object input) {
        boolean exist = Boolean.FALSE;
        if (input != null) {
            if (input instanceof StringBuilder) {
                StringBuilder builder = (StringBuilder) input;
                String string = builder.toString().trim();
                exist = !string.trim().isEmpty();

            } else if (input instanceof String) {
                String string = (String) input;
                exist = !string.trim().isEmpty();

            } else if (input instanceof Number) {
                BigDecimal decimal = new BigDecimal(input.toString()).setScale(2, RoundingMode.HALF_UP);
                exist = decimal.compareTo(BigDecimal.ZERO) != 0;

            } else if (input instanceof Collection) {
                Collection collection = (Collection) input;
                exist = !collection.isEmpty();

            } else if (input instanceof Map) {
                Map map = (Map) input;
                exist = !map.isEmpty();

            } else if (input instanceof Object[]) {
                Object[] array = (Object[]) input;
                exist = array.length > 0;

            } else {
                exist = true;
            }

        }

        return exist;
    }

    public static <T extends Object> boolean compareType(T a, T b) {
        boolean r = false;
        if (a != null && b != null) {
            r = a.getClass().equals(b.getClass());
        }
        return r;
    }

    public static <T extends Object> T clone(T input) {
        T output = null;
        if (ObjectTool.exist(input)) {
            try {
                output = (T) input.getClass().newInstance();
                ObjectManager manager = new ObjectManager(input.getClass());
                Collection<Field> values = manager.getFields().values();
                for (Field field : values) {
                    if (!Modifier.isFinal(field.getModifiers())) {
                        Object data = field.get(input);
                        field.set(output, data);
                    }
                }

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException exception) {
                log.error("ObjectTool.clone: ", exception);
            }
        }
        return output;
    }

    public static <T extends Object> T merge(T a, T b) {
        T c = null;
        if (ObjectTool.existAll(a, b)) {
            c = ObjectTool.clone(a);

            ObjectManager manager = new ObjectManager(c.getClass());
            Collection<Field> values = manager.getFields().values();
            for (Field field : values) {
                try {
                    Object dataA = field.get(a);
                    Object dataB = field.get(b);

                    if (ObjectTool.exist(dataB) && !dataB.equals(dataA)) {
                        field.set(c, dataB);
                    }

                } catch (IllegalArgumentException | IllegalAccessException exception) {
                    log.error("ObjectTool.merge: ", exception);
                    return null;
                }
            }
        }

        return c;
    }

    public static <T extends Object> T coalesce(T... datas) {
        T value = null;
        if (existAll(datas)) {
            for (T data : datas) {
                if (exist(data)) {
                    value = data;
                    break;
                }
            }
        }
        return value;

    }

    public static boolean containsInterface(Class type, Class intf) {
        boolean contains = Boolean.FALSE;
        if (ObjectTool.existAll(type, intf)) {
            Class[] interfaces = type.getInterfaces();
            for (Class itype : interfaces) {
                if (itype.equals(intf)) {
                    contains = Boolean.TRUE;
                    break;
                }
            }
        }
        return contains;
    }


}
