package com.harshit.JavaQuestions;

public class ConvertToBinary 
{
	public static void main(String args[])
	{
		//int ans = convertToNumber("1011");
		//System.out.println("the answer is:" + ans);
		String str = convertToBinary(8);
		System.out.println("the value is:"+str);
	}
	public static int convertToNumber(String str)
	{
		int sum = 0;
		int length = str.length();
		for(int i=length-1;i>=0;i--)
		{
			if(str.charAt(i) == '1')
			{
				sum += (int)Math.pow(2, length-1-i) * 1;
			}
		}
		return sum;
	}
	public static String convertToBinary(int value)
	{
		if(value == 0)
		{
			return "";
		}
		else
		{
			String str = null;
			if(value % 2 == 0)
			{
				value = value / 2;
				str = convertToBinary(value);
				str += "0";
			}
			else
			{
				value = value / 2;
				str = convertToBinary(value);
				str += "1";
			}
			return str;
		}
	}
}
