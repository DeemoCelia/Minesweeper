public class Minesweeper {
	public static void main(String[] args) {
		GameData gd = new GameData(10,10,10);
		GameWindow gw = new GameWindow();
		GameControl gc = new GameControl(gd,gw);
		gc.GameStart();

	}
}
