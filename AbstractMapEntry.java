package com.harshit;

/**
 * 
 * @author harshitpareek
 * this program shows the skeletal implementation of the Map.Entry Interface.
 */
import java.util.Map;
//import static java.lang.Math.*;

// Non instant utility class to provide the constants needed by the class
class PhysicalContants
{
	private PhysicalContants()
	{
		
	}
	public static final double AVOGRADO_NUMBER = 6.02214e23;
	public static final double BOLTZMANN_CONSTANT = 1.38e-23;
	public static final double ELECTRON_MASS = 9.10e-31;
}
public abstract class AbstractMapEntry<K,V> implements Map.Entry<K,V> 
{
	// primitive methods that are left out for implementation by the subclasses
	public abstract K getKey();
	public abstract V getValue();
	// Entries in the modifiable maps should override this method
	public V setValue(V v)
	{
		throw new UnsupportedOperationException();
	}
	// Equals method that follow the common contract followed by the every entry in the Map.Entry
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Map.Entry))
		{
			return false;
		}
		Map.Entry<?, ?> map = (Map.Entry<?, ?>)o;
		// In this we will make a helper method equals that will compare the key and value pair of the this and o
		// We include the helper function because we do not want to call the overridden method for the "self-use".
		return equals(getKey(),map.getKey()) && equals(getValue(),map.getValue());
	}
	private static boolean equals(Object o1,Object o2)
	{
		// this logic first checks if o1 is null or not if it not null then we call equals method 
		// otherwise we check the o2 for null if both o1 and o2 are null then it returns true.
		return o1==null ? o2==null : o1.equals(o2);
	}
	// this method override the hashCode to maintain the hashCode contract for this class. 
	@Override
	public int hashCode()
	{
		return hashCode(getKey()) ^ hashCode(getValue());
	}
	// this is the helper function for the hashCode method
	private static int hashCode(Object o)
	{
		return o == null ? 0 : o.hashCode();
	}
}
