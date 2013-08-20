import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;


public class ErgoJavaTemplate {
	
	abstract class IntList extends IntCollection implements Comparable<IntList> {

		public abstract int get(int index);

		public IntIterator iterator() {
			return new IntIterator() {
				private int size = size();
				private int index = 0;

				public int value() throws NoSuchElementException {
					if (!isValid())
						throw new NoSuchElementException();
					return get(index);
				}

				public void advance() throws NoSuchElementException {
					if (!isValid())
						throw new NoSuchElementException();
					index++;
				}

				public boolean isValid() {
					return index < size;
				}
			};
		}

		public int hashCode() {
			int hashCode = 1;
			for (IntIterator i = iterator(); i.isValid(); i.advance())
				hashCode = 31 * hashCode + i.value();
			return hashCode;
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof IntList))
				return false;
			IntList list = (IntList)obj;
			if (list.size() != size())
				return false;
			IntIterator i = iterator();
			IntIterator j = list.iterator();
			while (i.isValid()) {
				if (i.value() != j.value())
					return false;
				i.advance();
				j.advance();
			}
			return true;
		}

		public int compareTo(IntList o) {
			IntIterator i = iterator();
			IntIterator j = o.iterator();
			while (true) {
				if (i.isValid()) {
					if (j.isValid()) {
						if (i.value() != j.value()) {
							if (i.value() < j.value())
								return -1;
							else
								return 1;
						}
					} else
						return 1;
				} else {
					if (j.isValid())
						return -1;
					else
						return 0;
				}
				i.advance();
				j.advance();
			}
		}

		}

	class CPPMap<K, V> extends EHashMap<K, V> {
		private final Factory<V> defaultValueFactory;

		public CPPMap(Factory<V> defaultValueFactory) {
			this.defaultValueFactory = defaultValueFactory;
		}

		public V get(Object key) {
			if (containsKey(key))
				return super.get(key);
			V value = defaultValueFactory.create();
			try {
				//noinspection unchecked
				super.put((K) key, value);
				return value;
			} catch (ClassCastException e) {
				return value;
			}
		}
	}

	interface Factory<V> {
		public V create();
	}

	class IntArrayList extends IntList {
		private int[] array;
		private int size;

		public IntArrayList() {
			this(10);
		}

		public IntArrayList(int capacity) {
			array = new int[capacity];
		}

		public IntArrayList(IntList list) {
			this(list.size());
			addAll(list);
		}

		public int get(int index) {
			if (index >= size)
				throw new IndexOutOfBoundsException();
			return array[index];
		}

		public int size() {
			return size;
		}

		public void add(int value) {
			ensureCapacity(size + 1);
			array[size++] = value;
		}

		public void ensureCapacity(int newCapacity) {
			if (newCapacity > array.length) {
				int[] newArray = new int[Math.max(newCapacity, array.length << 1)];
				System.arraycopy(array, 0, newArray, 0, size);
				array = newArray;
			}
		}

		public int[] toArray() {
			int[] array = new int[size];
			System.arraycopy(this.array, 0, array, 0, size);
			return array;
		}

	    }

	abstract class IntCollection {
		public abstract IntIterator iterator();
		public abstract int size();
		public abstract void add(int value);

		public int[] toArray() {
			int size = size();
			int[] array = new int[size];
			int i = 0;
			for (IntIterator iterator = iterator(); iterator.isValid(); iterator.advance())
				array[i++] = iterator.value();
			return array;
		}

		public void addAll(IntCollection values) {
			for (IntIterator it = values.iterator(); it.isValid(); it.advance()) {
				add(it.value());
			}
		}

		}

	abstract static class AbstractStringHash implements StringHash {
	    public static final long MULTIPLIER;
	    protected static final long FIRST_REVERSE_MULTIPLIER;
	    protected static final long SECOND_REVERSE_MULTIPLIER;
	    public static final long FIRST_MOD;
	    public static final long SECOND_MOD;

	    static {
	        Random random = new Random(System.currentTimeMillis());
	        FIRST_MOD = IntegerUtils.nextPrime((long) (1e9 + random.nextInt((int) 1e9)));
	        SECOND_MOD = IntegerUtils.nextPrime((long) (1e9 + random.nextInt((int) 1e9)));
			MULTIPLIER = random.nextInt((int) 1e9 - 257) + 257;
	        FIRST_REVERSE_MULTIPLIER = IntegerUtils.reverse(MULTIPLIER, FIRST_MOD);
	        SECOND_REVERSE_MULTIPLIER = IntegerUtils.reverse(MULTIPLIER, SECOND_MOD);
	    }

	    public long hash(int from) {
			return hash(from, length());
		}
	}

	static class SimpleStringHash extends AbstractStringHash {
	    private static long[] firstReversePower = new long[0];
	    private static long[] secondReversePower = new long[0];

	    private final long[] firstHash;
	    private final long[] secondHash;

	    public SimpleStringHash(CharSequence string) {
	        int length = string.length();
	        ensureCapacity(length);
			firstHash = new long[length + 1];
	        secondHash = new long[length + 1];
	        long firstPower = 1;
	        long secondPower = 1;
			for (int i = 0; i < length; i++) {
				firstHash[i + 1] = (firstHash[i] + string.charAt(i) * firstPower) % FIRST_MOD;
	            secondHash[i + 1] = (secondHash[i] + string.charAt(i) * secondPower) % SECOND_MOD;
				firstPower *= MULTIPLIER;
	            firstPower %= FIRST_MOD;
	            secondPower *= MULTIPLIER;
	            secondPower %= SECOND_MOD;
			}
		}

	    private void ensureCapacity(int length) {
	        if (firstReversePower.length >= length)
	            return;
	        length = Math.max(length + 1, firstReversePower.length << 1);
	        long[] oldFirst = firstReversePower;
	        long[] oldSecond = secondReversePower;
	        firstReversePower = new long[length];
	        secondReversePower = new long[length];
	        System.arraycopy(oldFirst, 0, firstReversePower, 0, oldFirst.length);
	        System.arraycopy(oldSecond, 0, secondReversePower, 0, oldSecond.length);
	        firstReversePower[0] = secondReversePower[0] = 1;
	        for (int i = Math.max(oldFirst.length, 1); i < length; i++) {
	            firstReversePower[i] = firstReversePower[i - 1] * FIRST_REVERSE_MULTIPLIER % FIRST_MOD;
	            secondReversePower[i]= secondReversePower[i - 1] * SECOND_REVERSE_MULTIPLIER % SECOND_MOD;
	        }
	    }

	    public long hash(int from, int to) {
			return (((firstHash[to] - firstHash[from] + FIRST_MOD) * firstReversePower[from] % FIRST_MOD) << 32) +
	            ((secondHash[to] - secondHash[from] + SECOND_MOD) * secondReversePower[from] % SECOND_MOD);
		}

		public int length() {
			return firstHash.length - 1;
		}
	}

	static class EHashMap<E, V> extends AbstractMap<E, V> {
		private static final int[] shifts = new int[10];

		private int size;
		private HashEntry<E, V>[] data;
		private int capacity;
		private Set<Entry<E, V>> entrySet;

		static {
			Random random = new Random(System.currentTimeMillis());
			for (int i = 0; i < 10; i++)
				shifts[i] = 1 + 3 * i + random.nextInt(3);
		}

		public EHashMap() {
			this(4);
		}

		private void setCapacity(int size) {
			capacity = Integer.highestOneBit(4 * size);
	        //noinspection unchecked
	        data = new HashEntry[capacity];
		}

		public EHashMap(int maxSize) {
			setCapacity(maxSize);
			entrySet = new AbstractSet<Entry<E, V>>() {
				@Override
				public Iterator<Entry<E, V>> iterator() {
					return new Iterator<Entry<E, V>>() {
						private HashEntry<E, V> last = null;
	                    private HashEntry<E, V> current = null;
	                    private HashEntry<E, V> base = null;
	                    private int lastIndex = -1;
						private int index = -1;

						public boolean hasNext() {
	                        if (current == null) {
	                            for (index++; index < capacity; index++) {
	                                if (data[index] != null) {
	                                    base = current = data[index];
	                                    break;
	                                }
	                            }
	                        }
	                        return current != null;
						}

						public Entry<E, V> next() {
							if (!hasNext())
								throw new NoSuchElementException();
	                        last = current;
	                        lastIndex = index;
	                        current = current.next;
	                        if (base.next != last)
	                            base = base.next;
							return last;
						}

						public void remove() {
							if (last == null)
								throw new IllegalStateException();
	                        size--;
	                        if (base == last)
	                            data[lastIndex] = last.next;
	                        else
	                            base.next = last.next;
						}
					};
				}

				@Override
				public int size() {
					return size;
				}
			};
		}

		public EHashMap(Map<E, V> map) {
			this(map.size());
			putAll(map);
		}

		public Set<Entry<E, V>> entrySet() {
			return entrySet;
		}

		public void clear() {
			Arrays.fill(data, null);
			size = 0;
		}

		private int index(Object o) {
			return getHash(o.hashCode()) & (capacity - 1);
		}

		private int getHash(int h) {
			int result = h;
			for (int i : shifts)
				result ^= h >>> i;
			return result;
		}

		public V remove(Object o) {
			if (o == null)
				return null;
			int index = index(o);
	        HashEntry<E, V> current = data[index];
	        HashEntry<E, V> last = null;
	        while (current != null) {
	            if (current.key.equals(o)) {
	                if (last == null)
	                    data[index] = current.next;
	                else
	                    last.next = current.next;
	                size--;
	                return current.value;
	            }
	            last = current;
	            current = current.next;
	        }
	        return null;
		}

		public V put(E e, V value) {
			if (e == null)
				return null;
			int index = index(e);
	        HashEntry<E, V> current = data[index];
	        if (current != null) {
	            while (true) {
	                if (current.key.equals(e)) {
	                    V oldValue = current.value;
	                    current.value = value;
	                    return oldValue;
	                }
	                if (current.next == null)
	                    break;
	                current = current.next;
	            }
	        }
	        if (current == null)
	            data[index] = new HashEntry<E, V>(e, value);
	        else
	            current.next = new HashEntry<E, V>(e, value);
	        size++;
	        if (2 * size > capacity) {
	            HashEntry<E, V>[] oldData = data;
	            setCapacity(size);
	            for (HashEntry<E, V> entry : oldData) {
	                while (entry != null) {
	                    HashEntry<E, V> next = entry.next;
	                    index = index(entry.key);
	                    entry.next = data[index];
	                    data[index] = entry;
	                    entry = next;
	                }
	            }
	        }
			return null;
		}

		public V get(Object o) {
			if (o == null)
				return null;
			int index = index(o);
	        HashEntry<E, V> current = data[index];
			while (current != null) {
				if (current.key.equals(o))
	                return current.value;
	            current = current.next;
	        }
			return null;
		}

		public boolean containsKey(Object o) {
	        if (o == null)
	            return false;
	        int index = index(o);
	        HashEntry<E, V> current = data[index];
	        while (current != null) {
	            if (current.key.equals(o))
	                return true;
	            current = current.next;
	        }
	        return false;
		}

		public int size() {
			return size;
		}

		private static class HashEntry<E, V> implements Entry<E, V> {
			private final E key;
			private V value;
	        private HashEntry<E, V> next;

	        private HashEntry(E key, V value) {
	            this.key = key;
	            this.value = value;
	        }


	        public E getKey() {
	            return key;
	        }

	        public V getValue() {
	            return value;
	        }

	        public V setValue(V value) {
	            V oldValue = this.value;
	            this.value = value;
	            return oldValue;
	        }
	    }
	}

	interface IntIterator {
		public int value() throws NoSuchElementException;
		/*
		 * @throws NoSuchElementException only if iterator already invalid
		 */
		public void advance() throws NoSuchElementException;
		public boolean isValid();
	}

	interface StringHash {
	    long hash(int from, int to);

		int length();
	}

	static class IntegerUtils {

	    public static long power(long base, long exponent, long mod) {
			if (base >= mod)
				base %= mod;
			if (exponent == 0)
				return 1 % mod;
			long result = power(base, exponent >> 1, mod);
			result = result * result % mod;
			if ((exponent & 1) != 0)
				result = result * base % mod;
			return result;
		}

		public static long reverse(long number, long module) {
			return power(number, module - 2, module);
		}

		public static boolean isPrime(long number) {
			if (number < 2)
				return false;
			for (long i = 2; i * i <= number; i++) {
				if (number % i == 0)
					return false;
			}
			return true;
		}

		public static long nextPrime(long from) {
	        if (from <= 2)
	            return 2;
	        from += 1 - (from & 1);
	        while (!isPrime(from))
	            from += 2;
	        return from;
	    }

	}

}
