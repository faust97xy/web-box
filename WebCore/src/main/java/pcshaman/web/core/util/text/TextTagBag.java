package pcshaman.web.core.util.text;

/**
 *
 * @author pcShaman
 */
public enum TextTagBag implements TextTag {

    NAWIASL_WASATY("{", "}"),
    NAWIASL_KWADRATOWE("[", "]"),
    NAWIASL_OKRAGLY("(", ")"),
    CUDZYSLOW("\"");

    private final String beginMark;
    private final String endMark;

    private TextTagBag(String beginMark, String endMark) {
        this.beginMark = beginMark;
        this.endMark = endMark;
    }

    private TextTagBag(String mark) {
        this(mark, mark);
    }

    @Override
    public String getBeginMark() {
        return beginMark;
    }

    @Override
    public String getEndMark() {
        return endMark;
    }

}
