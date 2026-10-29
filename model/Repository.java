
package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.annotation.Id;

public class Repository<T> {

    private final Map<String, T> data = new HashMap<>();

    public void save(T item) {
        try {
            String id = getId(item);
            data.put(id, item);
            System.out.println("Saved object with ID: " + id);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Could not read object ID.", e);
        }
    }

    private String getId(T item)
            throws ReflectiveOperationException {

        if (item == null) {
            throw new IllegalArgumentException("Object cannot be null.");
        }

        Class<?> type = item.getClass();

        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    Object value = field.get(item);

                    if (value == null) {
                        throw new IllegalArgumentException(
                                "Object ID cannot be null.");
                    }

                    return value.toString();
                }
            }

            type = type.getSuperclass();
        }

        throw new IllegalArgumentException(
                "No field annotated with @Id was found.");
    }

    public T findById(String id) {
        return data.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean delete(String id) {
        return data.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public int size() {
        return data.size();
    }
public static <N extends Number> double average(N[] values) {
    if (values == null || values.length == 0) {
        throw new IllegalArgumentException(
                "Values cannot be null or empty.");
    }

    double sum = 0;

    for (N value : values) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Values cannot contain null.");
        }
        sum += value.doubleValue();
    }

    return sum / values.length;
}
}
