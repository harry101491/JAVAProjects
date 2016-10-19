package com.harshit;
import java.util.Collection;
import java.util.Iterator;
/**
 * 
 * @author harshitpareek
 * This program implements the Item 16 in the Effective JAVA. This program has Wrapper class
 * that is wrapped around the Set Interface. This program also provides the benefits of using
 * composition in place of inheritance.
 * This is the best possible way to add the functionality and not facing the problems 
 * done by the inheritance.  
 */
import java.util.Set;

public class InstrumentedSet<E> extends ForwardingSet<E> 
{
	private int addCount = 0;
	public InstrumentedSet(Set<E> set)
	{
		super(set);
	}
	@Override
	public boolean add(E e)
	{
		addCount++;
		return super.add(e);
	}
	
}
class ForwardingSet<E> implements Set<E>
{
	private final Set<E> set;
	public ForwardingSet(Set<E> set)
	{
		this.set = set;
	}
	public boolean add(E e)
	{
		return set.add(e);
	}
	public void clear()
	{
		set.clear();
	}
	public boolean contains(Object o)
	{
		return set.contains(o);
	}
	public boolean isEmpty()
	{
		return set.isEmpty();
	}
	public int size()
	{
		return set.size();
	}
	public Iterator<E> iterator()
	{
		return set.iterator();
	}
	public boolean remove(Object o)
	{
		return set.remove(o);
	}
	public boolean containsAll(Collection<?> c)
	{
		return set.containsAll(c);
	}
	public boolean addAll(Collection<? extends E> c)
	{
		return set.addAll(c);
	}
	public boolean removeAll(Collection<?> c)
	{
		return set.removeAll(c);
	}
	public boolean retainAll(Collection<?> c)
	{
		return set.retainAll(c);
	}
	public Object[] toArray()
	{
		return set.toArray();
	}
	public <T> T[] toArray(T[] a)
	{
		return set.toArray(a);
	}
	@Override
	public boolean equals(Object o)
	{
		return set.equals(o);
	}
	@Override
	public int hashCode()
	{
		return set.hashCode();
	}
	@Override
	public String toString()
	{
		return set.toString();
	}
}
