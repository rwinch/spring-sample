package demo.getters;

class Line {
	private final String description;
	private final Point point1;
	private final Point point2;

	public Line(String description, Point point1, Point point2) {
		this.description = description;
		this.point1 = point1;
		this.point2 = point2;
	}

	public String getDescription() {
		return description;
	}

	public Point getPoint1() {
		return point1;
	}

	public Point getPoint2() {
		return point2;
	}
}
