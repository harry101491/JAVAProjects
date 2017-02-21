package BST;

public class BSTTree 
{
	public Node root;
	public BSTTree()
	{
		this.root = null;
	}
	private Node insert(Node p, int data)
	{
		if(p == null)
		{
			Node newNode = new Node(data);
			p = newNode;
			return p;
		}
		else
		{
			if(p.data < data)
			{
				p.right = insert(p.right, data);
			}
			else
			{
				p.left = insert(p.left, data);
			}
		}
		return p;
	}
	public void Insert(int data)
	{
		this.root = insert(this.root,data);
	}
	private void printInorder(Node p)
	{
		if(p == null)
		{
			return;
		}
		printInorder(p.left);
		System.out.print(p.data+ "  ");
		printInorder(p.right);
	}
	public void printTree()
	{
		printInorder(root);
	}
	public Node getRoot()
	{
		return this.root;
	}
}
