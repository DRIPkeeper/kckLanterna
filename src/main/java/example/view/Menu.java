package example.view;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    // Главное меню
    public int displayMainMenu() {
        System.out.println("\n=== Główne Menu ===");
        System.out.println("1. Dodaj przyzwyczajenie");
        System.out.println("2. Wprowadź dane na dzień");
        System.out.println("3. Raport tygodniowy");
        System.out.println("4. Lista przyzwyczajeń");
        System.out.println("5. Historia przyzwyczajeń");
        System.out.println("6. Wyjście");
        System.out.print("Wybierz opcję: ");
        return scanner.nextInt();
    }

    // Отображение сообщения
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
