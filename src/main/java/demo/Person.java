package demo;

public class Person {
	private final Name name;

	private final int age;

	public Person(Name name, int age) {
		this.name = name;
		this.age = age;
	}

	public Name getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}
