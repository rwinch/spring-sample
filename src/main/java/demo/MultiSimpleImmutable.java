package demo;

public class MultiSimpleImmutable {
	private final String id;

	private final String description;

	public MultiSimpleImmutable(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
