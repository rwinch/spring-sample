package demo.builder;

class Point {
	private final int x;
	private final int y;

	private Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private int x;
		private int y;

		private Builder() {
		}

		public Builder x(int x) {
			this.x = x;
			return this;
		}

		public Builder y(int y) {
			this.y = y;
			return this;
		}

		public Point build() {
			return new Point(x, y);
		}
	}
}