import java.util.Scanner;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(dateInEpochSecond(scanner.nextLine()));
    }  

    public static long dateInEpochSecond(String text) {
        Instant epoch = Instant.parse(text);
        return epoch.getEpochSecond();
    }
}