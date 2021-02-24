package pcshaman.web.core.model.collections;

/**
 *
 * @author jjurczak
 */
public enum UzytkownikTyp implements Collections<Integer> {

    ADMIN(0, "Administrator"),
    USER(1, "Użytkownik");

    private final Integer value;
    private final String description;

    private UzytkownikTyp(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Collections<Integer>[] items() {
        return values();
    }

    @Override
    public int compare(Integer value) {
        return getValue().compareTo(value);
    }

}
