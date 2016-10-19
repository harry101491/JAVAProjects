package com.harshit;

import java.util.Comparator;

/**
 * 
 * @author harshitpareek
 * This class represent the function object implementation. 
 */

public class FunctionObject 
{

}
// an instance of this class can be used as a function pointer and this instance can be called
// as the concrete strategy.
// These type of classes are state less because you need to have the instance variable for 
// maintaining a state.Because of this reason this class should be singleton and we should 
// avoid the costly object creation multiple times.
class StringComparator implements Comparator<String>
{
	// no instance can be made by the private constructor
	private StringComparator()
	{
		
	}
	// providing the object static to be used by all the uses required for the function object
	public static StringComparator INSTANCE = new StringComparator(); 
	public int compare(String s1,String s2)
	{
		return s1.length() - s2.length();
	}
}