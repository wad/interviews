package org.wadhome.codeproblems;

import java.util.*;

public class ListReverserExperiment {

    // the main method is the entry point
    public static void main(String... args) {
        ListReverserExperiment listReverserExperiment = new ListReverserExperiment();
        System.out.println("\n----------------> 1 <----------------");
        listReverserExperiment.run1(getNames());
        System.out.println("\n----------------> 2 <----------------");
        listReverserExperiment.run2(getNames());
        System.out.println("\n----------------> 3 <----------------");
        listReverserExperiment.run3(getNames());
        System.out.println("\n----------------> 4 <----------------");
        listReverserExperiment.run4(getNames());
        System.out.println("\n----------------> 5 <----------------");
        listReverserExperiment.run5(getNames());
    }

    void run1(List names) {
        // number of times to go through the loop
        final int n = names.size();

        // go over the names in reverse order
        for (int i = n; i > 0; i = i - 1) {
            String name = (String) names.get(i - 1);
            String message = "Found this name: " + name;
            System.out.println(message);
        }
    }

    void run2(List names) {
        Collections.reverse(names);
        for (String name : (List<String>) names) {
            System.out.println("Found this name: " + name);
        }
    }

    void run3(List names) {
        while (!names.isEmpty()) {
            System.out.println("Found this name: " + names.get(names.size() - 1));
            names.remove(names.size() - 1);
        }
    }

    void run4(List names) {
        ListIterator li = names.listIterator(names.size());
        while (li.hasPrevious()) {
            String name = (String) li.previous();
            System.out.println("Found this name: " + name);
        }
    }

    void run5(List names) {
        Iterable<String> iterable = iterableReverseList(names);
        for (String name : iterable) {
            System.out.println("Found this name: " + name);
        }
    }

    static <T> Iterable<T> iterableReverseList(final List<T> l) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    ListIterator<T> listIter = l.listIterator(l.size());
                    public boolean hasNext() { return listIter.hasPrevious(); }
                    public T next() { return listIter.previous(); }
                    public void remove() { listIter.remove(); }
                };
            }
        };
    }

    static List getNames() {
        return new ArrayList() {{
            add("Abigail");
            add("Barnaby");
            add("Claus");
            add("Dexter");
            add("Emma");
            add("Frankenstein");
            add("Genevieve");
        }};
    }

}
