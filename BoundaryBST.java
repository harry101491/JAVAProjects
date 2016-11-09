package BST;

class BSTNode
{
	int data;
	BSTNode left;
	BSTNode right;
	public BSTNode(int data)
	{
		this.data = data;
	}
}

public class BoundaryBST 
{
	BSTNode root;
	public BoundaryBST()
	{
		root = null;
	}
	public BSTNode insertBSTNode (BSTNode root, int value)
	{
		if(root == null)
		{
			return new BSTNode(value);
		}
		else
		{
			if(root.data > value)
			{
				root.left = insertBSTNode(root.left, value);
			}
			else
			{
				root.right = insertBSTNode(root.right, value);
			}
		}
		return root;
	}
	public void printPreOrder(BSTNode root)
	{
		if(root == null)
		{
			return;
		}
		System.out.print(root.data + " ");
		printPreOrder(root.left);
		printPreOrder(root.right);
	}
	public void printLeaves(BSTNode root)
	{
		if(root != null)
		{
			printLeaves(root.left);
			if(root.left == null && root.right == null)
			{
				System.out.print(root.data + " ");
				return;
			}
			printLeaves(root.right);
		}
	}
	public void printRightBoundary(BSTNode root)
	{
		if(root != null)
		{
			printRightBoundary(root.right);
			if(root.right != null)
			{
				System.out.print(root.data + " ");
				return;
			}
		}
		
	}
	public void printLeftBoundaryNodes(BSTNode root)
	{
		if(root != null && root.left != null)
		{
			System.out.print(root.data + " ");
			printLeftBoundaryNodes(root.left);
		}
	}
	public static void main(String args[])
	{
		BoundaryBST tree = new BoundaryBST();
		tree.root = tree.insertBSTNode(tree.root, 20);
		tree.root = tree.insertBSTNode(tree.root, 8);
		tree.root = tree.insertBSTNode(tree.root, 4);
		tree.root = tree.insertBSTNode(tree.root, 12);
		tree.root = tree.insertBSTNode(tree.root, 10);
		tree.root = tree.insertBSTNode(tree.root, 14);
		tree.root = tree.insertBSTNode(tree.root, 22);
		tree.root = tree.insertBSTNode(tree.root, 25);
		tree.printLeftBoundaryNodes(tree.root);
		tree.printLeaves(tree.root);
		tree.printRightBoundary(tree.root.right);
		//tree.printPreOrder(tree.root);
	}
}
