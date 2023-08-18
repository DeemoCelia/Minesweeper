import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class GameData {
	private int time;
	private int remainingMineCount;
	private int[][] mineMap;


	GameData(int row, int column, int mineCount)
	{
		time = 0;
		remainingMineCount = mineCount;
		mineMap = new int[row][column];
	}

	void clearMap()
	{
		mineMap = null;
	}

	void generateMineMap(int row,int column,int mineCount)
	{

		mineMap = new int[row][column];

		int grids = row*column;
		double minePercent = (double) mineCount /grids;

		Random random = new Random();

		int generateCount = 0;
		for(int r = 0;r<row;++r)
		{
			for(int c = 0;c<column;++c)
			{
				if(generateCount == mineCount)
					break;
				if(random.nextDouble()<minePercent)
				{
					generateCount+=1;
					mineMap[r][c] = -1;
					accAroundGrid(mineMap,r,c);
				}
			}
		}

		if(generateCount<mineCount)
		{
			while (generateCount<mineCount) {
				int randomRow = Math.abs(random.nextInt()%row);
				int randomColumn = Math.abs(random.nextInt()%column);
				if(mineMap[randomRow][randomColumn]!=-1)
				{
					mineMap[randomRow][randomColumn] = -1;
					accAroundGrid(mineMap,randomRow,randomColumn);
					generateCount+=1;
				}
			}
		}
	}

	int[][] getMineMap()
	{
		return mineMap;
	}

	int getRemainingMineCount()
	{
		return remainingMineCount;
	}

	void setRemainingMineCount(int curRemainingMineCount)
	{
		remainingMineCount = curRemainingMineCount;
	}

	int getTime()
	{
		return time;
	}

	void setTime(int curTime)
	{
		time = curTime;
	}

	void showMineMap()
	{
		for (int[] ints : mineMap) {
			System.out.println(Arrays.toString(ints));
		}
	}

	private void accAroundGrid(int[][] mineMap,int r,int c)
	{
		int row = mineMap.length;
		int column = mineMap[0].length;

		if(r-1>=0 && mineMap[r-1][c]!=-1)
			mineMap[r-1][c]+=1;
		if(c-1>=0 && mineMap[r][c-1]!=-1)
			mineMap[r][c-1]+=1;
		if(r+1<row && mineMap[r+1][c]!=-1)
			mineMap[r+1][c]+=1;
		if(c+1<column && mineMap[r][c+1]!=-1)
			mineMap[r][c+1]+=1;

		if(r-1>=0 && c-1>=0 && mineMap[r-1][c-1]!=-1)
			mineMap[r-1][c-1]+=1;
		if(r+1<row && c-1>=0 && mineMap[r+1][c-1]!=-1)
			mineMap[r+1][c-1]+=1;
		if(r-1>=0 && c+1 < column && mineMap[r-1][c+1]!=-1)
			mineMap[r-1][c+1]+=1;
		if(r+1<row && c+1< column && mineMap[r+1][c+1]!=-1)
			mineMap[r+1][c+1]+=1;
	}
}
