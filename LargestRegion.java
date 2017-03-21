package com.harshit;

import java.util.Scanner;

public class LargestRegion 
{
	// predefined rows and columns constants
	public static int ROWS;
	public static int COLUMNS;
	public static final int MAX_POS = 8;
	// main function
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the rows in the array");
		ROWS = sc.nextInt();
		System.out.println("Enter the rows in the array");
		COLUMNS = sc.nextInt();
		
		// predefined array
		int[][] array2D = {{0,0,1,1,0},{1,1,1,1,0},{0,1,0,0,0},{0,0,0,1,1}};
		
		boolean bool[][];
		bool = new boolean[ROWS][COLUMNS];
		for(int i=0;i<ROWS;i++)
		{
			for(int j=0;j<COLUMNS;j++)
			{
				bool[i][j] = false;
			}
		}	
		
		int max = Integer.MIN_VALUE;
		
		for(int i=0;i<ROWS;i++)
		{
			for(int j=0;j<COLUMNS;j++)
			{
				if(array2D[i][j] == 1)
				{
					int val = DFS(array2D, i, j, bool);
					System.out.println("the value of all ones that we can get:"+ val);
					if(val > max)
					{
						max = val;
					}
				}
			}
		}
		System.out.println("the value of max is:" + max);
		sc.close();
	}
	// method to validate the position at which DFS can be done
	public static boolean validate(int[][] arr, int row, int col, boolean[][] boolArr)
	{
		if(row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && arr[row][col] == 1 && !boolArr[row][col])
		{
			boolArr[row][col] = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	// function to call the DFS for the location
	public static int DFS(int[][] arr, int row, int col, boolean[][] boolArr)
	{
		int posRow[] = {-1, -1, -1, 0, 1, 1, 1, 0};
		int posCol[] = {-1, 0, 1, 1, 1, 0, -1, -1};
		int noOfCount = 0;
		for(int i=0;i<MAX_POS;i++)
		{
			if(validate(arr, row+posRow[i], col+posCol[i], boolArr))
			{
				noOfCount++;
				noOfCount += DFS(arr,row+posRow[i],col+posCol[i],boolArr);
			}
		}
		return noOfCount;
	}
}
