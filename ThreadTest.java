package com.harshit;

class Q
{
	int n;
	boolean valueSet = false;
	synchronized int get()
	{
		while(valueSet == false)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Got: "+n);
		valueSet = false;
		notify();
		return n;
	}
	synchronized void put(int n)
	{
		while(valueSet == true)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		this.n = n;
		System.out.println("Put: "+n);
		valueSet = true;
		notify();
	}
}

class Producer implements Runnable
{
	Q q;
	public Producer(Q q)
	{
		this.q = q;
		new Thread(this,"Producer").start();
	}
	public void run()
	{
		int i = 0;
		while(true)
		{
			q.put(i++);
		}
	}
}
class Consumer implements Runnable
{
	Q q;
	public Consumer(Q q)
	{
		this.q = q;
		new Thread(this,"Consumer").start();
	}
	public void run()
	{
		while(true)
		{
			q.get();
		}
	}
}
public class ThreadTest
{
	public static void main(String args[])
	{
		Q q = new Q();
		new Producer(q);
		new Consumer(q);
		
		System.out.println("print Ctrl+C for exit");
	}
}
/**
 * Using synchronized statement and synchronized method
class CallMe 
{
	public void printMessage(String msg)
	{
		try
		{
			System.out.print("["+msg);
			Thread.sleep(500);
			System.out.println("]");
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
class Caller implements Runnable
{
	private Thread t;
	private String str;
	private CallMe target;
	public Caller(CallMe targ, String s)
	{
		this.target = targ;
		this.str = s;
		t = new Thread(this, "Caller Thread");
		t.start();
	}
	public void run()
	{
		synchronized(target)
		{
			target.printMessage(str);
		}
	}
	public Thread getThread()
	{
		return t;
	}
}
public class ThreadTest
{
	public static void main(String args[])
	{
		CallMe target = new CallMe();
		Caller caller1 = new Caller(target,"Hello");
		Caller caller2 = new Caller(target,"Synchronized");
		Caller caller3 = new Caller(target, "World");
		try
		{
			caller1.getThread().join();
			caller2.getThread().join();
			caller3.getThread().join();
		}
		catch(InterruptedException i)
		{
			i.printStackTrace();
		}
	}
}
**/
/**
// Creating multiple threads from the main thread
class MultipleThreads implements Runnable
{
	private Thread t;
	public MultipleThreads(String name)
	{
		t = new Thread(this,"thread number "+(name));
		System.out.println("starting the child thread:"+t.getName());
		t.start();
	}
	public void run()
	{
		try
		{
			for(int i=0;i<5;i++)
			{
				System.out.println("the thread "+t.getName()+" "+i);
				Thread.sleep(500);
			}
		}
		catch(InterruptedException i)
		{
			i.printStackTrace();
		}
	}
}

public class ThreadTest
{
	public static void main(String args[])
	{
		new MultipleThreads("one");
		new MultipleThreads("two");
		new MultipleThreads("three");
		System.out.println("Main thread");
		try
		{
			for(int i=0;i<5;i++)
			{
				System.out.println("the main thread "+i);
				Thread.sleep(1000);
			}
		}
		catch(InterruptedException i)
		{
			i.printStackTrace();
		}
		System.out.println("the end of main thread");
	}
}
**/

/**
 * The implementation of the Thread by the help of Thread Class
 * 
class ExtendingThread extends Thread
{
	public ExtendingThread()
	{
		super("Demo Thread");
		System.out.println("Child Thread:"+this);
		start();
	}
	public void run()
	{
		try
		{
			for(int i=0;i<5;i++)
			{
				System.out.println("child thread:"+ i);
				Thread.sleep(500);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println(" the end of the child thread "+this.getName());
	}
}
public class ThreadTest
{
	public static void main(String args[])
	{
		new ExtendingThread(); // creating a new thread
		System.out.println("the starting main thread");
		try
		{
			for(int i=0;i<5;i++)
			{
				System.out.println("the main thread:"+i);
				Thread.sleep(1000);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("ending the main thread");
	}
}
**/

/**
 * Implementing by the help of implementing the Runnable
 * 
class ThreadByImplementing implements Runnable
{
	private Thread t;
	public ThreadByImplementing() // default constructor
	{
		t = new Thread(this, "myNewThread");
		t.start();
	}
	public void run() // implementing the run method
	{
		try
		{
			for(int i = 0;i<5;i++)
			{
				System.out.println("name:"+t.getName()+" count:"+i);
				Thread.sleep(500);
			}
		}
		catch(InterruptedException i)
		{
			i.printStackTrace();
		}
	}
}



public class ThreadTest 
{
	public static void main(String args[])
	{
		new ThreadByImplementing(); // creating a new thread by the instantiating the ThreadByImplementing
		
		// main thread
		try
		{
			for(int i=0;i<5;i++)
			{
				System.out.println("name: main" + "count:"+i);
				Thread.sleep(1000);
			}
		}
		catch(InterruptedException i)
		{
			i.printStackTrace();
		}
	}
}
**/
/**
 * The basic implementation in which I changed the name
 * of the thread and print out a range in a delay of
 * 1 second.
Thread t = Thread.currentThread(); // getting the current thread

System.out.println("the current thread is:"+t); // string representation

t.setName("MyMain"); // changing the name

System.out.println("After the name change:"+t);

try // try block required because the thread can be interrupted during sleep
{
	for(int i=0;i<5;i++)
	{
		System.out.println("the value of i is:"+i);
		Thread.sleep(1000); // sleep for 1 sec
	}
}
catch(InterruptedException i) // catching the interrupted exception
{
	i.printStackTrace();
}
**/