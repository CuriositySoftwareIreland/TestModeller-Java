package ie.curiositysoftware.testmodeller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestModellerSuite {
    long modelId() default 0;
    long modelVersionId() default 0;
    long profileId() default 0;
    long id() default 0;
}
