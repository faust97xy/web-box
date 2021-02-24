package pcshaman.web.core.util.text;

import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author pcshaman
 */
public class TextBuilder {

    protected StringBuilder builder;

    public TextBuilder(String text) {
        this.builder = new StringBuilder(text);
    }

    public TextBuilder() {
        this("");
    }

    public String toText() {
        return TextTool.superTrim(builder.toString());
    }

    public boolean isEmpty() {
        return toText().isEmpty();
    }

    public TextBuilder reset() {
        builder = new StringBuilder("");
        return this;
    }

    public TextBuilder addEndLine() {
        builder.append("\n");
        return this;
    }

    public TextBuilder addNewLine(String string) {
        if (ObjectTool.exist(string)) {
            builder.append("\n");
            builder.append(string);
        }
        return this;
    }

    public TextBuilder addWord(String string) {
        if (ObjectTool.exist(string)) {
            if (builder.length() > 0) {
                if (builder.charAt(builder.length() - 1) != '\n') {
                    builder.append(" ");
                }
            }
            builder.append(string);
        }
        return this;
    }

    public TextBuilder addWord(Object object, String empty) {
        String string = (object == null) ? "" : object.toString().trim();
        return addWord(string.isEmpty() ? empty : string);
    }

    public TextBuilder addWord(Object object) {
        return addWord(object, null);
    }

    public TextBuilder addWord(String string, TextTag... tags) {
        if (tags != null && ObjectTool.exist(string)) {
            for (TextTag tag : tags) {
                string = " " + tag.getBeginMark() + string + tag.getEndMark();
            }
            return addWord(string.trim());

        } else {
            return this;
        }
    }

    public TextBuilder addWord(TextTag tag, String string) {
        if (tag != null && ObjectTool.exist(string)) {
            builder.append(" ");
            builder.append(tag.getBeginMark());
            builder.append(string);
            builder.append(tag.getEndMark());
        }
        return this;
    }

    public TextBuilder addChar(char punctuation) {
        builder.append(punctuation);
        return this;
    }

}
