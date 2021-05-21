package demo;

public class ProjectSurvey {
	private final String text;

	private final Person person;


	public ProjectSurvey(String text, Person person) {
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
