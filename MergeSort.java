package DivideAndConquer;

public class MergeSort 
{
	public static void main(String args[])
	{
		int arr[] = {3,4,1,5,9,8};
		int n = arr.length;
		for(int i=0;i<n-1;i++)
		{
			int j = i+1;
			while(arr[i+1]<arr[j] && j != 0)
			{
				j--;
			}
			int temp = arr[i+1];
			arr[i+1] = arr[j];
			arr[j] = temp;
		}
		for(int i=0;i<n;i++)
		{
			System.out.println(arr[i]);
		}
	}
	
	/*public static void main(String args[])
	{
		int arr1[] = {1,5,6,9,10};
		int arr2[] = {2,3,4,7,8,11};
		int start1 = 0;
		int start2 = 0;
		int high1 = arr1.length-1;
		int high2 = arr2.length-1;
		int arr[] = new int[(high1-start1+1)+(high2-start2+1)];
		int arrPointer = 0;
		while(start1 <= high1 && start2 <= high2)
		{
			if(arr1[start1]<arr2[start2])
			{
				arr[arrPointer++] = arr1[start1];
				start1++;
			}
			else
			{
				arr[arrPointer++] = arr2[start2];
				start2++;
			}
		}
		if(start1 > high1)
		{
			for(int i=start2;i<=high2;i++)
			{
				arr[arrPointer++] = arr2[i];
			}
		}
		else if (start2 > high2)
		{
			for(int i=start1;i<=high1;i++)
			{
				arr[arrPointer++] = arr1[i];
			}
		}
		System.out.println("the sorted array is:"+arrPointer);
		for(int i=0;i<arrPointer;i++)
		{
			System.out.println(arr[i]);
		}
	}*/
}
