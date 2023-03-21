public class Book
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