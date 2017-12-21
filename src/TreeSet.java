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
        int lowerComparison = key.compareTo(first());
        int higherComparison = key.compareTo(last());
        return ((includeLower) ? lowerComparison >= 0 : lowerComparison > 0
                && (includeHigher) ? higherComparison <= 0 : higherComparison < 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Comparator<? super T> comparator() {
        return (Comparator<T>) Comparator.naturalOrder();
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new TreeSet<>(this, fromElement, toElement, true, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return new TreeSet<>(this, null, toElement, true, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new TreeSet<>(this, fromElement, null, true, true);
    }

    @Override
    public T first() {
        return (superior == null || lowerBound == null) ? set.first() : lowerBound;
    }

    @Override
    public T last() {
        return (superior == null || higherBound == null) ? set.last() : higherBound;
    }

    @Override
    public int size() {
        return (superior == null) ? set.getSize() : (lowerBound == higherBound) ? 0 : set.count(lowerBound, higherBound);
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

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            T first = first();
            T last = last();
            T current = null;
            boolean end = false;
            @Override
            public boolean hasNext() {
                return (!isEmpty() && (!end) || current == null);
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T out = current;
                if (current == null)
                    out = (includeLower) ? first : set.next(first);
                current = set.next(out);
                end = (includeHigher) ? out == last : current == last;
                return out;
            }
        };
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
        return true;
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        if (superior == null || inBounds(t))
            set.remove(t);
        return true;
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
        for (T o : c)
            add(o);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        iterator().forEachRemaining(T -> {
            if (!c.contains(T))
                remove(T);
        });
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c)
            remove(o);
        return true;
    }

    @Override
    public void clear() {
        if (superior == null)
            set.clear();
        else
            superior.removeAll(this);
    }
}
