package com.harshit;

import java.util.AbstractList;
/**
 * 
 * @author harshitpareek
 * This program depicts that how it is dangerous to call an overridable method in the 
 * constructor of the superclass. As, we know that subclass constructor calls the superclass
 * constructor first but what if the this constructor calls an overridable method then results
 * will be unexpected.
 */
import java.util.Date;
import java.util.List;

public class NotInvokeOverridable 
{
	public static void main(String args[])
	{
		Sub sub = new Sub();
		sub.overrideMe();
	}
	// this method is the best example of the skeletal implementation for every interface
	// that provide the basic implementation for the programmer and it also help the
	// programmer to provide its own implementation. This example is for the Adapter.
	// that allows an int array to be looked like an list of Integer.
	//** this is a static factory for the list of integers for a given int array
	public static List<Integer> intArrayToList(int arr[])
	{
		if(arr == null)
		{
			throw new NullPointerException("Null pointer has discovered");
		}
		// anonymous class 
		return new AbstractList<Integer>() 
		{
			@Override
			public Integer get(int i)
			{
				return arr[i]; //autoboxing
			}
			@Override
			public Integer set(int i,Integer val)
			{
				int oldValue = arr[i];
				arr[i] = val; //unboxing
				return oldValue; // autoboxing
			}
			@Override
			public int size()
			{
				return arr.length;
			}
		};
	}
}
class Super
{
	public Super()
	{
		overrideMe();
	}
	public void overrideMe()
	{
		
	}
}
class Sub extends Super
{
	private Date date;
	public Sub()
	{
		date = new Date();
	}
	public void overrideMe()
	{
		System.out.println(date);
	}
}
