// AVL Binary search tree implementation in Java
// Author: AlgorithmTutor
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// isbn structure that represents a node in the tree
class Node {
	long isbn; // holds the key
	Node parent; // pointer to the parent
	Node left; // pointer to left child
	Node right; // pointer to right child
	int bf; // balance factor of the node
	String author;
	String title;
	
	//delete this segment of code
	/*
	public Node(int isbn)
	{
		this.isbn = isbn;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.bf = 0;
		this.author = "";
		this.title = "";
	}*/
	
	public Node(long isbn, String author, String title) {
		this.isbn = isbn;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.bf = 0;
		this.author = author;
		this.title = title;
	}
}
class Book
{
    private long isbn;
    private String title;
    private String author;
	public Book()
	{
	    isbn = 0;
	    title = "";
	    author = "";
	}
	public Book(long isbn, String title, String author)
	{
	    this.isbn = isbn;
	    this.title = title;
	    this.author = author;
	}
	public long returnISBN()
	{
	    return isbn;
	}
	public String returnTitle()
	{
	    return title;
	}
	public String returnAuthor()
	{
	    return author;
	}
	public void returnAllAttributesConsole()
	{
	    System.out.println(isbn);
	    System.out.println(title);
	    System.out.println(author);
	}
}

public class AVLTree {
	private Node root;

	public AVLTree() {
		root = null;
	}

	private void printHelper(Node currPtr, String indent, boolean last) {
		// print the tree structure on the screen
	   	if (currPtr != null) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }

		   System.out.println(currPtr.title + "(BF = " + currPtr.bf + ")");

		   printHelper(currPtr.left, indent, false);
		   printHelper(currPtr.right, indent, true);
		}
	}

	private Node searchTreeHelper(Node node, int key) {
		if (node == null || key == node.isbn) {
			return node;
		}

		if (key < node.isbn) {
			return searchTreeHelper(node.left, key);
		}
		return searchTreeHelper(node.right, key);
	}

	private Node deleteNodeHelper(Node node, int key) {
		// search the key
		if (node == null) return node;
		else if (key < node.isbn) node.left = deleteNodeHelper(node.left, key);
		else if (key > node.isbn) node.right = deleteNodeHelper(node.right, key);
		else {
			// the key has been found, now delete it

			// case 1: node is a leaf node
			if (node.left == null && node.right == null) {
				node = null;
			}

			// case 2: node has only one child
			else if (node.left == null) {
				Node temp = node;
				node = node.right;
			}

			else if (node.right == null) {
				Node temp = node;
				node = node.left;
			}

			// case 3: has both children
			else {
				Node temp = minimum(node.right);
				node.isbn = temp.isbn;
				node.right = deleteNodeHelper(node.right, temp.isbn);
			}

		}

		// Write the update balance logic here
		// YOUR CODE HERE

		return node;
	}

	// update the balance factor the node
	private void updateBalance(Node node) {
		if (node.bf < -1 || node.bf > 1) {
			rebalance(node);
			return;
		}

		if (node.parent != null) {
			if (node == node.parent.left) {
				node.parent.bf -= 1;
			}

			if (node == node.parent.right) {
				node.parent.bf += 1;
			}

			if (node.parent.bf != 0) {
				updateBalance(node.parent);
			}
		}
	}

	// rebalance the tree
	//this is where all the rotations occur
	//make sure to include the "node" or the title, author and/or ISBN
	void rebalance(Node node) {
		if (node.bf > 0) {
			if (node.right.bf < 0) {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + node.isbn + "; fixed in RightLeft Rotation");
				rightRotate(node.right);
				leftRotate(node);
			} else {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + node.isbn + "; fixed in Left Rotation");
				leftRotate(node);
			}
		} else if (node.bf < 0) {
			if (node.left.bf > 0) {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + node.isbn + "; fixed in LeftRight Rotation");
				leftRotate(node.left);
				rightRotate(node);
			} else {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + node.isbn + "; fixed in Right Rotation");
				rightRotate(node);
			}
		}
	}


	private void preOrderHelper(Node node) {
		if (node != null) {
			System.out.print(node.isbn + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		}
	}

	private void inOrderHelper(Node node) {
		if (node != null) {
			inOrderHelper(node.left);
			System.out.print(node.isbn + " ");
			inOrderHelper(node.right);
		}
	}

	private void postOrderHelper(Node node) {
		if (node != null) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.isbn + " ");
		}
	}

	// Pre-Order traversal
	// Node.Left Subtree.Right Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// Left Subtree . Node . Right Subtree
	public void inorder() {
		inOrderHelper(this.root);
	}

	// Post-Order traversal
	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding node
	public Node searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public Node minimum(Node node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public Node maximum(Node node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public Node successor(Node x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != null) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		Node y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public Node predecessor(Node x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the
		// left subtree
		if (x.left != null) {
			return maximum(x.left);
		}

		Node y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf - 1 - Math.max(0, y.bf);
		y.bf = y.bf - 1 + Math.min(0, x.bf);
	}

	// rotate right at node x
	void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf + 1 - Math.min(0, y.bf);
		y.bf = y.bf + 1 + Math.max(0, x.bf);
	}


	// insert the key to the tree in its appropriate position
	public void insert(long isbn, String title, String author) {
		// PART 1: Ordinary BST insert
		Node node = new Node(isbn, title, author);
		Node y = null;
		Node x = this.root;

		while (x != null) {
			y = x;
			if (node.isbn < x.isbn) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.isbn < y.isbn) {
			y.left = node;
		} else {
			y.right = node;
		}

		// PART 2: re-balance the node if necessary
		updateBalance(node);
	}

	// delete the node from the tree
	Node deleteNode(int isbn) {
		return deleteNodeHelper(this.root, isbn);
	}

	// print the tree structure on the screen
	public void prettyPrint() {
		printHelper(this.root, "", true);
	}
	public static void readFile(String name)
	{
		AVLTree bst = new AVLTree();
		try
		{
			BufferedReader readMe = new BufferedReader(new FileReader(name));
	        String isbn;
	        String title;
	        String author;
	        String line;
	        while((line = readMe.readLine()) != null)
	        {
	          isbn = line;
	          line = readMe.readLine();
	          title = line;
	          line = readMe.readLine();
	          author = line;
	          //convert the isbn into a long data type
	          //create the book object and insert it into the AVL
	          bst.insert(Long.parseLong(isbn), title, author);
	        }
	        readMe.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			bst.prettyPrint();
		}
	}
	public static void main(String [] args) {
		/*
    	bst.insert(1);
    	bst.insert(2);
    	bst.insert(3);
    	bst.insert(4);
    	bst.insert(5);
    	bst.insert(6);
    	bst.insert(7);
    	bst.insert(8);
    	bst.prettyPrint();
    	*/
		readFile("booklist.txt");
	}
}
