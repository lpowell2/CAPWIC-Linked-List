import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class LListTest extends LList {
    //testing empty, one element and multiple elements
    @org.junit.jupiter.api.Test
    void testReverse() throws FileNotFoundException {
        //test when list is empty: make empty list, copy list to new list, reverse list, assert lists are equal
        //can test this way since list shouldnt change (only lists with more than one element should)
        LList reverseList=new LList();
        LList emptyList=reverseList;
        reverseList.reverse();
        assertEquals(reverseList,emptyList);
        //test when list only has one element: make single element list, copy list to new list, reverse list, assert lists are equal
        //can test this way since list shouldnt change (only lists with more than one element should)
        LList singleReverseList=new LList();
        singleReverseList.insert(new Song("Strawberry Fields","The Beatles"));
        LList singleList=singleReverseList;
        singleReverseList.reverse();
        assertEquals(singleList,singleReverseList);
        //test when there are multiple elements
        LList normalList=new LList();
        LList reversedList=new LList();
        //make a normal list (nonreversed) and a reversed list (same elements loaded but reversed)
        normalList.loadSongs("song.txt");
        reversedList.loadSongs("song.txt");
        reversedList.reverse();
        //assert equals song title and artist for reversed and normal list (reversed list checking from start to end and normal from end to start)
        int j=normalList.length()-1;
        for (int i=0;i<reversedList.length();i++){
            normalList.moveToPos(j);
            reversedList.moveToPos(i);
            assertEquals(reversedList.getValue().getArtist(),normalList.getValue().getArtist());
            assertEquals(reversedList.getValue().getTitle(),normalList.getValue().getTitle());
            j--;
        }
    }
    //testing empty, one element and multiple elements
    @org.junit.jupiter.api.Test
    void testShuffle() throws FileNotFoundException {
        //test when list is empty: make empty list, copy list to new list, reverse list, assert lists are equal
        //can test this way since list shouldnt change (only lists with more than one element should)
        LList shuffleList=new LList();
        LList emptyList=shuffleList;
        shuffleList.shuffle();
        assertEquals(shuffleList,emptyList);
        //test when list only has one element: make single element list, copy list to new list, reverse list, assert lists are equal
        //can test this way since list shouldnt change (only lists with more than one element should)
        LList singleShuffleList=new LList();
        singleShuffleList.insert(new Song("Strawberry Fields","The Beatles"));
        LList singleList=singleShuffleList;
        singleShuffleList.reverse();
        assertEquals(singleList,singleShuffleList);
        //testing multiple elements
        LList shuffledlist=new LList();
        shuffledlist.loadSongs("song.txt");
        int[] posArray=shuffledlist.shuffle();//saving shuffled int array for testing
        for(int i=0;i<shuffledlist.length();i++){//testing for playlist size 6 with intended position
            switch (i){
                case 0:
                    assertEquals(posArray[i],0);
                    break;
                case 1:
                    assertEquals(posArray[i],4);
                    break;
                case 2:
                    assertEquals(posArray[i],1);
                    break;
                case 3:
                    assertEquals(posArray[i],3);
                    break;
                case 4:
                    assertEquals(posArray[i],5);
                    break;
                case 5:
                    assertEquals(posArray[i],2);
                    break;
            }
        }
    }
    @org.junit.jupiter.api.Test
    void testReadWrite () throws FileNotFoundException {
        //tests when file to read doesn't exist (throws exception)
        LList songLoad=new LList();
        FileNotFoundException e = Assertions.assertThrows(FileNotFoundException.class, ()->songLoad.loadSongs("notthere"));
        assertEquals("File could not be opened!",e.getMessage());

    }
}