import java.util.function.Predicate;

// predicate for find -- checks if the given string has 
// a length of three 
class StringLengthEqualsThree implements Predicate<String> {

  // returns true if the length of given string t equals 3
  public boolean test(String t) {
    return (t.length() == 3);
  }

}

// a Deque represents a list of nodes
class Deque<T> {
  Sentinel<T> header;

  // the default constructor
  Deque() {
    this.header = new Sentinel<T>();
  }

  // the convenience constructor
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // counts the size of this Deque's sentinel
  int size() {
    return this.header.size();
  }

  // adds the given value to the front of this
  // Deque's sentinel
  void addAtHead(T value) {
    this.header.addAtHead(value);
  }

  // adds the given value to the back of this
  // Deque's sentinel
  void addAtTail(T value) {
    this.header.addAtTail(value);
  }

  // removes the first node from this Deque, and returns it
  T removeFromHead() {
    return this.header.removeAtIndex(1);
  }

  // removes the last node from this Deque, and returns it
  T removeFromTail() {
    return this.header.removeAtIndex(-1);
  }

  // the abstracted method for removeFromHead and
  // removeFromTail, uses the index of the item
  // to know where to remove it
  T removeAtIndex(int index) {
    return this.header.removeAtIndex(index);
  }

  //getAtIndex
  T getAtIndex(int index) {
    return this.header.getAtIndex(index);
  }

  // returns the first node in this Deque for which the
  // given predicate returns true
  ANode<T> find(Predicate<T> finder) {
    return this.header.find(finder);
  }

  // removes the given node from this Deque
  // utilizing the given node's index
  void removeNode(ANode<T> node) {
    try {
      node.removeAtIndexHelp(0);
    } catch (Exception e) {
      return;
    }
    return;
  }

  public Iterator<T> iterator() {
    // Choose a forward iteration by default
    return new DequeForwardIterator<T>(this);
  }
  // But...also provide a reverse iterator if needed
  Iterator<T> reverseIterator() {
    return new DequeReverseIterator<T>(this);
  }

  public void remove() {
    throw new RuntimeException();
  }

  public T find(int x) {
    // TODO Auto-generated method stub
    return null;
  }
}

// represents the abstracted node
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // the constructor which initializes fields to null
  ANode() {}

  // the constructor which initializes this ANode's
  // fields to the given values
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  // helper for the size function, returns
  // the accumulated count of nodes
  abstract int sizeHelp();

  // helper for removeAtIndex, fixes the 
  // node links between the next node and
  // this ANode for when this ANode
  // is being removed
  abstract void updateNext(ANode<T> next2);

  // helper for removeAtIndex, fixes the 
  // node links between the previous node and
  // this ANode for when this ANode
  // is being removed
  abstract void updatePrev(ANode<T> prev2);

  // helper for removeAtIndex, throws an error
  // when trying to remove from a Sentinel
  abstract T removeAtIndexHelp(int index);

  // returns the first ANode for which the
  // given predicate returns true
  abstract ANode<T> find(Predicate<T> finder);

  // helper for find, recurs through the nodes of the Deque
  // or returns the Sentinel signifying that the end 
  // has been reached
  abstract ANode<T> findHelp(Predicate<T> finder);

  abstract T getAtIndexHelp(int i);


}

// represents a dummy Node of generic type T at
// the front of a list of nodes 
class Sentinel<T> extends ANode<T> {
  Sentinel() {
    super.next = this;
    super.prev = this;
  }

  public T getAtIndex(int index) {
    if (index > 0) {
      return this.next.getAtIndexHelp(index - 1);
    } else {
      return this.prev.getAtIndexHelp(index + 1);
    }
  }

  // adds the given value as the next node in this
  // Sentinel and shifts the nodes of this Sentinel
  // toward the back
  void addAtHead(T value) {
    this.next = new Node<T>(value, this.next, this);
  }

  // adds the given value as the previous node in this
  // Sentinel and shifts the nodes of this Sentinel
  // forward
  void addAtTail(T value) {
    this.prev = new Node<T>(value, this, this.prev);
  }

  // the abstracted method for addAtHead and addAtTail,
  // utilizes the given boolean flag to know
  // which end the value will be added to and mutates
  // the node links accordingly
  // We chose not to use this because the abstraction isn't worth the complexity
  /*void addAtHeadOrTail(T value, boolean atHead) {
    if (atHead) {
      this.next = new Node<T>(value, this.next, this);
    } else {
      this.prev = new Node<T>(value, this, this.prev);
    }
  }*/

  // counts the number of nodes in this Sentinel
  int size() {
    return this.next.sizeHelp();
  }

  // helper for size -- returns 0 as it would mean
  // that the end has been reached
  int sizeHelp() {
    return 0;
  }

  // sets this Sentinel's next Node to the
  // given Node
  void updateNext(ANode<T> next2) {
    this.next = next2; 
  }

  // sets this Sentinel's previous Node to the
  // given Node
  void updatePrev(ANode<T> prev2) {
    this.prev = prev2;
  }

  // removes the node from the given index and returns it
  public T removeAtIndex(int index) {
    if (index > 0) {
      return this.next.removeAtIndexHelp(index - 1);
    } else {
      return this.prev.removeAtIndexHelp(index + 1);
    }
  }

  // helper for removeAtIndex, throws an error
  // as the Sentinel signifies the end of nodes containing data
  T removeAtIndexHelp(int index) {
    throw new RuntimeException("Can't remove from empty Sentinel");
  }

  // returns the first ANode which passes the given predicate
  ANode<T> find(Predicate<T> finder) {
    return this.next.findHelp(finder);
  }

  // helper for find, returns this Sentinel signifying that
  // the end has been reached
  ANode<T> findHelp(Predicate<T> finder) {
    return this;
  }

  T getAtIndexHelp(int i) {
    throw new RuntimeException("Can't remove from empty Sentinel");
  }
}

// represents a Node of generic type T
class Node<T> extends ANode<T> {
  T data;

  // the default constructor
  Node(T data) {
    super(null, null);
    this.data = data;
  }

  // the convenience constructor --
  // throws exception if the next and previous
  // nodes are null
  Node(T data, ANode<T> node1, ANode<T> node2) {
    super(node1, node2);
    this.data = data;

    if (node1 == null || node2 == null) {
      throw new IllegalArgumentException("Node is null");
    } else {
      node1.prev = this;
      node2.next = this;
    }
  }

  // helper for the size method -- counts this node
  // and the next node
  int sizeHelp() {
    return 1 + this.next.sizeHelp();
  }

  // sets this Node's next Node to the
  // given Node
  void updateNext(ANode<T> next2) {
    this.next = next2; 
  }

  // sets this Node's previous Node to the
  // given Node
  void updatePrev(ANode<T> prev2) {
    this.prev = prev2;
  }

  // helper for removeAtIndex, uses the given
  // index to remove this Node and return its
  // data, and updates the linking Nodes
  T removeAtIndexHelp(int index) {
    if (index == 0) {
      this.prev.updateNext(this.next);
      this.next.updatePrev(this.prev);
      return this.data;

    } else if (index > 0) {
      return this.next.removeAtIndexHelp(index - 1);

    } else {
      return this.prev.removeAtIndexHelp(index + 1);
    }
  }

  // returns the first ANode which passes
  // the given predicate
  ANode<T> find(Predicate<T> finder) {
    return this.findHelp(finder);
  }

  // helper for find, goes through the linked ANodes
  // to find the first one which passes the given predicate
  ANode<T> findHelp(Predicate<T> finder) {
    if (finder.test(this.data)) {
      return this;
    } else {
      return this.next.findHelp(finder);
    }
  }

  @Override
  T getAtIndexHelp(int index) {
    if (index == 0) {
      return this.data;

    } else if (index > 0) {
      return this.next.removeAtIndexHelp(index - 1);

    } else {
      return this.prev.removeAtIndexHelp(index + 1);
    }
  }
}