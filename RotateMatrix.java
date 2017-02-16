package com.harshit;

import java.util.Scanner;

public class RotateMatrix 
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the n for the n*n matrix");
		int n = sc.nextInt();
		int arr[][] = new int[n][n];
		System.out.println("Enter the n*n matrix");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				arr[i][j] = sc.nextInt();
			}
		}
		System.out.println("Before Rotation of matrix anti-clockwise");
		printArray(arr, n);
		rotateAntiClock(arr,n);
		System.out.println("After rotation of matrix anti-clockwise");
		printArray(arr,n);
		sc.close();
	}
	public static void rotateAntiClock(int arr[][], int n)
	{
		for(int layer=0;layer<n/2;layer++)
		{
			for(int i=layer;i<n-(layer+1);i++)
			{
				int temp = arr[layer][i];
				arr[layer][i] = arr[i][n-(layer+1)];
				arr[i][n-(layer+1)] = arr[n-(layer+1)][n-(i+1)];
				arr[n-(layer+1)][n-(i+1)] = arr[n-(i+1)][layer];
				arr[n-(i+1)][layer] = temp;
			}
		}
	}
	public static void printArray(int arr[][], int n)
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
}
