package peter.world.explorer;

public class Boot {
	public Boot() {

		Runner runner = new Runner();
		runner.Load();

	}

	public static void main(String[] args) {
		Boot b = new Boot();
	}
}
