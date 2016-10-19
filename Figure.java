package com.harshit;

/**
 * 
 * @author harshitpareek
 * This program tries to mimic the Item20 concept and try to show the difference between
 * the tag classes and class hierarchy. 
 */
// This is the example of the Tag class
// Tag class are the type of classes in which we define a tag that is used to distinguish 
// two different types of instances.
// In this current class we are providing a Tag that define the shape of the figure.
public class Figure 
{
	enum Shape {RETANGLE, CIRCLE};
	// tag field of the class
	Shape shape;
	
	// instance variable if the Tag is RECTENGLE
	double length;
	double width;
	
	// instance variable if the Tag is CIRCLE
	double radius;
	
	// constructor for the tag RECTENGLE
	public Figure(double len, double wid)
	{
		this.length = len;
		this.width = wid;
	}
	// constructor for the tag CIRCLE
	public Figure(double rad)
	{
		this.radius = rad;
	}
	public double area()
	{
		switch(shape)
		{
			case RETANGLE:
				return length*width;
			case CIRCLE:
				return Math.PI*radius*radius;
			default:
				throw new AssertionError();
		}		
	}
}
