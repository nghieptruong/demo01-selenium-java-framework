package refactor.annotationdemousage;

import java.lang.reflect.Field;

public class ReflectUtils {
    public static void printAllFields(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            System.out.println("Field: " + field.getName());
        }
    }
}
