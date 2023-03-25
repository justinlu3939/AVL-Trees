// AVL Binary search tree implementation in Java
// Author: AlgorithmTutor
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// isbn structure that represents a AVLNode in the tree
class AVLNode {
Book bookObject; 
	int bf; //changed from int height
	AVLNode leftPTR;
	AVLNode rightPTR;
	AVLNode parent;
  public AVLNode(long isbn, String author, String title)
  {
    bookObject = new Book(isbn, title, author);
    leftPTR = null;
    rightPTR = null;
    parent = null;
    bf = 0;
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
	public void setISBN(long isbn)
	{
		this.isbn = isbn;
	}
	public long getISBN()
	{
	    return isbn;
	}
	public String getTitle()
	{
	    return title;
	}
	public String getAuthor()
	{
	    return author;
	}
	public void getAllAttributesConsole()
	{
	    System.out.println(isbn);
	    System.out.println(title);
	    System.out.println(author);
	}
}

public class AVLTree {
	private AVLNode root;

	public AVLTree() {
		root = null;
	}

	private void printHelper(AVLNode currPtr, String indent, boolean last) {
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

		   System.out.println(currPtr.bookObject.getTitle() + currPtr.bookObject.getAuthor() + "(BF = " + currPtr.bf + ")");

		   printHelper(currPtr.leftPTR, indent, false);
		   printHelper(currPtr.rightPTR, indent, true);
		}
	}

	private AVLNode searchTreeHelper(AVLNode AVLNode, int key) {
		if (AVLNode == null || key == AVLNode.bookObject.getISBN()) {
			return AVLNode;
		}

		if (key < AVLNode.bookObject.getISBN()) {
			return searchTreeHelper(AVLNode.leftPTR, key);
		}
		return searchTreeHelper(AVLNode.rightPTR, key);
	}

	private AVLNode deleteAVLNodeHelper(AVLNode AVLNode, long key) {
		// search the key
		if (AVLNode == null) return AVLNode;
		else if (key < AVLNode.bookObject.getISBN()) AVLNode.leftPTR = deleteAVLNodeHelper(AVLNode.leftPTR, key);
		else if (key > AVLNode.bookObject.getISBN()) AVLNode.rightPTR = deleteAVLNodeHelper(AVLNode.rightPTR, key);
		else {
			// the key has been found, now delete it

			// case 1: AVLNode is a leaf AVLNode
			if (AVLNode.leftPTR == null && AVLNode.rightPTR == null) {
				AVLNode = null;
			}

			// case 2: AVLNode has only one child
			else if (AVLNode.leftPTR == null) {
				AVLNode temp = AVLNode;
				AVLNode = AVLNode.rightPTR;
			}

			else if (AVLNode.rightPTR == null) {
				AVLNode temp = AVLNode;
				AVLNode = AVLNode.leftPTR;
			}

			// case 3: has both children
			else {
				AVLNode temp = minimum(AVLNode.rightPTR);
				AVLNode.bookObject.setISBN(temp.bookObject.getISBN());
				AVLNode.rightPTR = deleteAVLNodeHelper(AVLNode.rightPTR, temp.bookObject.getISBN());
			}

		}

		// Write the update balance logic here
		// YOUR CODE HERE

		return AVLNode;
	}

	// update the balance factor the AVLNode
	private void updateBalance(AVLNode AVLNode) {
		if (AVLNode.bf < -1 || AVLNode.bf > 1) {
			rebalance(AVLNode);
			return;
		}

		if (AVLNode.parent != null) {
			if (AVLNode == AVLNode.parent.leftPTR) {
				AVLNode.parent.bf -= 1;
			}

			if (AVLNode == AVLNode.parent.rightPTR) {
				AVLNode.parent.bf += 1;
			}

			if (AVLNode.parent.bf != 0) {
				updateBalance(AVLNode.parent);
			}
		}
	}

	// rebalance the tree
	//this is where all the rotations occur
	//make sure to include the "AVLNode" or the title, author and/or ISBN
	void rebalance(AVLNode AVLNode) {
		//more children on the left side
		if (AVLNode.bf > 0) {
			//more on the right
			if (AVLNode.rightPTR.bf < 0) {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + AVLNode.bookObject.getISBN() + "; fixed in RightLeft Rotation at ISBN " + AVLNode.bookObject.getISBN());
				rightPTRRotate(AVLNode.rightPTR);
				leftPtrRotate(AVLNode);
			}
			//more on the left
			else {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + AVLNode.bookObject.getISBN() + "; fixed in Left Rotation at ISBN " + AVLNode.bookObject.getISBN());
				leftPtrRotate(AVLNode);
			}
		}
		//more children on the right side
		else if (AVLNode.bf < 0) {
			//more on the left
			if (AVLNode.leftPTR.bf > 0) {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + AVLNode.bookObject.getISBN() + "; fixed in LeftRight Rotation at ISBN " + AVLNode.bookObject.getISBN());
				leftPtrRotate(AVLNode.leftPTR);
				rightPTRRotate(AVLNode);
			}
			//more on the right
			else {
			    System.out.println("Imbalance condition occurred at inserting ISBN " + AVLNode.bookObject.getISBN() + "; fixed in Right Rotation at ISBN " + AVLNode.bookObject.getISBN());
				rightPTRRotate(AVLNode);
			}
		}
	}


	private void preOrderHelper(AVLNode AVLNode) {
		if (AVLNode != null) {
			System.out.print(AVLNode.bookObject.getISBN() + " ");
			preOrderHelper(AVLNode.leftPTR);
			preOrderHelper(AVLNode.rightPTR);
		}
	}

	private void inOrderHelper(AVLNode AVLNode) {
		if (AVLNode != null) {
			inOrderHelper(AVLNode.leftPTR);
			System.out.print(AVLNode.bookObject.getISBN() + " ");
			inOrderHelper(AVLNode.rightPTR);
		}
	}

	private void postOrderHelper(AVLNode AVLNode) {
		if (AVLNode != null) {
			postOrderHelper(AVLNode.leftPTR);
			postOrderHelper(AVLNode.rightPTR);
			System.out.print(AVLNode.bookObject.getISBN() + " ");
		}
	}

	// Pre-Order traversal
	// AVLNode.leftPtr Subtree.rightPTR Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// leftPtr Subtree . AVLNode . rightPTR Subtree
	public void inorder() {
		inOrderHelper(this.root);
	}

	// Post-Order traversal
	// leftPtr Subtree . rightPTR Subtree . AVLNode
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding AVLNode
	public AVLNode searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	// find the AVLNode with the minimum key
	public AVLNode minimum(AVLNode AVLNode) {
		while (AVLNode.leftPTR != null) {
			AVLNode = AVLNode.leftPTR;
		}
		return AVLNode;
	}

	// find the AVLNode with the maximum key
	public AVLNode maximum(AVLNode AVLNode) {
		while (AVLNode.rightPTR != null) {
			AVLNode = AVLNode.rightPTR;
		}
		return AVLNode;
	}

	// find the successor of a given AVLNode
	public AVLNode successor(AVLNode x) {
		// if the rightPTR subtree is not null,
		// the successor is the leftPtrmost AVLNode in the
		// rightPTR subtree
		if (x.rightPTR != null) {
			return minimum(x.rightPTR);
		}

		// else it is the lowest ancestor of x whose
		// leftPtr child is also an ancestor of x.
		AVLNode y = x.parent;
		while (y != null && x == y.rightPTR) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given AVLNode
	public AVLNode predecessor(AVLNode x) {
		// if the leftPtr subtree is not null,
		// the predecessor is the rightPTRmost AVLNode in the
		// leftPtr subtree
		if (x.leftPTR != null) {
			return maximum(x.leftPTR);
		}

		AVLNode y = x.parent;
		while (y != null && x == y.leftPTR) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate leftPtr at AVLNode x
	void leftPtrRotate(AVLNode x) {
		AVLNode y = x.rightPTR;
		x.rightPTR = y.leftPTR;
		if (y.leftPTR != null) {
			y.leftPTR.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.leftPTR) {
			x.parent.leftPTR = y;
		} else {
			x.parent.rightPTR = y;
		}
		y.leftPTR = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf - 1 - Math.max(0, y.bf);
		y.bf = y.bf - 1 + Math.min(0, x.bf);
	}

	// rotate rightPTR at AVLNode x
	void rightPTRRotate(AVLNode x) {
		AVLNode y = x.leftPTR;
		x.leftPTR = y.rightPTR;
		if (y.rightPTR != null) {
			y.rightPTR.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.rightPTR) {
			x.parent.rightPTR = y;
		} else {
			x.parent.leftPTR = y;
		}
		y.rightPTR = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf + 1 - Math.min(0, y.bf);
		y.bf = y.bf + 1 + Math.max(0, x.bf);
	}


	// insert the key to the tree in its appropriate position
	public void insert(long isbn, String title, String author) {
		// PART 1: Ordinary BST insert
		AVLNode AVLNode = new AVLNode(isbn, title, author);
		AVLNode y = null;
		AVLNode x = this.root;

		while (x != null) {
			y = x;
			if (AVLNode.bookObject.getISBN() < x.bookObject.getISBN()) {
				x = x.leftPTR;
			} else {
				x = x.rightPTR;
			}
		}

		// y is parent of x
		AVLNode.parent = y;
		if (y == null) {
			root = AVLNode;
		} else if (AVLNode.bookObject.getISBN() < y.bookObject.getISBN()) {
			y.leftPTR = AVLNode;
		} else {
			y.rightPTR = AVLNode;
		}

		// PART 2: re-balance the AVLNode if necessary
		updateBalance(AVLNode);
	}

	// delete the AVLNode from the tree
	AVLNode deleteAVLNode(int isbn) {
		return deleteAVLNodeHelper(this.root, isbn);
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
