package com.harshit;

/**You are currently in practice mode. This is a demo only.

A zero-indexed array A consisting of N integers is given. An equilibrium index of this array is any integer P such that 0 ≤ P < N and the sum of elements of lower indices is equal to the sum of elements of higher indices, i.e. 
A[0] + A[1] + ... + A[P−1] = A[P+1] + ... + A[N−2] + A[N−1].
Sum of zero elements is assumed to be equal to 0. This can happen if P = 0 or if P = N−1.

For example, consider the following array A consisting of N = 8 elements:

  A[0] = -1
  A[1] =  3
  A[2] = -4
  A[3] =  5
  A[4] =  1
  A[5] = -6
  A[6] =  2
  A[7] =  1
P = 1 is an equilibrium index of this array, because:

A[0] = −1 = A[2] + A[3] + A[4] + A[5] + A[6] + A[7]
P = 3 is an equilibrium index of this array, because:

A[0] + A[1] + A[2] = −2 = A[4] + A[5] + A[6] + A[7]
P = 7 is also an equilibrium index, because:

A[0] + A[1] + A[2] + A[3] + A[4] + A[5] + A[6] = 0
and there are no elements with indices greater than 7.

P = 8 is not an equilibrium index, because it does not fulfill the condition 0 ≤ P < N.

Write a function:

class Solution { public int solution(int[] A); }

that, given a zero-indexed array A consisting of N integers, returns any of its equilibrium indices. The function should return −1 if no equilibrium index exists.

For example, given array A shown above, the function may return 1, 3 or 7, as explained above.

Assume that:

N is an integer within the range [0..100,000];
each element of array A is an integer within the range [−2,147,483,648..2,147,483,647].
Complexity:

expected worst-case time complexity is O(N);
expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
Elements of input arrays can be modified.
**/
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class NumberAverage 
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the value of positive integer:");
		int n = sc.nextInt();
		int num = n;
		int len = 0;
		while(num > 0)
		{
			num = num / 10;
			len++;
		}
		double max = 0;
		for(int i=1;i<len;i++)
		{
			int numPos1 = getDigit(n, 4, len);
			int numPos2 = getDigit(n, 5, len);
			double avg = Math.round(((double)numPos1+numPos2)/2);
			double val = buildNumber(n, 4, (int)avg, len);
			if(max < val)
			{
				max = val;
			}
		}
		System.out.println("the maximum value is:" +max);
		sc.close();
	}
	public static double buildNumber(int num, int pos, int digit, int len)
	{
		Map<Integer,Integer> map = new HashMap<>(); 
		for(int i=1;i<=len;i++)
		{
			if(i == pos)
			{
				map.put(i, digit);
			}
			else if(i < pos)
			{
				int digAtI = getDigit(num, i, len);
				if(digAtI > 0)
				{
					map.put(i,digAtI);
				}
			}
			else if(i == pos+1)
			{
				
			}
			else
			{
				int digAtI = getDigit(num, i, len);
				if(digAtI > 0)
				{
					map.put(i-1,digAtI);
				}
			}
		}
		double sum = 0;
		for(Map.Entry<Integer, Integer> mem : map.entrySet())
		{
			sum += mem.getValue()*Math.pow(10,mem.getKey()-1);
		}
		return sum;
	}
	public static void printMap(Map<Integer,Integer> map)
	{
		for(Map.Entry<Integer, Integer> set : map.entrySet())
		{
			System.out.println(" ( "+ set.getKey()+" , "+set.getValue()+" ) ");
		}
	}
	public static int getDigit(int num, int pos, int len)
	{
		if(pos > len)
		{
			return -1;
		}
		if(pos == 1)
		{
			return num%10;
		}
		else
		{
			return getDigit(num/10,pos-1,len);
		}
	}
}

/*public static void main(String args[])
{
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the string for which you want to print subsequence");
	String str = sc.nextLine();
	Subsequance s = new Subsequance();
	int low = 0;
	int high = str.length();
	System.out.println(s.allSubseqence(str,low,high));
}*/
/*public String allSubseqence(String str, int low, int high)
{
	if((high-low) == 0)
	{
		return "";
	}
	else if((high-low) == 1)
	{
		return str.substring(low, high);
	}
	else
	{
		char ch = str.charAt(low);
		String str1 = allSubseqence(str, 0, low) + ch + allSubseqence(str, low+1, high);
		String str2 = allSubseqence(str, 0, low) + allSubseqence(str, low+1, high);
		return str1 +" "+  str2;
	}
}*/



/*Scanner sc = new Scanner(System.in);
System.out.println("Enter the positive integer:");
int n = sc.nextInt();
for(int i=1;i<=n;i++)
{
	for(int j=1;j<=n;j++)
	{
		System.out.print(i*j + " ");
	}
	System.out.println();
}
sc.close();
*/
/*int A[] = {-1, 3, -4, 5, 1, -6, 2, 1};
int len = A.length;
double sumLow = 0;
double sumHigh = 0;
for(int i=0;i<len;i++)
{
	sumHigh += A[i];
}
//System.out.println("the value of sumHigh is:"+sumHigh);
for(int i=0;i<len;i++)
{
	if(i == 0)
	{
		if(sumHigh-A[i] == 0)
		{
			System.out.println("the equilibrium is reached at "+ (i));
		}
	}
	else if(i == len-1)
	{
		if(sumLow+A[i-1] == 0)
		{
			System.out.println("the equilibrium is reached at "+ (i));
		}
	}
	else
	{
		sumLow += A[i-1];
		sumHigh -= A[i-1];
		if(sumLow == (sumHigh-A[i]))
		{
			System.out.println("the eqilibrium is reached at "+(i));
		}
	}
}*/
/**You are currently in practice mode. This is a demo only.

A zero-indexed array A consisting of N integers is given. An equilibrium index of this array is any integer P such that 0 ≤ P < N and the sum of elements of lower indices is equal to the sum of elements of higher indices, i.e. 
A[0] + A[1] + ... + A[P−1] = A[P+1] + ... + A[N−2] + A[N−1].
Sum of zero elements is assumed to be equal to 0. This can happen if P = 0 or if P = N−1.

For example, consider the following array A consisting of N = 8 elements:

  A[0] = -1
  A[1] =  3
  A[2] = -4
  A[3] =  5
  A[4] =  1
  A[5] = -6
  A[6] =  2
  A[7] =  1
P = 1 is an equilibrium index of this array, because:

A[0] = −1 = A[2] + A[3] + A[4] + A[5] + A[6] + A[7]
P = 3 is an equilibrium index of this array, because:

A[0] + A[1] + A[2] = −2 = A[4] + A[5] + A[6] + A[7]
P = 7 is also an equilibrium index, because:

A[0] + A[1] + A[2] + A[3] + A[4] + A[5] + A[6] = 0
and there are no elements with indices greater than 7.

P = 8 is not an equilibrium index, because it does not fulfill the condition 0 ≤ P < N.

Write a function:

class Solution { public int solution(int[] A); }

that, given a zero-indexed array A consisting of N integers, returns any of its equilibrium indices. The function should return −1 if no equilibrium index exists.

For example, given array A shown above, the function may return 1, 3 or 7, as explained above.

Assume that:

N is an integer within the range [0..100,000];
each element of array A is an integer within the range [−2,147,483,648..2,147,483,647].
Complexity:

expected worst-case time complexity is O(N);
expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
Elements of input arrays can be modified.
**/
