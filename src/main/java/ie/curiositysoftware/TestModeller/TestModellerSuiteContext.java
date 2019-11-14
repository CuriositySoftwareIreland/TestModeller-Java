package ie.curiositysoftware.TestModeller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TestModellerSuiteContext {
    long profileId() default 0;
    long modelId() default 0;
    long modelVersionId() default 0;
}
