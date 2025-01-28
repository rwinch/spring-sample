package example;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD})
@HasPermission(object = "{value}", permission = "'read'")
public @interface HasReadPermission {
//	@AliasFor(annotation = HasPermission.class, value = "object")
	String value();
}
