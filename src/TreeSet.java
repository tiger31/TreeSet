import AVL.Tree;

import java.util.*;

public class TreeSet<T extends Comparable<T>> implements SortedSet<T> {
    private Tree<T> set;
    private TreeSet<T> superior = null;
    private T lowerBound = null;
    private T higherBound = null;
    private boolean includeHigher = true;
    private boolean includeLower = true;

    public TreeSet(){
        set = new Tree<>();
    }
    private TreeSet(TreeSet<T> superior, T loverBound, T higherBound, boolean includeLower, boolean includeHigher){
        this.set = superior.set;
        this.superior = superior;
        this.lowerBound = loverBound;
        this.higherBound = higherBound;
        this.includeLower = includeLower;
        this.includeHigher = includeHigher;
    }

    private boolean inBounds(T key) {
        int lowerComparison = key.compareTo(lowerBound);
        int higherComparison = key.compareTo(higherBound);
        return (((includeLower) ? lowerComparison >= 0 : lowerComparison > 0)
                && ((includeHigher) ? higherComparison <= 0 : higherComparison < 0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Comparator<? super T> comparator() {
        return (Comparator<T>) Comparator.naturalOrder();
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        T lower = lower();
        T higher = higher();
        if (superior != null
                && ((lower != null && fromElement.compareTo(lower) < 0)
                || (higher != null && toElement.compareTo(higher) > 0)))
            throw new IllegalArgumentException("key ot of range");
        return new TreeSet<>(this, fromElement, toElement, true, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        T higher = higher();
        if (superior != null && (higher != null && toElement.compareTo(higher) > 0))
            throw new IllegalArgumentException("key ot of range");
        return new TreeSet<>(this, null, toElement, true, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        T lower = lower();
        if (superior != null && (lower != null && fromElement.compareTo(lower) < 0))
            throw new IllegalArgumentException("key ot of range");
        return new TreeSet<>(this, fromElement, null, true, true);
    }

    private T lower() {
        if (superior != null && lowerBound == null)
            return superior.lower();
        return lowerBound;

    }
    private T higher() {
        if (superior != null && higherBound == null)
            return superior.higher();
        return higherBound;
    }

    @Override
    public T first() {
        if (superior == null) return set.first();
        else if (lowerBound == null) return superior.first();
        else {
            T nextToLower = set.next(lowerBound);
            if (set.contains(lowerBound))
                if (includeLower) return lowerBound;
            else if(!inBounds(nextToLower)) throw new NoSuchElementException();
            return nextToLower;
        }
    }

    @Override
    public T last() {
        if (superior == null) return set.last();
        else if (lowerBound == null) return superior.last();
        else {
            T prevToHigher = set.prev(higherBound);
            if (set.contains(higherBound))
                if (includeHigher) return higherBound;
            else if(!inBounds(prevToHigher)) throw new NoSuchElementException();
            return prevToHigher;
        }
    }

    @Override
    public int size() {
        return (superior == null) ? set.getSize() : (lowerBound == higherBound) ? 0 : set.count(lowerBound, higherBound, includeHigher && includeLower);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        return (superior == null || inBounds(t) && set.contains(t)) ;
    }

    private class TreeIterator implements Iterator<T> {
        T current = null;
        T first = null;
        T last = null;
        boolean end = false;

        TreeIterator() {
            if (!isEmpty()) {
                first = first();
                current = first;
                last = (superior == null) ? last() : higherBound;
            }
        }

        @Override
        public boolean hasNext() {
            return (!isEmpty() && !end);
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T out = current;
            current = set.next(out);
            end = ((includeHigher) ? out == last : current == last) || current == null;
            return out;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeIterator();
    }

    @Override
    public Object[] toArray() {
        int size = size();
        Object[] o = new Object[size];
        Iterator<T> iterator = iterator();
        for (int i = 0; i < size; i++)
            o[i] = iterator.next();
        return o;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        int size = size();
        if (a.length < size)
            a = (T1[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        Object[] result = a;
        Iterator<T> iterator = iterator();
        for (int i = 0; i < size; i++)
            result[i] = iterator.next();
        if (a.length > size)
            for (int i = size; i < a.length; i++)
                a[i] = null;
        return a;
    }

    @Override
    public boolean add(T t) {
        if (superior == null || inBounds(t))
            set.insert(t);
        else throw new IllegalArgumentException("key out of range");
        return set.is_modified();
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        if (superior == null || inBounds(t))
            set.remove(t);
        return set.is_modified();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T o : c)
            modified = add(o) ||  modified;
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        if (iterator().hasNext()) {
            T i = iterator().next();
            for (T j = i; iterator().hasNext(); j = iterator().next()) {
                if (!c.contains(j))
                    modified = remove(j) || modified;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c)
            modified = remove(o) || modified;
        return modified;
    }

    @Override
    public void clear() {
        if (superior == null)
            set.clear();
        else
            superior.removeAll(this);
    }
    @Override
    public int hashCode() {
        Iterator<T> i = iterator();
        int h = 0;
        while (i.hasNext())
            h += i.next().hashCode();
        return h;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Set<?> s = (Set<?>) o;
        if (s.size() != size())
            return false;

        try {
            for (T t : this)
                if (!s.contains(t))
                    return false;
        } catch (ClassCastException unused) {
            return false;
        }
        return true;
    }
}
