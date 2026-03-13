package refactor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomFindBy {
    String id() default "";
    String css() default "";
    String xpath() default "";
    String name() default "";
    String className() default "";
    String tagName() default "";
    String linkText() default "";
    String partialLinkText() default "";
}
