package demo;

public class NestedWithSingleSimpleImmutable {
	private final SingleSimpleImmutable singleSimpleImmutable;

	public NestedWithSingleSimpleImmutable(SingleSimpleImmutable singleSimpleImmutable) {
		this.singleSimpleImmutable = singleSimpleImmutable;
	}

	public SingleSimpleImmutable getSingleSimpleImmutable() {
		return singleSimpleImmutable;
	}
}
