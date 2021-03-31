class DequeIterator implements Iterator<T> {
  Deque deque;
  Index x;

  DequeIterator(Deque deque) {
    this.deque = deque;
    this.x = x;
  }

  boolean hasNext() {
    return this.deque.size() >= index;
  }

  T next() {
    if(!this.hasNext()) {
      throw new NoSuchElementException();
    }

    T answer = this.deque;
    return T;
    x++;
  }
}