package BST;

import java.util.ArrayList;
import java.util.HashMap;

// tree node class which contain the data, left and right reference.
class TreeNode
{
	private int data;
	private TreeNode left;
	private TreeNode right;
	public TreeNode(int data)
	{
		this.data = data;
		this.left = null;
		this.right = null;
	}
	// Inserting node in tree
	public static TreeNode Insert(TreeNode root, int data)
	{
		// when the root is null
		if(root == null)
		{
			TreeNode newNode = new TreeNode(data); // creating a new node with data
			root = newNode;
		}
		else
		{
			if(root.data > data)
			{
				root.left = Insert(root.left, data);
			}
			else
			{
				root.right = Insert(root.right,data);
			}
		}
		return root;
	}
	// preOrder printing
	public static void preOrderPrint(TreeNode root)
	{
		if(root == null)
		{
			return;
		}
		System.out.print(root.data + " ");
		preOrderPrint(root.left);
		preOrderPrint(root.right);
	}
	// In Order printing
	public static ArrayList<Integer> inOrderPrint(TreeNode root, ArrayList<Integer> list)
	{
		if(root == null)
		{
			return list;
		}
		list = inOrderPrint(root.left, list);
		list.add(root.data);
		list = inOrderPrint(root.right,list);
		return list;
	}
	// post Order printing
	public static void postOrderPrint(TreeNode root)
	{
		if(root == null)
		{
			return;
		}
		postOrderPrint(root.left);
		postOrderPrint(root.right);
		System.out.print(root.data + " ");
	}
	
}
public class GreatestRight 
{
	public static void main(String args[])
	{
		/*TreeNode root = null;
		root = TreeNode.Insert(root, 8);
		root = TreeNode.Insert(root, 7);
		root = TreeNode.Insert(root, 1);
		root = TreeNode.Insert(root, 15);
		System.out.println("the Pre order print for the tree is:");
		TreeNode.preOrderPrint(root);
		System.out.println();
		System.out.println("the Inorder order print for the tree is:");
		TreeNode.inOrderPrint(root);
		System.out.println();
		System.out.println("the Post order print for the tree is:");
		TreeNode.postOrderPrint(root);
		*/
		// Implementation of the Replace every element with the least greater element on its right
		// from geeksforgeeks problem complexity is 3.4 (average)
		int arr[] = {8,58,71,18,31,32,63,92,43,3,91,93,25,80,28}; // given array
		TreeNode root = null;
		for(int i = 0;i< arr.length;i++)
		{
			root = TreeNode.Insert(root, arr[i]);
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		ArrayList<Integer> list = new ArrayList<>();
		// O(n) complexity because inserting in hash map is O(1) and we are doing it n times 
		for(int i=0;i<arr.length;i++)
		{
			map.put(arr[i], i);
		}
		// Inorder by BST.
		list = TreeNode.inOrderPrint(root, list);
		//System.out.print("size is:"+list.size());
		/*for(int i = 0; i<list.size();i++)
		{
			System.out.println(list.get(i));
		}*/
		for(int i = 0;i<arr.length;i++)
		{
			int index1 = list.indexOf(arr[i]);
			int index2 = index1 + 1;
			if(index2 == arr.length)
			{
				arr[i] = -1;
			}
			int j;
			for(j = index2; j < list.size();j++)
			{
				int gr = list.get(j);
				if(map.get(gr) > i)
				{
					arr[i] = gr;
					break;
				}
			}
			if(j == list.size())
			{
				arr[i] = -1;
			}
		}
		// printing of the array after the processing
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i] + " ");
		}
		/*for(int i=0;i<arr.length;i++)
		{
			//System.out.println(map.get(arr[i]));
		}*/
		System.out.println();
	}
}

