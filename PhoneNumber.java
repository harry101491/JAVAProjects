/**
 * 
 * @author harshitpareek
 * This program is about the overriding of the hash code method and what contracts must
 * be followed in the implementation of the same.
 * If you override the equals method then you should must implement the hashCode to
 * obey the contract and not get the undesirable results.
 * This program also contains the method compareTo to provide the natural ordering of the
 * PhoneNumber class. Note:- We should use the < or > for comparison of the primitive types
 * other than Double or Float for float and double we should use Float.compare and Double.compare. 
 */
package com.harshit;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumber implements Cloneable,Comparable<PhoneNumber>
{
	// instance variable that are immutable components of the phone number
	private final short areaCode;
	private final short prefix;
	private final short lineNumber;
	
	public PhoneNumber(int areaCode,int prefix,int lineNumber)
	{
		rangeCheck(areaCode,999,"area code");
		rangeCheck(prefix,999,"prefix");
		rangeCheck(lineNumber,9999,"line number");
		this.areaCode = (short)areaCode;
		this.lineNumber = (short)lineNumber;
		this.prefix = (short)prefix;
	}
	
	public void rangeCheck(int value,int max,String mes)
	{
		if(value < 0 || value > max)
		{
			throw new IllegalArgumentException("Value has passed the maximum "+value+" : "+mes);
		}
	}
	// implementation of the equals method with following all the best practices 
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(!(o instanceof PhoneNumber))
		{
			return false;
		}
		PhoneNumber phone = (PhoneNumber)o;
		return (this.areaCode == phone.areaCode) && (this.lineNumber == phone.lineNumber) && (this.prefix == phone.prefix); 
	}
	// toString method override
	/**
	 * This is the overriding of the Object's toString method which try to represent 
	 * the PhoneNumber class instances into human readable form.
	 */
	@Override
	public String toString()
	{
		return String.format("(%03d) %03d-%04d",this.areaCode,this.prefix,this.lineNumber);
	}
	
	// implementation of the hashcode function by the applying the rules of making a hash code
	// that follows the contract of the hashcode
	@Override
	public int hashCode()
	{
		// declare a constant arbitrary
		int result = 19;
		// iterate for every significant field in the class and get the hash code c accordingly
		// result = 31*result + c;
		result = 31*result + (int)areaCode;
		result = 31*result + (int)lineNumber;
		result = 31*result + (int)prefix;
		return result;
	}
	// trying to implement the clone method for the phone number class
	@Override
	public PhoneNumber clone()
	{
		try
		{
			return (PhoneNumber)super.clone();
		}
		catch(CloneNotSupportedException cloneException)
		{
			throw new AssertionError();
		}
	}
	// implementation of the compareTo method
	public int compareTo(PhoneNumber pn)
	{
		// similar contracts apply that are used to improve the performance of the compareTo
		// method
		if(pn.areaCode < this.areaCode)
			return 1;
		if(pn.areaCode > this.areaCode)
			return -1;
		if(pn.prefix < this.prefix)
			return 1;
		if(pn.prefix > this.prefix)
			return -1;
		if(pn.lineNumber < this.lineNumber)
			return 1;
		if(pn.lineNumber > this.lineNumber)
			return -1;
		return 0;
	}
	// main method to see the problems due to non involvement of the hashcode method which are 
	// more evident when we use hashset,hashmap and other.
	public static void main(String args[])
	{
		Map<PhoneNumber, String> m = new HashMap<PhoneNumber, String>();
		PhoneNumber phone1 = new PhoneNumber(99, 939, 1934);
		PhoneNumber phone2 = new PhoneNumber(917, 378, 4926);
		// result of toString method
		System.out.println("The String representation of the phone 1 "+phone1);
		System.out.println("The String representation of the phone 2 "+phone2);
		//m.put(phone2, "Harshit");
		//m.put(phone1, "VNPareek");
		// hashCodes for these two objects
		//System.out.println("HashCode for the phone number 1 : " + phone1.hashCode());
		//System.out.println("HashCode for the phone number 2 : " + phone2.hashCode());
		// result of the get method from the m
		System.out.println(m.get(new PhoneNumber(917,378,4926)));
	}
}
