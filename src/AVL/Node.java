package AVL;

class Node<T extends Comparable<T>> {
    final T key;
    Node<T> left;
    Node<T> right;

    Node (T key){
        this.key = key;
    }
    int getHeight() {
        int leftHeight = (left != null) ? left.getHeight() : 0;
        int rightHeight = (right != null) ? right.getHeight() : 0;
        return ((leftHeight > rightHeight) ? leftHeight : rightHeight) + 1;
    }
    int factor() {
        int leftHeight = (left != null) ? left.getHeight() : 0;
        int rightHeight = (right != null) ? right.getHeight() : 0;
        return rightHeight - leftHeight;
    }
}
