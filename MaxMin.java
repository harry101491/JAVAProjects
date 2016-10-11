package DivideAndConquer;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


/*class Portfolio
{
    private String ticker;
    private String name;
    private double quantity;
    public Portfolio(String ticker, String name, double quantity)
    {
        this.ticker = ticker;
        this.name = name;
        this.quantity = quantity;
    }
    public void setTicker(String ticker)
    {
        this.ticker = ticker;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }
    public String getTicker()
    {
        return this.ticker;
    }
    public String getName()
    {
        return this.name;
    }
    public double getQuantity()
    {
        return this.quantity;
    }
}
public class MaxMin {
    
    public static final String SEPARATOR = "@";
    /*
     * Complete the function below.
     *
 	 * Note: The questions in this test build upon each other. We recommend you
	 * copy your solutions to your text editor of choice before proceeding to
	 * the next question as you will not be able to revisit previous questions.
	 */
/*
    static String printHoldings(String portfolioString) {
        String record[];
        record = portfolioString.split(SEPARATOR);
        Arrays.sort(record);
        String str = "";
        for(int i=0;i<record.length;i++)
        {
            String holdings[] = record[i].split(",");
            Portfolio portfolio = new Portfolio(holdings[0],holdings[1],Integer.valueOf(holdings[3]));
            if(i != record.length-1)
            {
                str = str + "["+portfolio.getTicker()+", "+portfolio.getName()+", "+portfolio.getQuantity()+"], ";
            }
            else
            {
               str = str + "["+portfolio.getTicker()+", "+portfolio.getName()+", "+portfolio.getQuantity()+"]";
            }
        }
        /*for(int i=0;i<arr.length;i++)
        {
            System.out.println(arr[i]);
        }
        return str;
    }
    
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        String res;
        String _input;
        try {
            _input = in.nextLine();
        } catch (Exception e) {
            _input = null;
        }
        res = printHoldings(_input);
        System.out.println(res);
    }
}
// benchmark
class Benchmark
{
    private String ticker;
    private String name;
    private int quantity;
    public Benchmark(String ticker, String name, int quantity)
    {
        this.ticker = ticker;
        this.name = name;
        this.quantity = quantity;
    }
    public void setTicker(String ticker)
    {
        this.ticker = ticker;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    public String getTicker()
    {
        return this.ticker;
    }
    public String getName()
    {
        return this.name;
    }
    public int getQuantity()
    {
        return this.quantity;
    }
}*/
/*
// question 4
static String printHoldings(String portfolioString) {
    String benchmarkString;
    String str;
    String arr[];
    arr = portfolioString.split(COLON);
    str = arr[0];
    benchmarkString = arr[1];
    String benchmarkArray[] = benchmarkString.split(SEPARATOR);
    Arrays.sort(benchmarkArray);
    String str1 = "";
    for(int i=0;i<benchmarkArray.length;i++)
    {
        String holdings[] = benchmarkArray[i].split(",");
        Benchmark benchmark = new Benchmark(holdings[0],holdings[1],Integer.valueOf(holdings[2]));
        if(i != benchmarkArray.length-1)
        {
            str1= str1 + "["+benchmark.getTicker()+", "+benchmark.getName()+", "+benchmark.getQuantity()+"], ";
        }
        else
        {
           str1 = str1 + "["+benchmark.getTicker()+", "+benchmark.getName()+", "+benchmark.getQuantity()+"]";
        }
    }
    return str1;
}





/*package DivideAndConquer;

public class MaxMin 
{
	public static int max(int arr[],int startIndex, int endIndex)
	{
		if(startIndex == endIndex)
		{
			return arr[startIndex];
		}
		int max1 = max(arr,startIndex,(startIndex+endIndex)/2);
		int max2 = max(arr,((startIndex+endIndex)/2)+1,endIndex);
		return max1>max2 ? max1 : max2;
	}
	public static int min(int arr[],int startIndex,int endIndex)
	{
		if(startIndex == endIndex)
		{
			return arr[startIndex];
		}
		int min1 = min(arr,startIndex,(startIndex+endIndex)/2);
		int min2 = min(arr,((startIndex+endIndex)/2)+1,endIndex);
		return min1>min2 ? min2 : min1;
	}
	public static void main(String args[])
	{
		int arr[] = {5,3,20,1,6,7};
		int min,max;
		min = min(arr,0,arr.length-1);
		max = max(arr,0,arr.length-1);
		System.out.println("the max value is:"+max);
		System.out.println("the min value is:"+min);
	}
}*/
