package example.spring;

import org.jspecify.annotations.Nullable;

public interface GenericApi<T extends @Nullable Object> {
	void go(T t);
}
