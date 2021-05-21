package demo;

public class ProjectSurvey {
	private final Person person;

	private final String text;


	public ProjectSurvey(Person person, String text) {
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
