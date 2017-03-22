package com.harshit;

import java.util.Scanner;

public class MaxFlip 
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		int arr[] = {1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1};
//		int arr[] = {0, 0, 0, 1};
		int m;
		System.out.println("Enter value of m:");
		m = sc.nextInt();
		maxWindow(arr, m);
		sc.close();
	}
	// a method that finds the max window for which we can put the zeros in
	public static void maxWindow(int arr[], int m)
	{
		int wL = 0;
		int wR = 0;
		int zeros = 0;
		int bestL = 0;
		int maxWindow = 0;
		while(wR < arr.length)
		{
			if(arr[wR] == 0 && (zeros+1) <= m)
			{
				zeros++;
				wR++;
			}
			else if(arr[wR] == 0 && (zeros+1) > m)
			{
				if(arr[wL] == 0)
				{
					zeros--;
				}
				wL++;
			}
			else
			{
				wR++;
			}
			
			if(maxWindow < (wR-wL))
			{
				maxWindow = (wR-wL);
				bestL = wL;
			}
		}
		for(int i=bestL;i<(bestL+maxWindow);i++)
		{
			if(arr[i] == 0)
			{
				System.out.println(" the position at which we need to flip is: "+i);
			}
		}
	}	
}
