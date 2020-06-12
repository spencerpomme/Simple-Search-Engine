import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int[] array = Arrays.stream(scanner.nextLine().split("\\s"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
        int length = array.length;
        int rotate = scanner.nextInt() % length;
        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
            int index = i + rotate >= length ? i + rotate - length : i + rotate;
            res[index] = array[i];
        }

        System.out.println(
                Arrays.toString(res)
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", "")
        );
    }
}