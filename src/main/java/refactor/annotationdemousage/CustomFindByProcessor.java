package refactor.annotationdemousage;

import refactor.annotations.CustomFindBy;

import java.lang.reflect.Field;

public class CustomFindByProcessor {
    public static void processAnnotations(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            if (field.isAnnotationPresent(CustomFindBy.class)) {
                CustomFindBy annotation = field.getAnnotation(CustomFindBy.class);
                field.setAccessible(true); // Nếu không có dòng này, không thể truy cập field private!
                String selector = getSelector(annotation);
                System.out.println("Field: " + field.getName());
                System.out.println("Selector: " + selector);
            }
        }
    }
    private static String getSelector(CustomFindBy annotation) {
        if (!annotation.id().isEmpty()) {
            return "ID: " + annotation.id();
        } else if (!annotation.css().isEmpty()) {
            return "CSS: " + annotation.css();
        } else if (!annotation.xpath().isEmpty()) {
            return "XPath: " + annotation.xpath();
        } if (!annotation.name().isEmpty()) {
            return "Name: " + annotation.name();
        } else if (!annotation.className().isEmpty()) {
            return "ClassName: " + annotation.className();
        } else if (!annotation.tagName().isEmpty()) {
            return "TagName: " + annotation.tagName();
        } else if (!annotation.linkText().isEmpty()) {
            return "LinkText: " + annotation.linkText();
        } else if (!annotation.partialLinkText().isEmpty()) {
            return "PartialLinkText: " + annotation.partialLinkText();
        }
        return "No selector specified";
    }
}
