import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class GameWindow extends JFrame {
	private JPanel panel;

	public int getGameSelect() {
		return gameSelect;
	}

	public void setGameSelect(int gameSelect) {
		this.gameSelect = gameSelect;
	}

	public boolean isGaming() {
		return isGaming;
	}

	public void setGaming(boolean gaming) {
		isGaming = gaming;
	}

	public boolean isReset() {
		return isReset;
	}

	public void setReset(boolean reset) {
		isReset = reset;
	}

	public Vector<Integer> getPressMinePositionRow() {
		return pressMinePositionRow;
	}

	public void setPressMinePositionRow(Vector<Integer> pressMinePositionRow) {
		this.pressMinePositionRow = pressMinePositionRow;
	}

	public Vector<Integer> getPressMinePositionColumn() {
		return pressMinePositionColumn;
	}

	public void setPressMinePositionColumn(Vector<Integer> pressMinePositionColumn) {
		this.pressMinePositionColumn = pressMinePositionColumn;
	}

	private int gameSelect; //0: init window; 1: game;
	private boolean isGaming;
	private boolean isReset;
	private Vector<Integer> pressMinePositionRow;
	private Vector<Integer> pressMinePositionColumn;
	GameWindow(){
		pressMinePositionRow = new Vector<>();
		pressMinePositionColumn = new Vector<>();
	}

	public void initGameMenu()
	{
		panel = new JPanel();
		setTitle("MineSweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawMenu();

		add(panel);

		pack();
		setVisible(true);
	}

	private void drawMenu()
	{
		setPreferredSize(new Dimension(500,400));
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(400,300));
		JButton button1 = new JButton("初级");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSelect = 1;
				isGaming = true;
			}
		});
		panel.add(button1);
	}

	private void drawGame(int remaingMineCount, int[][] mineMap)
	{
		panel.removeAll();
		panel.setLayout(null);

		Dimension panelSize = new Dimension(20+20+mineMap[0].length*10,50+mineMap.length*10+10);
		panel.setSize(panelSize);
		int mineMapPositionX = 20;
		int mineMapPositionY = 50;

		Rectangle windowRemainingMineCountSize = new Rectangle(20,20,30,15);
		Rectangle windowResetGameSize = new Rectangle(panel.getX()/2-15,15,30,30);
		Rectangle windowUsedTimeSize = new Rectangle(panel.getX()-20,20,30,15);
		Dimension windowMineButtonSize = new Dimension(10,10);

		JLabel labelRemainingMineCount = new JLabel(remaingMineCount+"");
		labelRemainingMineCount.setBounds(windowRemainingMineCountSize);

		JButton resetButton = new JButton("重新开始");
		resetButton.setBounds(windowResetGameSize);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isReset = true;
			}
		});

		JLabel labelUsedTime = new JLabel("0");
		labelUsedTime.setBounds(windowUsedTimeSize);

		panel.add(labelRemainingMineCount);
		panel.add(resetButton);
		panel.add(labelUsedTime);

		int row = mineMap.length;
		int column = mineMap[0].length;

		for(int r = 0;r<row;++r)
		{
			for(int c = 0;c<column;++c)
			{
				MineButton mb = new MineButton();
				mb.setRowPosition(r);
				mb.setColumnPosition(c);
				mb.setShow(false);
				mb.setBounds(mineMapPositionX+r*10,mineMapPositionY+c*10,10,10);
				mb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MineButton theMB = (MineButton) e.getSource();
						if(!theMB.isShow())
						{
							pressMinePositionRow.add(theMB.getRowPosition());
							pressMinePositionColumn.add(theMB.getColumnPosition());
							theMB.setShow(true);
						}
					}
				});
				panel.add(mb);
			}
		}
	}
}

class MineButton extends JButton
{
	private int rowPosition;
	private int columnPosition;
	private boolean isShow;

	public int getRowPosition() {
		return rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public int getColumnPosition() {
		return columnPosition;
	}

	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean show) {
		isShow = show;
	}
}