package com.harshit;

/**
 * 
 * @author harshitpareek
 *
 */

public final class ComplexNumber 
{
	private final double re; // real part of complex number
	private final double im; // imaginary part of the complex number
	
	// constructor for the complex number
	public ComplexNumber(double re, double im)
	{
		this.re = re;
		this.im = im;
	}
	// providing the accessors for the fields
	public double realPart()
	{
		return this.re;
	}
	public double imaginaryPart()
	{
		return this.im;
	}
	// basic arithmetic of the complex number
	public ComplexNumber add(ComplexNumber c)
	{
		return new ComplexNumber(this.re+c.re, this.im+c.im);
	}
	public ComplexNumber subtract(ComplexNumber c)
	{
		return new ComplexNumber(this.re-c.re, this.im-c.im);
	}
	public ComplexNumber multiply(ComplexNumber c)
	{
		return new ComplexNumber((this.re*c.re - this.im*c.im),this.re*c.im + this.im*c.re);
	}
	public ComplexNumber divide(ComplexNumber c)
	{
		double tmp = this.re*c.re + this.im*c.re;
		return new ComplexNumber((this.re*c.re+this.im*c.im)/tmp,(this.im*c.re - this.re*c.im)/tmp);
	}
	// overriding the equals method
	@Override
	public boolean equals(Object o)
	{
		// first check whether o is equals to this. If this is true then only we check other
		if(o == this)
		{
			return true;
		}
		if(!(o instanceof ComplexNumber))
		{
			return false;
		}
		ComplexNumber c = (ComplexNumber)o;
		// checking the fields by the help of Double.compare or Float.compare because
		// they contain Double.NaN or -0.0f
		return (Double.compare(this.re, c.re) == 0) && (Double.compare(this.im, c.im) == 0);
	}
	// implementing the hashCode method of the ComplexNumber
	@Override
	public int hashCode()
	{
		int result = 19;
		result = 31*result + doubleToIntConversion(this.re);
		result = 31*result + doubleToIntConversion(this.im);
		return result;
	}
	// function to convert the double value to the int value for hashCode
	// In this we have to first covert the double into long and then apply
	// boolea algebra to get the corresponding int value.
	private static int doubleToIntConversion(double d)
	{
		long l = Double.doubleToLongBits(d);
		return (int)(l ^ (l >>> 64));
	}
	// toString method Overriding
	@Override
	public String toString()
	{
		return " " + this.re + this.im + "i";
	}
}
