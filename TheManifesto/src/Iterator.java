import java.util.ArrayList;
import java.util.NoSuchElementException;

//Represents the ability to produce a sequence of values
//of type T, one at a time
interface Iterator<T> {
  // Does this sequence have at least one more value?
  boolean hasNext();
  // Get the next value in this sequence, or
  // throws an exception if there aren't any
  // EFFECT: Advance the iterator to the subsequent value
  T next();
  
  void remove();
}

//Represents anything that can be iterated over
interface Iterable<T> {
  // Returns an iterator over this collection
  Iterator<T> iterator();
}


class AlternatingIterator<T> implements Iterator<T> {
  Iterator<T> iterator1;
  Iterator<T> iterator2;
  boolean isIterator1;

  AlternatingIterator(Iterator<T> iterator1, Iterator<T> iterator2) {
    this.iterator1 = iterator1;
    this.iterator2 = iterator2;
    this.isIterator1 = true;
  }

  public boolean hasNext() {
    return this.iterator1.hasNext() || this.iterator2.hasNext(); 
  }

  public T next() {
    if (!this.iterator1.hasNext()) {
      return this.iterator2.next();
    } else if (!this.iterator2.hasNext()) {
      return this.iterator1.next();
    } else if (this.isIterator1) {
      this.isIterator1 = false;
      return this.iterator1.next();
    } else {
      this.isIterator1 = true;
      return this.iterator2.next();
    }
  }

  public void remove() {
    throw new RuntimeException();
  }
}

class ArrayListIterator<T> implements Iterator<T> {
  // the list of items that this iterator iterates over
  ArrayList<T> items;
  // the index of the next item to be returned
  int nextIdx;
  // Construct an iterator for a given ArrayList
  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.nextIdx = 0;
  }

  public boolean hasNext() {
    return this.nextIdx < this.items.size();
  }

  //Get the next value in this sequence, or throw an exception if there aren't any
  //EFFECT: Advance the iterator to the subsequent value
  public T next() {
    if (!this.hasNext()) {  // defensively check whether any elements remain
      throw new NoSuchElementException(); // and fail if there aren't any
    }
    T answer = this.items.get(this.nextIdx);
    this.nextIdx = this.nextIdx + 1;
    return answer;
  }
  
  public void remove() {
    throw new RuntimeException();
  }
}


class IListIterator<T> implements Iterator<T> {
  IList<T> items;
  IListIterator(IList<T> items) {
    this.items = items;
  }

  public boolean hasNext() {
    return this.items.isCons();
  }

  public T next() {
    if (!this.hasNext()) {  // defensively check whether any elements remain
      throw new NoSuchElementException(); // and fail if there aren't any
    }
    ConsList<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

class DequeForwardIterator<T> implements Iterator<T> {
  Deque<T> deque;
  int x;

  DequeForwardIterator(Deque<T> deque) {
    this.deque = deque;
    this.x = 0;
  }

  public boolean hasNext() {
    return this.deque.size() >= x;
  }

  public T next() {
    if(!this.hasNext()) {
      throw new NoSuchElementException();
    }

    T answer = this.deque.find(x);
    x++;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

class DequeReverseIterator<T> implements Iterator<T> {
  Deque<T> deque;
  int x;

  DequeReverseIterator(Deque<T> deque) {
    this.deque = deque;
    this.x = deque.size() - 1;
  }

  public boolean hasNext() {
    return x < 0;
  }

  public T next() {
    if(!this.hasNext()) {
      throw new NoSuchElementException();
    }

    T answer = this.deque.find(x);
    x++;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

//Represents the subsequence of the first, third, fifth, etc. items from a given sequence
class EveryOtherIter<T> implements Iterator<T> {
  Iterator<T> source;
  EveryOtherIter(Iterator<T> source) {
    this.source = source;
  }
  public boolean hasNext() {
    // this sequence has a next item if the source does
    return this.source.hasNext();
  }
  public T next() {
    if (!this.hasNext()) {  // defensively check whether any elements remain
      throw new NoSuchElementException(); // and fail if there aren't any
    }
    T answer = this.source.next(); // gets the answer, and advances the source
    // We need to have the source "skip" the next value
    if (this.source.hasNext()) {
      this.source.next(); // get the next value and ignore it
    }
    return answer;
  }
  public void remove() {
    // We can remove an item if our source can remove the item
    this.source.remove(); // so just delegate to the source
  }
}
