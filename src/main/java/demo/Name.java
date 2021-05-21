package demo;

import javax.validation.constraints.NotEmpty;

public class Name {
	@NotEmpty(message = "required")
	private final String firstName;

	private final String lastName;

	public Name(String firstName, String lastName) {
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
