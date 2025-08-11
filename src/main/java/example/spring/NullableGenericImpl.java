package example.spring;

import org.jspecify.annotations.Nullable;

public class NullableGenericImpl implements GenericApi<@Nullable Object> {
	@Override
	public void go(@Nullable Object o) {

	}
}
