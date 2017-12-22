import java.util.SortedSet;

public class Main {
    public static void main(String[] args) {
        SortedSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(4);
        SortedSet<Integer> sub = set.subSet(3,7);
        SortedSet<Integer> subSub = sub.subSet(4,6);
        sub.forEach(System.out::println);
        System.out.println("sub---");
        subSub.forEach(System.out::println);
        System.out.println("subsub---");
        sub.add(5+1);
        set.forEach(System.out::println);
        set.add(5+2);
        sub.forEach(System.out::println);
        System.out.println("sub---");
        subSub.forEach(System.out::println);
        subSub.iterator();
        System.out.println("subsub---");
    }
}
