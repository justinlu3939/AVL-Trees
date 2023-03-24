class AVLNode {
Book bookObject; 
	int bf; //changed from int height
	AVLNode leftPtr;
	AVLNode rightPtr;
  public AVLNode(long isbn, String author, String title)
  {
    bookObject = new Book(isbn, title, author);
    leftPtr = null;
    rightPtr = null;
    bf = 0;
  }
}
