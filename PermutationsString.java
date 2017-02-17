package com.harshit;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class PermutationsString 
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number for which you have to purmute");
		int n = sc.nextInt();
		Map<Integer, ArrayList<String>> mem = new HashMap<>();
		permute(n,mem);
		ArrayList<String> list = mem.get(n);
		for(String str : list)
		{
			System.out.println(str);
		}
		sc.close();
	}
	public static void permute(int n, Map<Integer,ArrayList<String>> mem)
	{
		// base condition if the length of the string is 1.
		if(n == 1)
		{
			ArrayList<String> l = new ArrayList<>();
			l.add("1");
			mem.put(1,l);
			return;
		}
		else
		{
			if(mem.get(n-1) == null)
			{
				permute(n-1,mem);
			}
			String strCh = String.valueOf(n);
			ArrayList<String> list1 = new ArrayList<>();
			mem.put(n, list1);
			ArrayList<String> strN = mem.get(n-1);
			for(String str: strN)
			{
				for(int i=0;i<n;i++)
				{
					String str1 = str.substring(0, i) + strCh + str.substring(i, str.length());
					list1.add(str1);
				}
			}
		}
	}
}
