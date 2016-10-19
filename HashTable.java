package com.harshit;
/**
 * 
 * @author harshitpareek
 * This program tries to show the some problem that we face when we try to clone the object when 
 * fields contain some class or some structure that needs to be deep copied.
 * Item 11
 *
 */

public class HashTable implements Cloneable 
{
	// instance fields
	private Entry[] elements; // Entry is a key-value pair of the hash table
	// the static class that defines the Entry class
	public static class Entry
	{
		private Object key; // key of the entry
		private Object value; // value of the entry
		private Entry link; // the link to the next entry
		
		public Entry(Object key,Object value,Entry link) 
		{
			this.key = key;
			this.value = value;
			this.link = link;
		}
		// method for the deep copy provided in the Hash Table class using iteration
		public Entry deepCopy()
		{
			Entry result = new Entry(key, value, link);
			Entry p = result;
			for(;p.link != null;p=p.link)
			{
				p.link = new Entry(p.link.key,p.link.value,p.link.link);
			}
			return result;
		}
		/*
		// method for the deep copy provided in the Hash Table class using recursion
		public Entry deepCopy()
		{
			// it return the copy of the every Entry linked list present for every 
			// array element
			return new Entry(key, value, link == null ? null : link.deepCopy());
		}
		*/
		
	}
	// clone method for the hash table class
	/*
	@Override
	public HashTable clone()
	{
		try
		{
			HashTable result = (HashTable)super.clone();
			 // this is wrong as the HashTable contain a structure inside of it
			// so it needs to deep copied rather than just copying the references
			// to resolve this HashTable provides a method for deep copy
			result.elements = elements.clone(); 
			return result;
		}
		catch(CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}*/
	// the iterative method of doing the deep copy
	@Override
	public HashTable clone()
	{
		try
		{
			HashTable result = (HashTable)super.clone();
			result.elements = new Entry[this.elements.length];
			return result;
		}
		catch(CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}
	/*
	// recursive method of doing the deep copy
	@Override
	public HashTable clone()
	{
		try
		{
			HashTable result = (HashTable)super.clone();
			result.elements = new Entry[this.elements.length];
			for(int i = 0;i < result.elements.length; i++)
			{
				// this problematic because it class the stack for every entry
				// in the bucket that might lead to stack overflow if this hash
				// table is quite big.
				result.elements[i] = this.elements[i].deepCopy();
			}
			return result;
		}
		catch(CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}*/
}
