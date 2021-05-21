package demo;

public class ProjectSurvey {

	private final String id;

	private final Project project;


	public ProjectSurvey(String id, Project project) {
		this.id = id;
		this.project = project;
	}


	public String getId() {
		return this.id;
	}

	public Project getProject() {
		return this.project;
	}
}
