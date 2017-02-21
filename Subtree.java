package BST;

public class Subtree 
{
	public static void main(String args[])
	{
		// the tree which we have check is a subtree of another 
		BSTTree subTree2 = new BSTTree();
		subTree2.Insert(4);
		subTree2.Insert(3);
		subTree2.Insert(6);
		subTree2.Insert(10);
		
		// the tree for which we have to check whether it has subtree 1 or not.
		BSTTree subTree1 = new BSTTree();
		subTree1.Insert(26);
		subTree1.Insert(11);
		subTree1.Insert(4);
		subTree1.Insert(3);
		subTree1.Insert(6);
		subTree1.Insert(10);
		subTree1.Insert(27);
		subTree1.Insert(28);
		
		Subtree subtree = new Subtree();
		if(subtree.IsSubtree(subTree1.root, subTree2.root))
		{
			System.out.println("the Tree 2 is subtree of Tree 1");
		}
		else
		{
			System.out.println("the Tree 2 is not subtree of Tree 1");
		}
	}
	
	public boolean IsSubtree(Node root1, Node root2)
	{
		if(root1 == null && root2 == null)
		{
			return true;
		}
		else if(root2 == null)
		{
			return true;
		}
		else if(root1 == null)
		{
			return false;
		}
		else
		{
			if(root1.data == root2.data)
			{
				boolean left = IsSubtree(root1.left,root2.left);
				boolean right = IsSubtree(root1.right,root2.right);
				if(left && right)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if(IsSubtree(root1.left, root2) || IsSubtree(root1.right, root2))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
}
