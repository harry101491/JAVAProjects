/* This program encompasses two Variable length local scope Algorithms one is PFF(PAGE FAULT FREQUENCY) and
 * other is VSWS(VARIABLE INTERVAL SAMPLE WORKING SET).
 * Description:
 * In this I first made a class of Frames that contain two elements one is frame value and other is the use bit.Then I implemented the logic of two
 * Algorithms by making classes PFF and VSWS.These two classes contain pageReference function with page as input.This function perform the 
 * functionality of checking whether the page fault has occurred or not.Other functions are increaseResidentSize and decreaseResidentSize
 * which are used to increase and decrease the resident size according to algorithm by the help of f,L,M,Q constants. RandomNumber class is used
 * create random number to simulate the page reference by process.10000 page references are made and then stored into the file.There are two functions
 * in the RandomNumber class one is pageWriting and other is pageAccessing. PageWriting is used to write the page numbers into the file and pageAccessing
 * is used to get those numbers and fed into two algorithms.
 * Description PFF:
 * In PFF we have a constant f which is a threshold value to decide whether we should increase the resident size or decrease it. If the value of 
 * counter(the number of page references since the last page fault) is greater than or equal to f then we should decrease the resident size as 
 * the page fault frequency is low so the process needs less frames than required.On the other side if counter is less than f then we should
 * increase the resident size because page fault frequency is high and there is some need of extra page frames for the process.
 * I have implemented the code with the value of f to be 2. Other values can be put in the static variable of f in PFF class.If we increase the   
 * value of f then resident size will increase very high and there should be less page fault to make the counter equal to f so we can decrease the
 * resident.But on the other of side if f is too small there will be lot of decrement in resident size which will not make resident size to increase
 * as required.
 * Description VSWS:
 * VSWS is slightly different than PFF. In VSWS we use sampling interval, In a sampling interval virtual counter is used denote the number of page 
 * reference made by the process from the start of interval.Number of page fault during the interval is counted by the page fault counter.In VSWS the
 * resident size only increases or remain same during the interval.Resident size only decreases at the end of the sampling interval.There are three
 * threshold values which are used to determine where to decrease resident size of the process. 
 * M: minimum duration of the sampling interval
 * L: maximum duration of the sampling interval
 * Q: maximum number of page faults that are allowed in the sampling interval
 * There some conditions which are to be followed while increasing or decreasing the resident set. These conditions are implemented in the following code.
 * In this I have implemented the code with the values M:2 L:4 Q:2. Other values can be put in the static variables M,L and Q. If value L is very high
 * then first condition of virtualCounter == L will be less executed then the second condition of virtualCounter < L and fault  == Q. Q is number of 
 * page fault which are allowed in the sampling interval if it is very high then more page fault can occur in the sampling interval and resident size
 * will increase and counter will become equal to L to decrease the resident size after the interval.   
 * Description VSWS v/s PFF:
 * In PFF value of f decides when to increase or decrease the resident size.If there is locality shift in the page references then this algorithm fails
 * because during the locality shift there will be multiple page faults at the same time so the counter will not go above the f and there will be no
 * decrease in the resident size.Because of this new locality pages will be added to resident set without discarding the old locality pages. This will
 * increase the resident size to a very big value.
 * But this problem is solved by VSWS in which whenever the virtual counter becomes L decrement is done. If virtual counter is < L then we check
 * the page fault counter is checked to find whether the it has passed Q. If it is true then we have to decrease again.
 */
package numbers;

import java.io.*;
import java.util.*;

class Frames
{
	private int frame; // frame which will hold the page reference
	private boolean usebit; // use bit associated with the frame
	public Frames() // Frame constructor
	{
		usebit = false;
		frame = -1;
	}
	// getter and setter functions
	public int getFrame() 
	{
		return frame;
	}
	public void setFrame(int frame) 
	{
		this.frame = frame;
	}
	public boolean isUsebit() 
	{
		return usebit;
	}
	public void setUsebit(boolean usebit)
	{
		this.usebit = usebit;
	}
}
class VariableIntervalSampleWorkingSet
{
	public static int M = 2; // minimum duration of sampling interval
	public static int L = 4; // maximum duration of sampling interval
	public static int Q = 2; // the number of page faults that are allowed to occur in a sampling instances
	private int virtualCounter; // counter for the sampling interval
	private int residentSize; // resident set size
	public int statCounter; // to collect some statistics
	private int pageFaultCounter; // page fault counter to compare with the Q
	private ArrayList<Frames> memory; // the memory contain frames and use bit
	public VariableIntervalSampleWorkingSet() // initialization of the instance variable 
	{
		this.virtualCounter = 0; // virtual counter for counting the number of page references
		this.residentSize = 0; // resident size of a process it is zero in the starting
		this.memory = new ArrayList<Frames>(this.residentSize); // memory array list of Frames which contain frames and use bit
		this.pageFaultCounter = 0; // the page fault counter determine the number of page faults occurred during a sampling interval
		this.statCounter = 0;
	}
	public void pageReference(int page) // page reference function
	{
		int flag = 0; // determine whether the page fault has occurred or not
		if(memory.size() == 0) // checking whether the memory size is 0 or not
		{
			increaseResidentSize(page); // increase the resident size of the process in memory
		}
		else
		{
			if(virtualCounter == 0) // if virtual counter is then it is a start of sampling instance 
			{
				for(int p=0;p<memory.size();p++) 
				{
					memory.get(p).setUsebit(false); // reset all the pages in the start of sampling interval
				}
				printMemory(); // print memory
			}
			virtualCounter++; // incrementing the virtual counter
			for(int i=0;i<memory.size();i++) // loop to whether page is present in the memory or not
			{
				Frames f = memory.get(i);
				if(f.getFrame() == page) // if it is present then we set use bit to true 
				{
					f.setUsebit(true); // setting of use bit
					flag = 0; // resetting the flag
					break; // coming out of the loop
				}
				else  // if page fault has occurred
				{
					flag = 1; // setting the flag
				}
			}
		}
		if(flag != 0) // if flag is one then page fault has occured
		{ 
			pageFaultCounter++; // page fault counter is increased 
			increaseResidentSize(page); // calling the increaseResidentSize is called
		}
		else
		{
			return; // if there is no fault then return 
		}
	}
	public void increaseResidentSize(int page) // function to increase the resident size
	{
		this.residentSize += 1; // increasing the resident size by 1
		checkResidentSize(); // checks the resident size
		Frames frame = new Frames(); // a frames object 
		frame.setFrame(page); // set the page to frame
		frame.setUsebit(true); // set the use bit to frame
		memory.add(frame); // add a frame to the memory
		printMemory(); // print memory
		if(virtualCounter == L) // if virtual counter is equals L then we should stop and scan the memory to decrease resident size 
		{
			decreaseResidentSize(); // decreasing the resident size
		}
		else if(virtualCounter < L && pageFaultCounter == Q)  // if virtual counter is less then L and number of page fault is equal to Q then we check following condition
		{
			if(virtualCounter < M)  // if it is less then M then we have to wait for virtual counter to become M
			{
				return;
			}
			else if(virtualCounter >= M) // when it is equal to M or greater then M then we decrease the resident size
			{				
				decreaseResidentSize(); // decrease resident size
			}
		}
	}
	public void decreaseResidentSize()  // function to decrease the resident size
	{
		int decrement = 0; 
		for(int i=0;i<memory.size();i++)
		{
			Frames f  = memory.get(i); // Frames object
			if(f.isUsebit() == false) // if the use bit is false
			{
				memory.remove(f); // remove the element from the memory
				memory.trimToSize(); // trim the size of the memory
				decrement++; // increase the decrement 
				i = -1; // for getting the desired result after changing the size of memory
			}
		}
		this.residentSize -= decrement;  // resident size is decreased
		checkResidentSize(); // checks the resident size
		virtualCounter = 0; // virtual counter is reset to start the new sampling interface
		pageFaultCounter = 0; // page fault counter is set to zero
		printMemory(); // print the memory
	}
	public void checkResidentSize() // checks the resident size if it is equal to 10
	{
		if(residentSize == 10)
		{
			statCounter++;
		}
	}
	public void printMemory()  // function to print the memory and its use bit
	{
		if(memory.size() == 0)
		{
			System.out.println("The memory is empty");
		}
		else
		{
			for(int i=0;i<memory.size();i++)
			{
				System.out.print(memory.get(i).getFrame());
				System.out.print(" ");
			}
			System.out.println();
			for(int i=0;i<memory.size();i++)
			{
				System.out.print(memory.get(i).isUsebit());
				System.out.print(" ");
			}
			System.out.println();
		}	
	}
}
class PageFaultFrequency
{
	public static int f = 2; // value f will be used for comparison 
	private int counter; // use to count the page references
	public int statCounter;
	private int residentSize; // resident size of process
	private ArrayList<Frames> memory; // array list to contain the pages and their use bits
	public PageFaultFrequency()
	{
		this.counter = 0;
		this.residentSize = 0;
		this.statCounter = 0;
		memory = new ArrayList<Frames>(this.residentSize);
	}
	public void pageReference(int page)
	{
		int flag = 0; // flag used to find whether page fault has occurred or not
		counter++; // increment the counter it will be used to compare with the f threshold value
		if(memory.size() == 0) // checking whether the process is resident on the memory or not
		{
			increaseResidentSize(page); // function call to increase resident size
		}
		else
		{
			for(int i=0;i<memory.size();i++) // loop to whether page is present in the memory or not
			{
				Frames f = memory.get(i);
				if(f.getFrame() == page) // if it is present then we set use bit to true 
				{
					f.setUsebit(true); // setting of use bit
					flag = 0; // resetting the flag
					break; // coming out of the loop
				}
				else  // if page fault has occurred
				{
					flag = 1; // setting the flag
				}
			}
		}
		if(flag != 0) // if flag is 1 then page fault has occurred and we have to compare the counter to the constant f value
		{
			for(int i=0;i<memory.size();i++)
			{
				memory.get(i).setUsebit(false); // setting the use bit to false
			}
			if(counter >= f) // if counter is bigger than f then we have to decrease the resident set as to optimize the overall performance
			{
				increaseResidentSize(page); // first we have to put the the new page and then decrease resident size
				decreaseResidentSize(); //  decreasing the resident set
			}
			else if(counter < f) // if counter is smaller than f then we have to increase the resident as by adding the frame we can decrease the page fault rate
			{
				increaseResidentSize(page); // increasing the resident set
			}
		}
		else
		{
			return;
		}
	}
	public void checkResidentSize() // checks the resident size if it is equal to 10
	{
		if(residentSize == 10)
		{
			statCounter++;
		}
	}
	public void increaseResidentSize(int page) // increment the resident size
	{
		this.residentSize += 1; // increasing the resident size by one
		checkResidentSize();
		Frames fr = new Frames(); // forming a temporary frames object
		fr.setFrame(page); // setting the page to frame object
		fr.setUsebit(true); // setting the use bit
		memory.add(fr); // adding to the array list
		counter = 0; // resetting the counter
		printMemory(); // printing the memory
	}
	public void decreaseResidentSize() // decreases the resident size
	{
		int decrement = 0; // declaration of decrement
		for(int i=0;i<memory.size();i++) // looping through the memory
		{
			Frames f  = memory.get(i);
			if(f.isUsebit() == false) // if we find that use bit is false then we have remove those elements
			{
				memory.remove(f); // removing the frame from the memory
				memory.trimToSize(); // trimming the size of the memory
				decrement++; // incrementing the decrement's value
				i = -1; // setting the i value to -1
			}
		}
		residentSize -= decrement; // decreasing the resident size
		checkResidentSize();
		counter = 0; // resetting the counter value to 0
		printMemory(); // printing the memory
	}
	public void printMemory() // function to print the memory
	{
		if(memory.size() == 0) 
		{
			System.out.println("The memory is empty");
		}
		else
		{
			for(int i=0;i<memory.size();i++)
			{
				System.out.print(memory.get(i).getFrame());
				System.out.print(" ");
			}
			System.out.println();
			for(int i=0;i<memory.size();i++)
			{
				System.out.print(memory.get(i).isUsebit());
				System.out.print(" ");
			}
			System.out.println();
		}	
	}
}
class RandomNumber
{
	public static int noOfPage = 10;  // number of pages for a process
	public static int maxPageReference = 10000; // maximum number of page reference
	private File file; // file for the page references
	private FileWriter fWrite; // File Writer for writing in the file
	public RandomNumber()
	{
		file = new File("G:\\JAVACode\\Paging\\pageReference.txt");
	}
	public void pageWriting()
	{
		Random rand = new Random(); // creating random 
		try
		{
			fWrite = new FileWriter(file); // File Writer for writing in the file
			fWrite.write(Integer.valueOf(noOfPage).toString()); // first write will be noOfPage
			fWrite.write("\n");
			for(int i=0;i<maxPageReference;i++) // loop of 10000
			{
				int n = rand.nextInt(noOfPage)+1; // creating a set of number of random numbers
				fWrite.write(Integer.valueOf(n).toString()); // writing the numbers to the file
				if(i != maxPageReference-1) 
				{
					fWrite.write("\n");
				}
			}
			fWrite.flush();
			fWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public String[] pageAccessing()
	{
		try
		{
			char buffer[] = new char[20986];
			FileReader freader = new FileReader(file);
			freader.read(buffer);
			StringBuilder b = new StringBuilder();
			String s = b.append(buffer).toString();
			String strArr[] = s.split("\n");
			freader.close();
			return strArr;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
/*
 * Professor, Please decomment accordingly to see the result.
 */
public class PagingAlgorithm
{
	public static void main(String args[])
	{
		RandomNumber rand = new RandomNumber();
		PageFaultFrequency pff = new PageFaultFrequency();
		VariableIntervalSampleWorkingSet vsws = new VariableIntervalSampleWorkingSet();
		String str[] = rand.pageAccessing();
		for(int i=0;i<str.length;i++)
		{
			if(i == 0)
			{
				// do nothing
			}
			else
			{
				pff.pageReference(Integer.valueOf(str[i]));
			}
		}
		System.out.println("how many times resident size has gone equal to 10 is:"+pff.statCounter);
		/*for(int i=0;i<str.length;i++)
		{
			if(i == 0)
			{
				// do nothing
			}
			else
			{
				vsws.pageReference(Integer.valueOf(str[i]));
			}
		}
		System.out.println("how many times resident size has gone equal to 10 is:"+vsws.statCounter);
		/*System.out.println("First Algorithm Page Fault Frequency of Variable Allocation and Local Scope");
		faultFrequency.pageReference(5);
		faultFrequency.pageReference(6);
		faultFrequency.pageReference(5);
		faultFrequency.pageReference(7);
		faultFrequency.pageReference(8);
		faultFrequency.pageReference(7);
		faultFrequency.pageReference(5);
		faultFrequency.pageReference(6);
		faultFrequency.pageReference(9);*/
		/*System.out.println("Second Algorithm Variable Interval Sample Working Set of Variable Allocation and Local Scope");
		
		vsws.pageReference(5);
		vsws.pageReference(6);
		vsws.pageReference(7);
		vsws.pageReference(8);
		vsws.pageReference(7);
		vsws.pageReference(5);
		vsws.pageReference(6);
		vsws.pageReference(9);*/
	}
}