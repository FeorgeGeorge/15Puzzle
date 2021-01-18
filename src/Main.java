import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please, enter the width (m):");
        int width = input.nextInt(); // todo: handle incorrect input;
        System.out.println("Please, enter the height (n):");
        int height = input.nextInt(); // todo: handle incorrect input;

        while(input.hasNext()) {
            String s = input.nextLine();
            Scanner line = new Scanner(s);
            while(line.hasNextInt()) {
                // todo: finish;
            }
        }
    }
}
