class DequeIterator implements Iterator<T> {
  Deque deque;
  int x;
  private Deque this.deque.find(x);

  DequeIterator(Deque deque) {
    this.deque = deque;
    this.x = deque.size() - 1;
  }

  boolean hasNext() {
    return x < 0;
  }

  T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException();
    }

    T answer = this.deque.find(x);
    x++;
    return answer
  }
}