package jackson3;

import java.util.Map;

import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CglibTest {

	@Test
	public void cglibPropertiesNotRendered() throws Exception {
		JsonMapper mapper = JsonMapper.builder().build();
		Person target = new Person("first", "last");
		ProxyFactory factory = new ProxyFactory(target);
		factory.setOpaque(true);

		Person proxied = (Person) factory.getProxy();
		String json = mapper.writeValueAsString(proxied);
		Map<String, Object> properties = mapper.readValue(json, new TypeReference<>() {
		});
		assertThat(properties).containsOnlyKeys("firstName", "lastName");
	}

	static class Person {
		private final String firstName;

		private final String lastName;

		public Person(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}
	}
}
