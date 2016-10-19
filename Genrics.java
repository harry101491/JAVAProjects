package com.harshit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author harshitpareek
 *	This program tries to Display the item 23 Don't use raw types in new code (release 1.5).
 */

public class Genrics 
{
	// This is the collection of the Stamps and should not contain the other types.
	// This is the raw type which is not type safe and not recommended to use.
	//private final Collection<Stamps> stamp = new ArrayList<Stamps>();
	public static void main(String args[])
	{
		/*Genrics gen = new Genrics();
		//gen.stamp.add(new Coin(3));
		/*Iterator itr = gen.stamp.iterator();
		while(itr.hasNext())
		{
			Stamps s = (Stamps)itr.next();
			System.out.println(s);
		}*/
		//List<String>[] stringList = new List<String>[1]; // this is a compile time error
		//List<?>[] stringList = new List<?>[1]; // but this is not a compile time error
		//List<Long> list = new ArrayList<Long>();
		
	}
}
/*
class Stamps
{
	private String name;
	public Stamps(String name)
	{
		this.name = name;
	}
}
class Coin
{
	private int value;
	public Coin(int value)
	{
		this.value = value;
	}
}*/