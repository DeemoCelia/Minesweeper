
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
					int clickMsg = gameWindow.getClickedMsg().getFirst();
					gameWindow.getPressPositionRow().removeFirst();
					gameWindow.getPressPositionColumn().removeFirst();
					gameWindow.getClickedMsg().removeFirst();

					int gridValue = gameData.getMineMap()[pressPositionRow][pressPositionColumn];

					if(clickMsg == 0 && gridValue == -1)
					{
						System.out.println("lose");
						gameWindow.changeToMenuWindow();
						gameData.clearMap();
					}
					else if(clickMsg == 1){
						if(gridValue == -1)
							gameData.setRemainingMineCount(gameData.getRemainingMineCount()-1);
					}
					else if(clickMsg == 2){
						if(gridValue == -1){
							gameData.setRemainingMineCount(gameData.getRemainingMineCount()+1);
						}
					}
				}

				if(gameData.getRemainingMineCount() == 0)
				{
					System.out.println("win");
					gameData.clearMap();
					gameWindow.changeToMenuWindow();
				}
			}
		}
	}
}
