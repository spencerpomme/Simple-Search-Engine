import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine().toLowerCase();
        String second = scanner.nextLine().toLowerCase();
        Map<String, Integer> firstMap = new HashMap<>();
        Map<String, Integer> secondMap = new HashMap<>();

        for (String s : first.split("")) {
            if (firstMap.containsKey(s)) {
                firstMap.replace(s, firstMap.get(s) + 1);
            }
            else firstMap.putIfAbsent(s, 1);
        }

        for (String s : second.split("")) {
            if (secondMap.containsKey(s)) {
                secondMap.replace(s, secondMap.get(s) + 1);
            }
            else secondMap.putIfAbsent(s, 1);
        }
        System.out.println(firstMap.equals(secondMap) ? "yes" : "no");
    }
}