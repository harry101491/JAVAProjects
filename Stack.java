package com.harshit;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 
 * @author harshitpareek
 * The implementation of the Stack class that tries to resolve the problem 
 * of cloning when object contains the array as a field. 
 * Item 10
 * Date :- 08/17/2016 
 * This Program tries to convert the Collection in to generic.
 */
/*
public class Stack implements Cloneable 
{
	private Object[] elements;
	private int top;
	private static final int DEFAULT_SIZE = 16;
	// constructor
	public Stack()
	{
		this.elements = new Object[DEFAULT_SIZE];
	}
	// push method
	public void push(Object o)
	{
		ensureCapacity();
		this.elements[top++] = o;
	}
	// Ensures Stack never overflows and if it does that increase the size of the array
	public void ensureCapacity()
	{
		if(elements.length == top)
		{
			this.elements = Arrays.copyOf(elements, 2*DEFAULT_SIZE+1);
		}
	}
	// pop method
	public Object pop()
	{
		if(top == 0)
		{
			throw new EmptyStackException();
		}
		Object result = elements[top];
		elements[top] = null;
		top--;
		return result;
	}
	// implementing the clone method
	public Object clone()
	{
		try
		{
			// type casting cloned object returned from the 
			// super class
			Stack stack = (Stack)super.clone();
			// cloning each and every element of the array that is field of the object
			stack.elements = elements.clone();
			return stack;  // returning the modified object 
		}
		catch(CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}
}*/
/**
 * 
 * @author harshitpareek
 *	Program for the item 26. Making it for generic.
 */
public class Stack<E> implements Cloneable 
{
	private E[] elements;
	private int top;
	private static final int DEFAULT_SIZE = 16;
	// constructor
	@SuppressWarnings("unchecked")
	public Stack()
	{
		// because i am sure that the type cast is safe as the elements are private
		// and there is no method taking it and changing it  unwontedly. We can say that it 
		// fine to add the suppress warning in it.
		this.elements = (E[]) new Object[DEFAULT_SIZE];
	}
	// push method
	public void push(E o)
	{
		ensureCapacity();
		this.elements[top++] = o;
	}
	// Ensures Stack never overflows and if it does that increase the size of the array
	public void ensureCapacity()
	{
		if(elements.length == top)
		{
			this.elements = Arrays.copyOf(elements, 2*DEFAULT_SIZE+1);
		}
	}
	// pop method
	public E pop()
	{
		if(top == 0)
		{
			throw new EmptyStackException();
		}
		E result = elements[top];
		elements[top] = null;
		top--;
		return result;
	}
	// implementing the clone method
	public Object clone()
	{
		try
		{
			// type casting cloned object returned from the 
			// super class
			Stack stack = (Stack)super.clone();
			// cloning each and every element of the array that is field of the object
			stack.elements = elements.clone();
			return stack;  // returning the modified object 
		}
		catch(CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}
}