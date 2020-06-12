import java.util.Scanner;

public class Main {

    public static Long convert(Long val) {
        if (val == null) {
            return 0L;
        } else {
            if (val > Integer.MAX_VALUE) {
                return (long) Integer.MAX_VALUE;
            }
            else if (val < Integer.MIN_VALUE) {
                return (long) Integer.MIN_VALUE;
            }
            else {
                return val;
            }
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        Long longVal = "null".equals(val) ? null : Long.parseLong(val);
        System.out.println(convert(longVal));
    }
}