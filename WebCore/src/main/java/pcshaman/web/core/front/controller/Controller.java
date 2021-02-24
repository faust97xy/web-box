package pcshaman.web.core.front.controller;

import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 */
public abstract class Controller {

    protected static final Map<String, Object> MODEL = new HashMap();

    protected Integer browserDraw = 0;

    /* ========================================================================
       XXX
     */
    public static class ControllerVariables {

        public static String APP_NAME = "";
        public static String ALERT_TYPE = "";
        public static String ALERT_MESSAGE = "";
        public static String PAGE_NAME = "";
        public static String PAGE_TITLE = "";

    }

    /* ========================================================================
       ABSTRACT
     */
    protected abstract Logger getLogger();

    /* ========================================================================
       PRINTERS
     */
    protected void printParams(Map<String, String> params) {
        ////////////wydruk danych wejściowych
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        getLogger().info("RequestParam map: {}", builder.toString());
    }

    /* ========================================================================
       VARIABLE
     */
    protected static Map reloadModel(String pageName, String pageDisplayName) {
        removeVariable(ControllerVariables.ALERT_TYPE);
        removeVariable(ControllerVariables.ALERT_MESSAGE);

        String appName = getVariable(String.class, ControllerVariables.APP_NAME);

        putVariable(ControllerVariables.PAGE_NAME, pageName);
        StringBuilder builder = new StringBuilder();
        builder.append(pageDisplayName).append(" (").append(appName).append(")");
        putVariable(ControllerVariables.PAGE_TITLE, builder.toString());
        return MODEL;
    }

    public static void removeVariable(String... names) {
        if (ObjectTool.exist(names)) {
            for (String name : names) {
                MODEL.remove(name);
            }
        }
    }

    public static <T> T getVariable(Class<T> clazz, String name) {
        return (T) MODEL.get(name);
    }

    public static void putVariable(String name, Object value) {
        MODEL.put(name, value);
    }

    public static void setAppName(String title) {
        putVariable(ControllerVariables.APP_NAME, title);
    }

    @ModelAttribute("dateFormat")
    public String dateFormat() {
        return "dd.MM.yyyy";
    }

    @ModelAttribute("godzinyWizytFormat")
    public String godzinyWizytFormat() {
        return "HH.mm";
    }

    protected GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBulider = new GsonBuilder();
        gsonBulider.setDateFormat(dateFormat());
        return gsonBulider;
    }

}
