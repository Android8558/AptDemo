package demo.apt.aptlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>×¢½â</p>
 *
 * @author liuzhaohong 2018/6/4 16:13
 * @version V1.0
 * @name ContentViewId
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ContentViewId {
    int id();
    String title() default "±êÌâ";
}
