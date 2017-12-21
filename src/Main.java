import java.util.SortedSet;

public class Main {
    public static void main(String[] args) {
        SortedSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(3);
        set.add(8);
        set.add(4);
        set.add(2);
        set.forEach(System.out::println);
        SortedSet<Integer> sub = set.subSet(3,5+3);
        System.out.println("---");
        sub.add(5+1);
        set.forEach(System.out::println);
        set.add(5+2);
        System.out.println("---");
        sub.forEach(System.out::println);
    }
}
