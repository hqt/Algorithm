
class IntegerPairs implements Comparable {
  Integer _first, _second;

  public IntegerPairs(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(Object o) {
    if (this.first() != ((IntegerPairs )o).first())
      return this.first() - ((IntegerPairs )o).first();
    else
      return this.second() - ((IntegerPairs )o).second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}

