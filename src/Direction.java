
public enum Direction {

	NORTH(0), EAST(1), SOUTH(2), WEST(3);

	private int intDir;

	private Direction(int n) { // private ?
		intDir = n;
	}

	public int getInt() {
		return intDir;
	}

}
