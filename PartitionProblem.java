package com.harshit.dynamicprogramming;

/**
 * Given a non-empty array containing only positive integers
 * find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
 * Note:
 * Each of the array element will not exceed 100.
 * The array size will not exceed 200.
 * @author harshitpareek
 *
 */
public class PartitionProblem 
{
	public boolean isSubSet(int[] set, int n, int sum)
	{
		// create a subset array
		boolean subset[][] = new boolean[sum+1][n+1];
		
		// if the n becomes 0 then result will be false
		for(int i=1;i<=sum;i++)
		{
			subset[i][0] = false;
		}
		
		// if sum becomes zero then the result will be true
		for(int j=1;j<=n;j++)
		{
			subset[0][j] = true;
		}
		
		// populating the array in the polynomial time
		for(int i=1;i<=sum;i++)
		{
			for(int j=1;j<=n;j++)
			{
				if(i >= set[j-1])
				{
					subset[i][j] = subset[i][j-1] || subset[i-set[j-1]][j-1];
				}
				else
				{
					subset[i][j] = subset[i][j-1];
				}
			}
		}
		return subset[sum][n];
	}
	public static void main(String args[])
	{
		// answer should be true
		int set[] = {1,2,3};
		int sum = 10;
		PartitionProblem p = new PartitionProblem();
		boolean result = p.isSubSet(set,set.length, sum);
		System.out.println("the result is : "+result);
	}
}
