package pcshaman.web.core.util;

/**
 *
 * @author jjurczak
 */
/**
 *
 * @author pcShaman
 */
public enum MessageColection {

    UNDEFINED_EXCEPTION("Wystąpił niezdefiniowany wyjątek!"),
    MODEL_IS_NULL("Brak przekazanego modelu!"),
    MODEL_IS_EMPTY("Model jest pusty!"),
    PARAMETER_IS_NULL("Brak przekazanego jednego z parametrów!"),
    PARENT_IS_NULL("Brak zdefiniowanego rodzica dla obiektu potomnego!"),
    FILE_NOT_EXIST("Wskazany plik nie istnieje!"),
    NO_VALUE_FOR_THE_KEY("Brak wartości dla wskazanego klucza!"),
    INCORRECT_DATA_FORMAT("Niepoprawny format danych!"),;

    private final String message;

    private MessageColection(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
    
    
}
