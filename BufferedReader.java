//create code that reads three lines of the text file and creates the Book object
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public static void main(String[] args)
{
    BufferedReader readMe = new BufferedReader(new FileReader("booklist.txt"));
    String isbn;
    String title;
    String author;
    String line;
    Book theBook;
    while(line = readMe.readLine() != null)
    {
      isbn = line;
      line = readMe.readLine();
      title = line;
      line = readMe.readLine();
      author = line;
      //convert the isbn into a long data type
      //create the book object and insert it into the AVL
      theBook = new Book(Long.parseLong(isbn), title, author);
      bst.insert(theBook);
    }
}
