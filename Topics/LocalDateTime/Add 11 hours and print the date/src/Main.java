import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine());
        LocalDate finalDate = LocalDate.from(dateTime.plusHours(11));
        System.out.println(finalDate);
    }
}