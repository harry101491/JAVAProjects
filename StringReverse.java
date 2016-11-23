package com.harshit.JavaQuestions;

public class StringReverse 
{
	public static void main(String args[])
	{
		/*String original = "harshit";
		StringBuilder builder = new StringBuilder();
		for(int i=original.length()-1;i>=0;i--)
		{
			builder.append(original.charAt(i));
		}
		System.out.println("the reverse value is:"+builder.toString());
		*/
		String reverse = recursiveReverse("harshit");
		System.out.println("the reverse value is:"+reverse);
	}
	public static String recursiveReverse(String str)
	{
		StringBuilder newString = new StringBuilder();
		if(str.length() == 0)
		{
			return "";
		}
		else
		{
			int length = str.length();
			newString.append(str.charAt(length-1));
			str = str.substring(0, length-1);
			newString.append(recursiveReverse(str));
		}
		return newString.toString();
	}
}
