/**
 * This Program analyze the equals contract in the type and sub types.
 * ITEM 8 
 */
package com.harshit;

/**
 * @author harshitpareek
 * @category anlysis
 *
 */
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.Color;
import java.util.HashSet;
public class Point 
{
	private final int x;
	private final int y;
	private static final Set<Point> unitCircle; // collection of points that are contained in the unit circle.
	static // static block for adding the points in to the unitCircle
	{
		unitCircle = new HashSet<Point>();
		unitCircle.add(new Point(0,1));
		unitCircle.add(new Point(0,-1));
		unitCircle.add(new Point(1,0));
		unitCircle.add(new Point(-1,0));
	}
	public Point(int x, int y)
	{
		if(x < 0 && y < 0)
		{
			throw new IllegalStateException();
		}
		this.x = x;
		this.y = y;
	}
	// static method that tells whether the given point exist in the unit Circle or not
	public static boolean isUnitCircle(Point p)
	{
		return unitCircle.contains(p);
	}
	@Override 
	public boolean equals(Object o)
	{
		if(o == null || o.getClass() != this.getClass())
		{
			return false;
		}
		Point str = (Point)o;
		return str.x == this.x && str.y == this.y;
	}

}
// sub type that extends the Point
class CounterPoint extends Point
{
	private static final AtomicInteger counter = new AtomicInteger();
	public CounterPoint(int x,int y)
	{
		super(x, y);
		counter.incrementAndGet();
	}
	public int getCreatedNumber()
	{
		return counter.get();
	}
}
// class ColorPoint that extends Point
/**
 *  Way to overcome the problem of equals contract when we inherit the class and try to add
 *  a value component in to the subclass is to prefer composition over inheritance and add
 *  a public method that will give the view for that member.
 */
class ColorPoint
{
	private final Point point;
	private final Color color;
	public ColorPoint(int x,int y,Color color)
	{
		if(color == null)
		{
			throw new NullPointerException();
		}
		this.point = new Point(x,y);
		this.color = color;
	}
	// public method that will give the view for the member
	public Point givePoint()
	{
		return this.point;
	}
	// implementing the equals method that invokes the general contract
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		if(!(o instanceof ColorPoint))
		{
			return false;
		}
		Point pointView = ((ColorPoint)o).givePoint();
		Point currentPoint = this.givePoint();
		return (pointView.equals(currentPoint)) && (((ColorPoint)o).color == this.color);
		/*if(!(o instanceof stringAnalysis))
		{
			return false; // this will cause symmetry but not transitivity
		}*/
		/*if(o == null || o.getClass() != this.getClass())
		{
			return false;
		}*/
		//return (super.equals(o)) && (((ColorPoint)o).color == this.color);
	}
}