package demo;

public class Message {
	private final Person person;

	private final String text;


	public Message(Person person, String text) {
		this.person = person;
		this.text = text;
	}

	public Person getPerson() {
		return person;
	}

	public String getText() {
		return text;
	}
}
