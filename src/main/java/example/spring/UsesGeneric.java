package example.spring;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
class UsesGeneric<T extends @Nullable Object> {
	UsesGeneric(T o) {}

	void method(T t) {}

	static void fails() {
		// fails
		UsesGeneric<@Nullable Object> api = new UsesGeneric<>(null);
	}

	static void fails2() {
		// fails
		new UsesGeneric<@Nullable Object>(null);
	}

	static void succeeds() {
		UsesGeneric<@Nullable Object> api = new UsesGeneric<>("null");
		api.method(null); // success
	}
}

@NullMarked
class NullableUsesGeneric extends UsesGeneric<@Nullable Object> {
	NullableUsesGeneric() {
		super(null); // fails
		method(null); // fails
	}

	static void succeeds() {
		NullableUsesGeneric api = new NullableUsesGeneric();
		api.method(null); // works, but cannot do this.method(null); as shown in the constructor
	}
}
