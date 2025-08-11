package example.spring;

public class UseGenericApi {

	void go(NullableGenericImpl api) {
		// this works
		api.go("");
		api.go(null);
	}

	void go(GenericImpl api) {
		api.go("");
		// This fails as expected
		// api.go(null);
	}
}
