import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class GameWindow{
	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	private boolean gameStart;
	private boolean gameRunning;

	public LinkedList<Integer> getPressPositionRow() {
		return pressPositionRow;
	}

	public LinkedList<Integer> getPressPositionColumn() {
		return pressPositionColumn;
	}

	public LinkedList<Integer> getClickedMsg() {
		return clickedMsg;
	}

	private LinkedList<Integer> pressPositionRow;
	private LinkedList<Integer> pressPositionColumn;
	private LinkedList<Integer> clickedMsg; //0: leftClick; 1:rightClickMark; 2:rightClickUnmark

	public MenuWindow menuWindow;
	public MineMapWindow mineMapWindow;
	public GameWindow(){
		menuWindow = new MenuWindow(this);
		pressPositionRow = new LinkedList<>();
		pressPositionColumn = new LinkedList<>();
		clickedMsg = new LinkedList<>();
	}

	public void changeToGameWindow(int[][] map, int number)
	{
		mineMapWindow = new MineMapWindow(this,map,number);
		menuWindow.setVisible(false);
		mineMapWindow.setVisible(true);
	}

	public void changeToMenuWindow()
	{
		if(mineMapWindow!=null)
		{
			mineMapWindow.dispose();
			mineMapWindow = null;
		}
		menuWindow.setVisible(true);
	}
}

class MenuWindow extends JFrame{
	private GameWindow gameWindow;

	MenuWindow(GameWindow gw){
		gameWindow = gw;

		gameWindow.setGameStart(false);
		gameWindow.setGameRunning(false);
		setTitle("MineSweeper");
		setSize(600,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		JButton startButton = new JButton("start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameWindow.setGameStart(true);
			}
		});
		add(startButton);
	}
}

class MineMapWindow extends JFrame{

	private GameWindow gameWindow;
	public int minesNumber;
	public int initMinesNumber;
	private int[][] mineMap;

	private JLabel remainingMinesCount;
	MineMapWindow(GameWindow gw, int[][] map, int number){
		gameWindow = gw;
		mineMap = map;
		minesNumber = number;
		initMinesNumber = number;

		gameWindow.setGameStart(false);
		gameWindow.setGameRunning(true);
		setTitle("MineSweeper");
		setSize(600,800);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		remainingMinesCount = new JLabel(minesNumber+"");
		remainingMinesCount.setBounds(50,50,40,20);
		remainingMinesCount.setVisible(true);
		add(remainingMinesCount);

		int mapRow = mineMap.length;
		int mapColumn = mineMap[0].length;
		int mapPositionRow = 50;
		int mapPositionColumn = 150;

		for(int r = 0; r < mapRow; ++r)
		{
			for(int c = 0; c < mapColumn; ++c)
			{
				MineButton mb = new MineButton();
				mb.isShowing = false;
				mb.isMarked = false;
				mb.positionRow = r;
				mb.positionColumn = c;
				mb.value = mineMap[r][c];
				mb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						MineButton mineButton = (MineButton) e.getSource();
						if(SwingUtilities.isLeftMouseButton(e)) {
							if(!mineButton.isShowing && !mineButton.isMarked)
							{
								mineButton.isShowing = true;
								if(mineButton.value == -1)
									mineButton.setText("x");
								else
									mineButton.setText(mineButton.value + "");
								gameWindow.getClickedMsg().add(0);
								gameWindow.getPressPositionRow().add(mineButton.positionRow);
								gameWindow.getPressPositionColumn().add(mineButton.positionColumn);
							}
						}else if(SwingUtilities.isRightMouseButton(e)) {
							if(!mineButton.isShowing)
							{
								if(minesNumber > 0 && !mineButton.isMarked){
									mineButton.isMarked = true;
									mineButton.setText("M");
									minesNumber-=1;
									gameWindow.getClickedMsg().add(1);
									gameWindow.getPressPositionRow().add(mineButton.positionRow);
									gameWindow.getPressPositionColumn().add(mineButton.positionColumn);
								}else if(minesNumber < initMinesNumber && mineButton.isMarked){
									mineButton.isMarked = false;
									mineButton.setText("");
									minesNumber+=1;
									gameWindow.getClickedMsg().add(2);
									gameWindow.getPressPositionRow().add(mineButton.positionRow);
									gameWindow.getPressPositionColumn().add(mineButton.positionColumn);
								}
								remainingMinesCount.setText(minesNumber+"");
							}
						}

					}
				});
				mb.setVisible(true);
				mb.setBounds(mapPositionRow+r*50,mapPositionColumn+c*50,50,50);
				add(mb);
			}
		}


	}
}

class MineButton extends JButton{
	public int positionRow;
	public int positionColumn;
	public boolean isShowing;
	public boolean isMarked;
	public int value;
}