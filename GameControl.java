
public class GameControl {
	private GameData gameData;
	private GameWindow gameWindow;


	GameControl(GameData gdata,GameWindow gw)
	{
		gameData = gdata;
		gameWindow = gw;
	}

	void GameStart()
	{
		gameWindow.changeToMenuWindow();

		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignored) {}

			if(gameWindow.isGameStart())
			{
				gameData.generateMineMap(10,10,10);
				gameWindow.changeToGameWindow(gameData.getMineMap(),gameData.getRemainingMineCount());
				gameWindow.setGameStart(false);
				gameWindow.setGameRunning(true);
			}

			if(gameWindow.isGameRunning())
			{
				if(!gameWindow.getPressPositionRow().isEmpty())
				{
					int pressPositionRow = gameWindow.getPressPositionRow().getFirst();
					int pressPositionColumn = gameWindow.getPressPositionColumn().getFirst();
					gameWindow.getPressPositionRow().removeFirst();
					gameWindow.getPressPositionColumn().removeFirst();

					if(gameData.getMineMap()[pressPositionRow][pressPositionColumn] == -1)
					{
						gameWindow.changeToMenuWindow();
						gameData.clearMap();
					}
				}
			}
		}
	}
}
