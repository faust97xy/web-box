package pcshaman.web.core.model.collections;

/**
 *
 * @author pcShaman
 * @param <V> typ wartości
 */
public interface Collections<V> {

    public V getValue();

    public String getDescription();
    
    public int compare(V value);

    public Collections<V>[] items();

    default Collections<V> find(V value) {
        Collections<V> find = null;
        if (value != null) {
            for (Collections<V> type : items()) {
                if (type.compare(value) == 0) {
                    find = type;
                }
            }
        }
        return find;

    }
}
