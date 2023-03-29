class Link {         // Singly linked list node class
    private Song e;  // Value for this node
    private Link n;    // Point to next node in list

    // Constructors
    Link(Song it, Link inn) { e = it; n = inn; }
    Link(Link inn) { e = null; n = inn; }

    Song element() { return e; }                  // Return the value
    Song setElement(Song it) { return e = it; } // Set element value
    Link next() { return n; }                       // Return next link
    Link setNext(Link inn) { return n = inn; }      // Set next link
}