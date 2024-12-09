package example;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import example.controller.Controller;
import example.view.lanterna.LanternaMenu;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем терминал с помощью DefaultTerminalFactory
            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            // Создаем экран с использованием терминала
            Screen screen = new TerminalScreen(terminal);

            // Инициализируем экран
            screen.startScreen();

            // Создаем объект LanternaMenu и передаем экран
            LanternaMenu lanternaMenu = new LanternaMenu(screen);

            // Создаем объект Controller и передаем в него меню
            Controller controller = new Controller(lanternaMenu);

            // Устанавливаем контроллер в меню
            lanternaMenu.setController(controller);

            // Создаем объект MultiWindowTextGUI (это новый подход в Lanterna)
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            // Устанавливаем GUI в LanternaMenu для управления окнами
            lanternaMenu.setGUI(gui);

            // Запускаем приложение
            lanternaMenu.start();

            // Закрываем экран после выхода из приложения
            screen.stopScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
