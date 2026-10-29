
package util;

import java.lang.reflect.Field;

import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {

    public static boolean validate(Object object) {
        boolean valid = true;

        Class<?> currentClass = object.getClass();

        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                try {
                    Object value = field.get(object);

                    if (field.isAnnotationPresent(Id.class)) {
                        if (value == null || value.toString().isBlank()) {
                            System.out.println(
                                    field.getName() + " cannot be empty.");
                            valid = false;
                        }
                    }

                    if (field.isAnnotationPresent(Positive.class)) {
                        if (!(value instanceof Number)
                                || ((Number) value).doubleValue() < 0) {
                            Positive annotation =
                                    field.getAnnotation(Positive.class);

                            System.out.println(field.getName() + ": "
                                    + annotation.message());
                            valid = false;
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength annotation =
                                field.getAnnotation(MaxLength.class);

                        if (value != null
                                && value.toString().length()
                                > annotation.value()) {
                            System.out.println(field.getName()
                                    + " exceeds maximum length "
                                    + annotation.value());
                            valid = false;
                        }
                    }

                } catch (IllegalAccessException e) {
                    System.out.println("Unable to validate "
                            + field.getName());
                    valid = false;
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return valid;
    }
}
