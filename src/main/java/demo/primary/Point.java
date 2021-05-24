package demo.primary;

class Point {
	private final String description;
	private final int x;
	private final int y;

	public Point(int x, int y) {
		this("default description", x, y);
	}

	public Point(String description, int x, int y) {
		this.description = description;
		this.x = x;
		this.y = y;
	}

	public String getDescription() {
		return description;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}