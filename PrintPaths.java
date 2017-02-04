package com.harshit;

import java.util.ArrayList;

class PNode
{
	int data;
	PNode left;
	PNode right;
	public PNode(int data)
	{
		this.data = data;
		this.left = null;
		this.right = null;
	}
}

public class PrintPaths 
{
	public static void preOrder(PNode root, ArrayList<Integer> list, int k)
	{
		// base condition
		if(root == null)
		{
			return;
		}
		list.add(root.data);
		// recurse on the left part 
		preOrder(root.left, list, k);
		// recurse on the right part
		preOrder(root.right, list, k);
		
		// we have to do these steps for each and every node to find wheather there is path which sums to k
		int sum = 0;
		for(int j=list.size()-1;j>=0;j--)
		{
			sum += list.get(j);
			if(sum == k)
			{
				printPath(list,j);
			}
		}
		list.remove(list.size()-1);
	}
	public static void printPath(ArrayList<Integer> list, int j)
	{
		for(int i=j;i<list.size();i++)
		{
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}
	public static void main(String args[])
	{
		ArrayList<Integer> list = new ArrayList<>();
		PNode root = new PNode(1);
	    root.left = new PNode(3);
	    root.left.left = new PNode(2);
	    root.left.right = new PNode(1);
	    root.left.right.left = new PNode(1);
	    root.right = new PNode(-1);
	    root.right.left = new PNode(4);
	    root.right.left.left = new PNode(1);
	    root.right.left.right = new PNode(2);
	    root.right.right = new PNode(5);
	    root.right.right.right = new PNode(2);
	    preOrder(root, list, 5);
	    for(Integer i: list)
	    {
	    	System.out.print(i + " ");
	    }
	    System.out.println();
	}
}
