package search;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        int argNum = args.length;
        String fileName = "";
        for (int i = 0; i < argNum-1; i++) {
            if (args[i].equals("--data")) {
                fileName = args[i+1];
            }
        }

        String content = "";
        try {
            content = readFileAsString(fileName);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // string loaded ok

        String[] lines = content.split("\n");
        String[][] peopleList = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            peopleList[i] = lines[i].split(" ");
        }

        // reverse index
        Map<String, List<Integer>> reverseIndex = new HashMap<>();

        // build reverse index
        for (int i = 0; i < peopleList.length; i++) {
            for (String word : peopleList[i]) {
                if (reverseIndex.containsKey(word.toLowerCase().strip())) {
                    reverseIndex.get(word.toLowerCase().strip()).add(i);
                }
                else {
                    List<Integer> empty = new ArrayList<>();
                    empty.add(i);
                    reverseIndex.putIfAbsent(word.toLowerCase().strip(), empty);

                }
            }
            // check reverse list
            // System.out.println(reverseIndex.toString());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");
            int selection = scanner.nextInt();
            switch (selection) {
                case 1:
                    findPersonFast(scanner, peopleList, reverseIndex);
                    break;
                case 2:
                    printAll(peopleList);
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    public static void findPerson(Scanner scanner, String[][] peopleList) {

        System.out.println("Enter a name or email to search all suitable people.");
        String target = scanner.next();
        scanner.nextLine();

        int found = 0;
        for (String[] people : peopleList) {
            for (String word : people) {
                if (word.toLowerCase().strip().contains(target.toLowerCase().strip())) {
                    if (found == 0) {
                        System.out.println("\nFound people:");
                    }
                    System.out.println(Arrays.toString(people)
                            .replace(", ", " ")
                            .replace("[", "")
                            .replace("]", ""));
                    found++;
                    break;
                }
            }
        }
        if (found <= 0) {
            System.out.println("No matching people found.");
        }
        System.out.println();
    }

    public static void findPersonFast(Scanner scanner, String[][] peopleList, Map<String, List<Integer>> index) {

        // select matching mode:
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.next();
        scanner.nextLine();

        System.out.println("Enter a name or email to search all suitable people.");
        // change from a whole name to a list of keywords:
        List<String> targets = Arrays.asList(scanner.nextLine().split(" "));
        
        // String target = scanner.next();
        // scanner.nextLine();
        switch (strategy) {
            case "ANY":
                matchAny(targets, peopleList, index);
                break;
            case "ALL":
                matchAll(targets, peopleList, index);
                break;
            case "NONE":
                matchNone(targets, peopleList, index);
                break;
            default:
                System.out.println("Wrong model");
        }
    }

    public static void matchAll(List<String> targets, String[][] peopleList, Map<String, List<Integer>> index) {

        Set<Integer> results = new HashSet<>();
        if (index.get(targets.get(0).toLowerCase().strip()) == null) {
            results.add(null);
        }

        for (String target : targets) {
            if (index.containsKey(target.toLowerCase().strip())) {
                results.retainAll(index.get(target.toLowerCase().strip()));
            }
            System.out.println(results.toString());
        }
        if (results.size() == 0 || (results.size() == 1 && results.toArray()[0] == null)) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(results.toString());
            for (int result : results) {
                System.out.println(Arrays.toString(peopleList[result])
                        .replace(", ", " ")
                        .replace("[", "")
                        .replace("]", ""));
            }
        }
        System.out.println();
    }

    public static void matchAny(List<String> targets, String[][] peopleList, Map<String, List<Integer>> index) {

        Set<Integer> results = matchHelper(targets, index);
        if (results.size() == 0) {
            System.out.println("No matching people found.");
        } else {
            for (int result : results) {
                System.out.println(Arrays.toString(peopleList[result])
                        .replace(", ", " ")
                        .replace("[", "")
                        .replace("]", ""));
            }
        }
        System.out.println();
    }

    public static void matchNone(List<String> targets, String[][] peopleList, Map<String, List<Integer>> index) {

        Set<Integer> results = matchHelper(targets, index);
        if (results.size() >= peopleList.length) {
            System.out.println("No matching people found.");
        } else {
            for (int i = 0; i < peopleList.length; i++) {
                if (!results.contains(i)) {
                    System.out.println(Arrays.toString(peopleList[i])
                            .replace(", ", " ")
                            .replace("[", "")
                            .replace("]", ""));
                }
            }
        }
        System.out.println();
    }

    private static Set<Integer> matchHelper(List<String> targets, Map<String, List<Integer>> index) {
        Set<Integer> results = new HashSet<>(index.get(targets.get(0).toLowerCase().strip()));

        for (String target : targets) {
            if (index.containsKey(target.toLowerCase().strip())) {
                results.addAll(index.get(target.toLowerCase().strip()));
            }
            System.out.println(results.toString());
        }
        return results;
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void printAll(String[][] peopleList) {
        System.out.println("=== List of people ===");
        for (String[] people : peopleList) {
            System.out.println(Arrays.toString(people)
                    .replace(", ", " ")
                    .replace("[", "")
                    .replace("]", ""));
        }
        System.out.println();
    }

    public static String selectStrategy(Scanner scanner) throws Exception {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();
        List<String> strategies = List.of("ALL", "ANY", "NONE");
        if (strategies.contains(strategy)) {
            return strategy;
        }
        else {
            throw new Exception("Please select from ALL, ANY, NONE");
        }
    }
}
