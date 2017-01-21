package ch.batthomas.surf.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author batthomas
 * @param <T>
 */
public abstract class Constant<T> {

    private final Map<String, T> content;

    public Constant() {
        content = new HashMap<>();
    }

    public abstract void initializeContent();

    public T get(String key) {
        return content.get(key);
    }

    public Map<String, T> getContent() {
        return content;
    }
}
