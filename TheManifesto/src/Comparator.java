import java.util.Comparator;

interface IPred<T> {
  boolean apply(T t);
}

interface IComparator<T> extends Comparator<T> {
  int compare(T elem1, T elem2);
}

class CompareByInteger implements IComparator<Integer> {
  public int compare(Integer elem1, Integer elem2) {
    return elem1 - elem2;
  }
}

class CompareByAlphabet implements IComparator<String> {
  public int compare(String elem1, String elem2) {
    return elem1.compareTo(elem2);
  }
}

//Interface for one-argument function-objects with signature [A -> R]
interface IFunc<A, R> {
  R apply(A arg);
}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
R apply(A1 arg1, A2 arg2);
}

interface IList<T> {
  
  int length();
  
  IList<T> filter(IPred<T> pred);
  
  IList<T> sort(IComparator<T> comp);
  
  IList<T> insert(IComparator<T> comp, T element);
  
  <U> IList<U> map(IFunc<T, U> f);
  
  <U> U foldr(IFunc2<T, U, U> func, U base);
  
}

class MtList<T> implements IList<T> {
  
  public int length() { return 0; }
  
  public IList<T> filter(IPred<T> pred) { return this; }
  
  public IList<T> sort(IComparator<T> comp) { return this; }
  
  public IList<T> insert(IComparator<T> comp, T element) {
    return new ConsList<T>(element, this);
  }

  public <U> IList<U> map(IFunc<T, U> f) { 
    return new MtList<U>(); 
  }
  
  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    return base;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public int length() {
    return 1 + this.rest.length();
  }
  
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  public IList<T> sort(IComparator<T> comp) {
    return this.rest.sort(comp).insert(comp, this.first);
  }

  public IList<T> insert(IComparator<T> comp, T element) {
    if (comp.compare(this.first, element) < 0) {
      return new ConsList<T>(this.first, this.rest.insert(comp, element));
    }
    else {
      return new ConsList<T>(element, this);
    }
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }
  
  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    return func.apply(this.first, this.rest.foldr(func, base));
  }
}