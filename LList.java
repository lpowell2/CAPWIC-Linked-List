import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

// Linked list implementation
class LList implements List {
    private Link head;         // Pointer to list header
    private Link tail;         // Pointer to last element
    private Link curr;         // Access to current element
    private int listSize;      // Size of list

    // Constructors
    LList(int size) { this(); }     // Constructor -- Ignore size
    LList() { clear(); }

    // Remove all elements
    public void clear() {
        curr = tail = new Link(null); // Create trailer
        head = new Link(tail);        // Create header
        listSize = 0;
    }

    // Insert "it" at current position
    public boolean insert(Song it) {
        curr.setNext(new Link(curr.element(), curr.next()));
        curr.setElement(it);
        if (tail == curr) tail = curr.next();  // New tail
        listSize++;
        return true;
    }

    // Append "it" to list
    public boolean append(Song it) {
        tail.setNext(new Link(null));
        tail.setElement(it);
        tail = tail.next();
        listSize++;
        return true;
    }

    // Remove and return current element
    public Song remove () throws NoSuchElementException {
        if (curr == tail) // Nothing to remove
            throw new NoSuchElementException("remove() in LList has current of " + curr + " and size of "
                    + listSize + " that is not a a valid element");
        Song it = curr.element();             // Remember value
        curr.setElement(curr.next().element()); // Pull forward the next element
        if (curr.next() == tail) tail = curr;   // Removed last, move tail
        curr.setNext(curr.next().next());       // Point around unneeded link
        listSize--;                             // Decrement element count
        return it;                              // Return value
    }

    public void moveToStart() { curr = head.next(); } // Set curr at list start
    public void moveToEnd() { curr = tail; }          // Set curr at list end

    // Move curr one step left; no change if now at front
    public void prev() {
        if (head.next() == curr) return; // No previous element
        Link temp = head;
        // March down list until we find the previous element
        while (temp.next() != curr) temp = temp.next();
        curr = temp;
    }

    // Move curr one step right; no change if now at end
    public void next() { if (curr != tail) curr = curr.next(); }

    public int length() { return listSize; } // Return list length


    // Return the position of the current element
    public int currPos() {
        Link temp = head.next();
        int i;
        for (i=0; curr != temp; i++)
            temp = temp.next();
        return i;
    }

    // Move down list to "pos" position
    public boolean moveToPos(int pos) {
        if ((pos < 0) || (pos > listSize)) return false;
        curr = head.next();
        for(int i=0; i<pos; i++) curr = curr.next();
        return true;
    }

    // Return true if current position is at end of the list
    public boolean isAtEnd() { return curr == tail; }

    // Return current element value.
    public Song getValue() throws NoSuchElementException {
        if (curr == tail) // No current element
            throw new NoSuchElementException("getvalue() in LList has current of " + curr + " and size of "
                    + listSize + " that is not a a valid element");
        return curr.element();
    }

    // Check if the list is empty
    public boolean isEmpty() { return listSize == 0; }
    //Load songs from file
    //takes in string input of file name
    //file load/printout has one line for song name and another for artist name (always even number of lines)
    //reads first line from file, saves as song name, reads next line, reads as song artist, inserts new song with new info into list, repeats
    public void loadSongs(String fileName) throws FileNotFoundException {
        //when count is even its reading a song name, when its odd its artist
        int count=1;
        String name="";
        String artist="";
        try {
            // open the file
            FileInputStream file = new FileInputStream(fileName);
            Scanner in = new Scanner(file);
            while(in.hasNextLine()){
                if(count%2==1){
                    name=in.nextLine();
                }
                if(count%2==0){
                    artist=in.nextLine();
                    insert(new Song(name,artist));
                }
                count++;
            }
            in.close();
            reverse();//list is reversed since songs are written into file first song being at the top of the file
        }
        catch (FileNotFoundException e) {
            // the file was not found!
            throw new FileNotFoundException("File could not be opened!");
        }
    }
    public void writeSongs(String fileName){
        //breaks if there is nothing to write
        if(isEmpty()) return;
        try {
            // open the output file
            PrintWriter file = new PrintWriter(fileName);
            //writes
            moveToPos(0);
            for(int i=0;i<length();i++){
                file.println(getValue().getTitle());
                file.println(getValue().getArtist());
                next();
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error, could not open");
        }
    }
    //I start at the beginning of the linked list and print out the song information, I then call next to access the next element to print and continue to call this  until I hit the tail
    public void play(){
        //will break if the playlist is empty
        if(isEmpty()) return;
        //move to start
        moveToStart();
        //while it's not at the tail print out the element
        while (!(curr==tail)) {
            System.out.println(getValue().getTitle()+" - "+getValue().getArtist());
            //progress to next song to print
            next();
        }
    }
    //reverses the order of songs in the playlist
    public void reverse(){
        //breaks if playlist is empty or only has one element do nothing and break
        if(isEmpty())return;
        if(length()==1)return;
        for(int i=0; i<(length()-1); i++ ){
            //moves to end, stores last value and removes it
            moveToPos(length()-1);
            Song temp=getValue();
            remove();
            //moves to position of insertion and inserts temp value
            moveToPos(i);
            insert(temp);
        }
    }
    //starting at the end of the list, I save the value (songi) and the value of a song at a random position between 0 and the end of the list(songJ)
    //I insert songi at position j, move to position j+1 (where the actual songj is now stored) and remove it, I repeat the same steps at position i with songj
    //This process repeats with i decrementing and the bound for the random number decrementing at the same rate
    public int[] shuffle(){
        //making random object and setting seed for testing
        Random r=new Random();
        r.setSeed(4);
        //breaks if list is empty or only has one element (aka does nothing) (commented out since method was changed for testing)
        //if(isEmpty())return;
        //if(length()==1)return;
        //makes integer array of positions based on length of list for testing (0-(length-1))
        int[] posArray=new int[length()];
        for(int i=0;i<length();i++){
            posArray[i]=i;
        }
        //iterates through list backwards as long as list has more than one element
        if (length()>1){
            for (int i=length()-1;i>0;i--){
                //swaps two numbers (i) and a random number between 0 and i
                int j= r.nextInt(i+1); //random int position to swap
                //swaps element in position array for testing (as long as elements being swapped arent the same element)
                int temp=posArray[i];
                posArray[i]=posArray[j];
                posArray[j]=temp;
                //swapping actual playlist
                moveToPos(i);
                Song songi=getValue();
                moveToPos(j);
                Song songj=getValue();
                insert(songi);
                moveToPos(j+1);
                remove();
                moveToPos(i);
                insert(songj);
                moveToPos(i+1);
                remove();
            }}
        return posArray;
    }

}
