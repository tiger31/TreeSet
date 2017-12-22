package AVL;

import java.util.NoSuchElementException;

public class Tree<T extends Comparable<T>> {
    private Node<T> root;
    private int size = 0;
    private boolean modified = false;

    public int getSize() {
        return size;
    }
    public int count(T from, T to, boolean includeHigher) {
        int count = 0;
        if (contains(from)) count++;
        T current = next(from);
        if (current == null) return 0;
        do {
            current = next(current);
            count++;
        }
        while (current != null && current.compareTo(to) < 0);
        return count + ((includeHigher) ? 1 : 0);
    }

    private Node<T> turnLeft(Node<T> n) {
        Node<T> right = n.right;
        n.right = right.left;
        right.left = n;
        return right;
    }
    private Node<T> turnRight(Node<T> n) {
        Node<T> left = n.left;
        n.left = left.right;
        left.right = n;
        return left;
    }
    private Node<T> balance(Node<T> n) {
        if (n.factor() == 2) {
            if (n.right.factor() < 0)
                n.right = turnRight(n.right);
            return turnLeft(n);
        }
        if (n.factor() == -2) {
            if (n.left.factor() > 0)
                n.left = turnLeft(n.left);
            return turnRight(n);
        }
        return n;
    }

    private Node<T> min(Node<T> n) {
        if (n.left == null) return n;
        else return min(n.left);
    }
    private Node<T> max(Node<T> n) {
        if (n.right == null) return n;
        else return max(n.right);
    }
    private Node<T> nextAfter(Node<T> n, Node<T> prev, T key) {
        int comparison = key.compareTo(n.key);
        Node<T> closest = (comparison < 0 && n.key.compareTo(prev.key) < 0) ? n : prev;
        if (comparison < 0 && n.left != null) {
            closest = nextAfter(n.left, closest, key);
        } else if (comparison > 0 && n.right != null) {
            closest = nextAfter(n.right, closest, key);
        } else if (comparison == 0){
            if (n.right != null) {
                return min(n.right);
            }
        }
        return closest;
    }
    private Node<T> prevBefore(Node<T> n, Node<T> prev, T key) {
        int comparison = key.compareTo(n.key);
        Node<T> closest = (comparison > 0 && n.key.compareTo(prev.key) > 0) ? n : prev;
        if (comparison < 0 && n.left != null) {
            closest = prevBefore(n.left, closest, key);
        } else if (comparison > 0 && n.right != null) {
            closest = prevBefore(n.right, closest, key);
        } else if (comparison == 0){
            if (n.left != null) {
                return max(n.left);
            }
        }
        return closest;
    }


    private boolean contains(Node<T> n, T key) {
        if (n == null) return false;
        int comparison = key.compareTo(n.key);
        if (comparison < 0) {
            return contains(n.left, key);
        } else
            return comparison == 0 || contains(n.right, key);
    }
    private Node<T> insert(Node<T> n, T key) {
        if (n == null) {
            size++;
            modified = true;
            return new Node<>(key);
        }
        int comparison = key.compareTo(n.key);
        if (comparison < 0) {
            n.left = insert(n.left, key);
        }
        if (comparison > 0) {
            n.right = insert(n.right, key);
        }
        return balance(n);
    }
    private Node<T> removeMinimum(Node<T> n) {
        if (n.left == null)
            return n.right;
        n.left = removeMinimum(n.left);
        return balance(n);
    }
    private Node<T> remove(Node<T> n, T key) {
        int comparison = key.compareTo(n.key);
        if (comparison < 0 && n.left != null) {
            n.left = remove(n.left ,key);
        } else if (comparison > 0 && n.right != null) {
            n.right = remove(n.right, key);
        } else if (comparison == 0) {
            size--;
            modified = true;
            if (n.right == null) return n.left;
            Node<T> swap = min(n.right);
            swap.right = removeMinimum(n.right);
            swap.left = n.left;
            return balance(swap);
        }
        return n;
    }

    public void remove(T key) {
        root = (root != null) ? remove(root, key) : null;
    }
    public void insert(T key) {
        root = insert(root, key);
    }
    public void clear() {
        root = null;
        size = 0;
    }
    public boolean contains(T key) {
        return (root != null && contains(root, key));
    }
    public T first() {
        if (root == null) throw new NoSuchElementException();
        return min(root).key;
    }
    public T last() {
        if (root == null) throw new NoSuchElementException();
        return max(root).key;
    }
    public T next(T key) {
        if (root == null) return null;
        T next = nextAfter(root, max(root), key).key;
        return (next.compareTo(key) > 0)  ? next : null;
    }
    public T prev(T key) {
        if (root == null) return null;
        T prev =  prevBefore(root, min(root), key).key;
        return (prev.compareTo(key) < 0) ? prev : null;
    }
    public boolean is_modified() {
        if (modified) {
            modified = false;
            return true;
        }
        return false;
    }
}
