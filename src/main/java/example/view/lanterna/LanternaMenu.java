package example.view.lanterna;
import java.util.Scanner;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import example.controller.Controller;
import example.model.Habit;

import java.io.IOException;
import java.util.List;

public class LanternaMenu {

    private MultiWindowTextGUI gui;  // Для хранения MultiWindowTextGUI
           // Для хранения Screen
    private Controller controller;

    // Конструктор, передаем экран
    public LanternaMenu(Screen screen) {
        this.gui = new MultiWindowTextGUI(screen);  // Инициализируем gui
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    // Устанавливаем GUI в LanternaMenu
    public void setGUI(MultiWindowTextGUI gui) {
        this.gui = gui;
    }

    public void start() throws IOException {
        while (true) {
            int choice = displayMainMenu();
            switch (choice) {
                case 1:
                    controller.triggerAddHabit();
                    break;
                case 2:
                    controller.triggerInputHabitData();
                    break;
                case 3:
                    controller.triggerGenerateWeeklyReport();
                    break;
                case 4:
                    controller.triggerShowHabitList();
                    break;
                case 5:
                    controller.triggerShowHabitHistory();
                    break;
                case 6:
                    return; // Exit the program
                default:
                    displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    public void displayMessage(String message) throws IOException {
        // Создаем окно с сообщением
        BasicWindow messageWindow = new BasicWindow("Message");

        // Создаем панель для размещения компонента
        Panel panel = new Panel(new GridLayout(1));

        // Создаем метку с сообщением
        Label label = new Label(message);
        panel.addComponent(label);

        // Кнопка для закрытия окна
        Button closeButton = new Button("Close", () -> {
            gui.removeWindow(messageWindow);  // Закрыть окно при нажатии
        });
        panel.addComponent(closeButton);

        // Устанавливаем панель в окно
        messageWindow.setComponent(panel);

        // Добавляем окно в GUI и ожидаем его закрытия
        gui.addWindowAndWait(messageWindow);

        // Пауза перед продолжением (это можно сделать в цикле или после закрытия окна)
        try {
            Thread.sleep(2000);  // Подождать 2 секунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int displayMainMenu() throws IOException {
        // Создаем окно для главного меню
        BasicWindow menuWindow = new BasicWindow("Main Menu");

        // Создаем панель для размещения кнопок
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        final int[] choice = new int[1]; // Для хранения выбора

        // Создаем кнопки для каждого пункта меню
        Button addHabitButton = new Button("1. Add Habit", () -> choice[0] = 1);
        Button enterDataButton = new Button("2. Enter Daily Data", () -> choice[0] = 2);
        Button weeklyReportButton = new Button("3. Weekly Report", () -> choice[0] = 3);
        Button habitListButton = new Button("4. Habit List", () -> choice[0] = 4);
        Button habitHistoryButton = new Button("5. Habit History", () -> choice[0] = 5);
        Button exitButton = new Button("6. Exit", () -> choice[0] = 6);

        // Добавляем кнопки на панель
        panel.addComponent(addHabitButton);
        panel.addComponent(enterDataButton);
        panel.addComponent(weeklyReportButton);
        panel.addComponent(habitListButton);
        panel.addComponent(habitHistoryButton);
        panel.addComponent(exitButton);

        // Устанавливаем панель в окно
        menuWindow.setComponent(panel);

        // Добавляем окно в GUI и ждем, пока не будет выбрана опция
        gui.addWindowAndWait(menuWindow);

        return choice[0]; // Возвращаем выбранный пункт меню
    }

    // Method for displaying habit list
    public void displayHabitList(List<Habit> habitList) throws IOException {
        // Создаем окно для списка привычек
        BasicWindow habitListWindow = new BasicWindow("Habit List");

        // Создаем панель для отображения списка привычек
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Добавляем заголовок
        panel.addComponent(new Label("Habit List:"));

        // Добавляем каждый элемент из списка привычек
        for (int i = 0; i < habitList.size(); i++) {
            String habitName = habitList.get(i).getName();
            panel.addComponent(new Label((i + 1) + ". " + habitName));
        }

        // Добавляем кнопку для возврата
        Button returnButton = new Button("Press any key to return", () -> habitListWindow.close());
        panel.addComponent(returnButton);

        // Устанавливаем панель в окно
        habitListWindow.setComponent(panel);

        // Добавляем окно в GUI и ждем, пока не будет выбрана опция
        gui.addWindowAndWait(habitListWindow);
    }


    // Method for displaying weekly report
    public void displayWeeklyReport(Habit habit) throws IOException {
        // Создаем окно для отчета
        BasicWindow reportWindow = new BasicWindow("Weekly Report for: " + habit.getName());

        // Создаем панель для размещения информации
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Добавляем заголовок
        panel.addComponent(new Label("Weekly Report for: " + habit.getName()));

        // Дни недели
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int[][] history = habit.getHistory();

        // Для каждой недели выводим отчет по дням
        for (int week = 0; week < history.length; week++) {
            panel.addComponent(new Label("Week " + (week + 1) + ":"));

            for (int day = 0; day < 7; day++) {
                String dayReport = daysOfWeek[day] + ": " + history[week][day] + "/" + habit.getTargetValue();
                panel.addComponent(new Label(dayReport));
            }
        }

        // Добавляем кнопку для возврата
        Button returnButton = new Button("Press any key to return", () -> reportWindow.close());
        panel.addComponent(returnButton);

        // Устанавливаем панель в окно
        reportWindow.setComponent(panel);

        // Добавляем окно в GUI и ждем, пока не будет выбрана опция
        gui.addWindowAndWait(reportWindow);
    }


    public void addHabitWindow(List<Habit> habits) {
        // Создаем окно для добавления привычки
        BasicWindow addHabitWindow = new BasicWindow("Add Habit");

        // Создаем панель для ввода данных
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Поле для ввода названия привычки
        TextBox nameTextBox = new TextBox();
        panel.addComponent(new Label("Enter habit name:"));
        panel.addComponent(nameTextBox);

        // Поле для ввода целевого значения
        TextBox targetValueTextBox = new TextBox();
        panel.addComponent(new Label("Enter target value (numeric):"));
        panel.addComponent(targetValueTextBox);

        // Поле для ввода единицы измерения (опционально)
        TextBox unitTextBox = new TextBox();
        panel.addComponent(new Label("Enter unit (optional):"));
        panel.addComponent(unitTextBox);

        // Кнопка для сохранения привычки
        Button saveButton = new Button("Save Habit", () -> {
            String name = nameTextBox.getText();
            String targetValueInput = targetValueTextBox.getText();
            String unit = unitTextBox.getText().isEmpty() ? null : unitTextBox.getText();

            // Проверяем, что название привычки не пустое и целевое значение является числом
            if (name.isEmpty() || targetValueInput.isEmpty()) {
                showSimpleDialog("Validation Error", "Please fill out all required fields.");
                return;
            }

            try {
                int targetValue = Integer.parseInt(targetValueInput);

                // Создаем привычку и добавляем в список
                Habit newHabit = new Habit(name, targetValue, unit);
                habits.add(newHabit);

                // Триггерим сохранение привычки
                controller.triggerAddHabit();

                showSimpleDialog("Success", "Habit added successfully.");
                addHabitWindow.close();
            } catch (NumberFormatException e) {
                showSimpleDialog("Validation Error", "Target value must be a number.");
            } catch (IOException e) {
                showSimpleDialog("Error", "Error occurred while saving habit.");
            }
        });
        panel.addComponent(saveButton);

        // Кнопка для отмены
        Button cancelButton = new Button("Cancel", addHabitWindow::close);
        panel.addComponent(cancelButton);

        // Устанавливаем панель в окно
        addHabitWindow.setComponent(panel);

        // Добавляем окно в GUI
        gui.addWindowAndWait(addHabitWindow);
    }
    private void showSimpleDialog(String title, String message) {
        BasicWindow dialogWindow = new BasicWindow(title);

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label(message));
        Button closeButton = new Button("OK", dialogWindow::close);
        panel.addComponent(closeButton);

        dialogWindow.setComponent(panel);
        gui.addWindowAndWait(dialogWindow);
    }



    // Method to input daily data for a habit
    public void inputHabitDataWindow(List<Habit> habits) {
        // Создаем окно для ввода данных о привычке
        BasicWindow inputDataWindow = new BasicWindow("Input Habit Data");

        // Создаем основную панель
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Список привычек для выбора
        ComboBox<String> habitComboBox = new ComboBox<>();
        for (Habit habit : habits) {
            habitComboBox.addItem(habit.getName());
        }
        panel.addComponent(new Label("Select a habit:"));
        panel.addComponent(habitComboBox);

        // Поле для ввода номера недели
        TextBox weekTextBox = new TextBox();
        panel.addComponent(new Label("Enter week number:"));
        panel.addComponent(weekTextBox);

        // Поле для ввода дня недели
        TextBox dayTextBox = new TextBox();
        panel.addComponent(new Label("Enter day number (0 for Monday to 6 for Sunday):"));
        panel.addComponent(dayTextBox);

        // Поле для ввода значения
        TextBox valueTextBox = new TextBox();
        panel.addComponent(new Label("Enter value:"));
        panel.addComponent(valueTextBox);

        // Кнопка для сохранения данных
        Button saveButton = new Button("Save Data", () -> {
            int habitIndex = habitComboBox.getSelectedIndex();

            // Проверяем, что привычка выбрана
            if (habitIndex < 0 || habitIndex >= habits.size()) {
                showSimpleDialog("Error", "Please select a valid habit.");
                return;
            }

            Habit selectedHabit = habits.get(habitIndex);

            // Получаем номер недели
            int week;
            try {
                week = Integer.parseInt(weekTextBox.getText());
            } catch (NumberFormatException e) {
                showSimpleDialog("Validation Error", "Week number must be numeric.");
                return;
            }

            // Получаем номер дня недели
            int day;
            try {
                day = Integer.parseInt(dayTextBox.getText());
                if (day < 0 || day > 6) {
                    showSimpleDialog("Validation Error", "Day number must be between 0 and 6.");
                    return;
                }
            } catch (NumberFormatException e) {
                showSimpleDialog("Validation Error", "Day number must be numeric.");
                return;
            }

            // Получаем значение
            int value;
            try {
                value = Integer.parseInt(valueTextBox.getText());
            } catch (NumberFormatException e) {
                showSimpleDialog("Validation Error", "Value must be numeric.");
                return;
            }

            // Сохраняем данные привычки
            try {
                controller.triggerSaveSelectedHabitHistory(selectedHabit.getName(), week, day, value);
                showSimpleDialog("Success", "Data saved successfully.");
                inputDataWindow.close();
            } catch (IOException e) {
                showSimpleDialog("Error", "An error occurred while saving data.");
            }
        });
        panel.addComponent(saveButton);

        // Кнопка для отмены
        Button cancelButton = new Button("Cancel", inputDataWindow::close);
        panel.addComponent(cancelButton);

        // Устанавливаем панель в окно
        inputDataWindow.setComponent(panel);

        // Отображаем окно
        gui.addWindowAndWait(inputDataWindow);
    }


    // Method for displaying habit history
    public void showHabitHistoryWindow(List<Habit> habits) {
        if (habits.isEmpty()) {
            showSimpleDialog("Info", "You don't have any habits yet.");
            return;
        }

        // Создаем окно для отображения истории
        BasicWindow historyWindow = new BasicWindow("Habit History");

        // Основная панель для размещения элементов
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Заголовок
        panel.addComponent(new Label("Habit history for the last 5 weeks:").addStyle(SGR.BOLD));

        // Отображаем историю для каждой привычки
        for (Habit habit : habits) {
            panel.addComponent(new Label("Habit: " + habit.getName()).addStyle(SGR.UNDERLINE));

            int[][] history = habit.getHistory();
            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

            for (int week = 0; week < history.length; week++) {
                panel.addComponent(new Label("Week " + (week + 1) + ":").addStyle(SGR.BOLD));

                for (int day = 0; day < 7; day++) {
                    panel.addComponent(new Label("  " + daysOfWeek[day] + ": " + history[week][day] + "/" + habit.getTargetValue()));
                }
            }
        }

        // Кнопка для возврата
        Button backButton = new Button("Back", historyWindow::close);
        panel.addComponent(backButton);

        // Устанавливаем панель в окно
        historyWindow.setComponent(panel);

        // Отображаем окно
        gui.addWindowAndWait(historyWindow);
    }

}
