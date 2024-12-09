package example.controller;

import example.model.Habit;
import example.view.lanterna.LanternaMenu;
import example.model.HabitManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Habit> habits = new ArrayList<>();
    private LanternaMenu lanternaMenu;
    private HabitManager habitManager;

    public Controller(LanternaMenu lanternaMenu) throws IOException {
        this.habitManager = new HabitManager();
        loadHabits();
        loadHabitHistoryForAllHabits();
        initializeDefaultHabits();
    }

    public void setLanternaMenu(LanternaMenu lanternaMenu) {
        this.lanternaMenu = lanternaMenu;
    }

    private void loadHabits() {
        habits = habitManager.loadHabits();  // Загружаем все привычки из файла
    }

    public void loadHabitHistoryForAllHabits() {
        for (Habit habit : habits) {
            habitManager.loadHabitHistory(habit);  // Для каждой привычки вызываем loadHabitHistory
        }
    }

    private void addHabit() throws IOException {
        lanternaMenu.displayMessage("Enter habit details...");
        lanternaMenu.addHabitWindow(habits);  // Показываем окно для добавления привычки
    }

    private void inputHabitData() throws IOException {
        if (habits.isEmpty()) {
            lanternaMenu.displayMessage("You haven't added any habits yet.");
            return;
        }

        lanternaMenu.displayMessage("Enter habit data...");
        lanternaMenu.inputHabitDataWindow(habits);  // Показываем окно для ввода данных
    }

    private void initializeDefaultHabits() throws IOException {
        if (habits.isEmpty()) {
            habits.add(new Habit("Drink water", 8, "glasses"));
            habits.add(new Habit("Sleep", 8, "hours"));
            habits.add(new Habit("Walk", 10000, "steps"));
            lanternaMenu.displayMessage("Added default habits.");

            for (Habit habit : habits) {
                habitManager.saveHabit(habit);  // Сохраняем стандартные привычки
            }
        }
    }

    public void triggerSaveSelectedHabitHistory(String habitName, int week, int day, int value) throws IOException {
        Habit selectedHabit = findHabitByName(habitName);
        if (selectedHabit != null) {
            saveSelectedHabitHistory(selectedHabit, week, day, value);
            lanternaMenu.displayMessage("History for habit " + habitName + " has been saved.");
        } else {
            lanternaMenu.displayMessage("Habit with the name " + habitName + " not found.");
        }
    }

    private void showHabitList() throws IOException {
        if (habits.isEmpty()) {
            lanternaMenu.displayMessage("You haven't added any habits yet.");
            return;
        }

        lanternaMenu.displayMessage("Displaying habit list...");
        lanternaMenu.displayHabitList(habits);
    }

    private void generateWeeklyReport() throws IOException {
        if (habits.isEmpty()) {
            lanternaMenu.displayMessage("You haven't added any habits yet.");
            return;
        }

        lanternaMenu.displayMessage("Generating weekly report...");
        // Проверяем, что список привычек не пуст
        for (Habit habit : habits) {
            lanternaMenu.displayWeeklyReport(habit);  // Для каждой привычки вызываем метод WeeklyReportWindow
        }
    }

    private void showHabitHistory() throws IOException {
        if (habits.isEmpty()) {
            lanternaMenu.displayMessage("You haven't added any habits yet.");
            return;
        }

        lanternaMenu.displayMessage("Displaying habit history...");
        lanternaMenu.showHabitHistoryWindow(habits);  // Показываем окно с историей привычек
    }

    private Habit findHabitByName(String habitName) {
        for (Habit habit : habits) {
            if (habit.getName().equals(habitName)) {
                return habit;
            }
        }
        return null;
    }

    private void saveSelectedHabitHistory(Habit habit, int week, int day, int value) {
        habitManager.saveHabitHistory(habit, week, day, value);
    }

    public void triggerAddHabit() throws IOException {addHabit();}

    public void triggerInputHabitData() throws IOException {
        inputHabitData();
    }

    public void triggerShowHabitList() throws IOException {
        showHabitList();
    }

    public void triggerGenerateWeeklyReport() throws IOException {
        generateWeeklyReport();
    }

    public void triggerShowHabitHistory() throws IOException {
        showHabitHistory();
    }
}
