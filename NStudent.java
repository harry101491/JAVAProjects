package com.harshit;

public class NStudent
{
	public static void main(String args[])
	{
		int arr[] = {6, 2, 3, 8};
		int val = returnElement(arr);
		System.out.println("the value of val is: "+val);
//		String inputString = "abac";
//		if(checkPalindrome(inputString))
//		{
//			System.out.println("Retured true");
//		}
//		else
//		{
//			System.out.println("Returend false");
//		}
		
	}
	public static int returnElement(int[] arr)
	{
		int len = arr.length;
		for(int i=0;i<len-1;i++)
		{
			int min = arr[i];
			int index = i;
			for(int j=i+1;j<len;j++)
			{
				if(min > arr[j])
				{
					min = arr[j];
					index = j;
				}
			}
			int temp = arr[i];
			arr[i] = arr[index];
			arr[index] = temp;
		}
		int counter = 0;
		for(int i=0;i<len-1;i++)
		{
			int diff = arr[i+1]-arr[i];
			if(arr[i+1]-arr[i] > 1)
			{
				counter += (diff-1);
			}
		}
		return counter;
//		if(m == 1)
//		{
//			return m*m;
//		}
//		else
//		{
//			return ((m-1)*4) + returnElement(m-1); 
//		}
//		int len = arr.length;
//		int max = Integer.MIN_VALUE;
//		for(int i=0;i<len-1;i++)
//		{
//			int product = arr[i]*arr[i+1];
//			if(product > max)
//			{
//				max = product;
//			}
//		}
//		return max;
	}
	/*public static boolean checkPalindrome(String input)
	{
		int low = 0;
		int high = input.length() - 1;
		boolean flag = false;
		while(low <= high)
		{
			if(input.charAt(low) == input.charAt(high))
			{
				flag = true;
			}
			else
			{
				flag = false;
				break;
			}
			low++;
			high--;
		}
		return flag;
	}*/
}