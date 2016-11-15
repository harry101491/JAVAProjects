package BST;

import java.util.Stack;

class LCANode
{
	int data;
	LCANode left;
	LCANode right;
	LCANode(int data)
	{
		this.data = data;
		this.left = null;
		this.right = null;
	}
}

public class LeastCommonAncestor 
{
	LCANode root;
	Stack<LCANode> list1;
	Stack<LCANode> list2;
	public LeastCommonAncestor() 
	{
		root = null;
		list1 = new Stack<>();
		list2 = new Stack<>();
	}
	public LCANode insertLCANode (LCANode root, int value)
	{
		if(root == null)
		{
			return new LCANode(value);
		}
		else
		{
			if(root.data > value)
			{
				root.left = insertLCANode(root.left, value);
			}
			else
			{
				root.right = insertLCANode(root.right, value);
			}
		}
		return root;
	}
	public void findFirst(LCANode root, int value)
	{
		LCANode p = root;
		while(p.data != value)
		{
			if(value > p.data)
			{
				list1.add(p);
				p = p.right;
			}
			else
			{
				list1.add(p);
				p = p.left;
			}
		}
		list1.add(p);
	}
	public void findSecond(LCANode root, int value)
	{
		LCANode p = root;
		while(p.data != value)
		{
			if(value > p.data)
			{
				list2.add(p);
				p = p.right;
			}
			else
			{
				list2.add(p);
				p = p.left;
			}
		}
		list2.add(p);
	}
	public LCANode lca(LCANode root, int value1, int value2)
	{
		LCANode p = root;
		if(value1 == value2)
		{
			if(p.data == value1)
			{
				return p;
			}
			else
			{
				list1.clear();
				findFirst(p,value1);
				return list1.pop();
			}
		}
		else
		{
			list1.clear();
			list2.clear();
			findFirst(p,value1);
			findSecond(p,value2);
			while((!list1.isEmpty()) && (!list2.isEmpty()))
			{
				LCANode first = list1.pop();
				LCANode second = list2.pop();
				if(first.data == second.data)
				{
					return first;
				}
			}
		}
		return root;
	}
	public static void main(String args[])
	{
		LeastCommonAncestor tree = new LeastCommonAncestor();
		tree.root = tree.insertLCANode(tree.root, 4);
		tree.root = tree.insertLCANode(tree.root, 2);
		tree.root = tree.insertLCANode(tree.root, 3);
		tree.root = tree.insertLCANode(tree.root, 1);
		tree.root = tree.insertLCANode(tree.root, 7);
		tree.root = tree.insertLCANode(tree.root, 6);
		LCANode node = tree.lca(tree.root, 1, 6);
		System.out.println("the value LCA is:"+node.data);
	}
}
