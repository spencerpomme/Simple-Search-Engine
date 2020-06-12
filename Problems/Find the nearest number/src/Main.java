import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> input = Arrays.stream(scanner.nextLine().split("\\s+"))
                                         .map(Integer::parseInt)
                                         .collect(Collectors.toCollection(ArrayList::new));

        int target = scanner.nextInt();
        int minDis = Integer.MAX_VALUE;

        ArrayList<Integer> result = new ArrayList<>();

        for (Integer e : input) {
            int dis = getDistance(target, e);
            if (dis < minDis) {
                minDis = dis;
                result.clear();
                result.add(e);
            }
            else if (dis == minDis) {
                result.add(e);
            }
        }

        Collections.sort(result);

        System.out.println(result.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
        );
    }

    private static int getDistance(int target, int element) {
        return Math.abs(target - element);
    }
}