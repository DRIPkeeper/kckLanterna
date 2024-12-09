package example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {
    private static final String HABITS_FILE = "habits.txt";  // Путь к файлу привычек
    private static final String HISTORY_FILE = "habit_history.txt";  // Путь к файлу истории привычек

    // Метод для загрузки привычек из файла
    public List<Habit> loadHabits() {
        List<Habit> habits = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HABITS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int targetValue = Integer.parseInt(parts[1]);
                    String unit = parts[2];
                    Habit habit = new Habit(name, targetValue, unit);
                    habits.add(habit);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке привычек: " + e.getMessage());
        }
        return habits;
    }

    // Метод для сохранения привычки в файл
    public void saveHabit(Habit habit) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HABITS_FILE, true))) {
            writer.write(habit.getName() + "," + habit.getTargetValue() + "," + habit.getUnit());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении привычки: " + e.getMessage());
        }
    }

    // Метод для загрузки истории привычек из файла
    public void loadHabitHistory(Habit habit) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int habitId = Integer.parseInt(parts[0]);
                    if (habit.getId() == habitId) {  // Проверяем, что ID привычки совпадает
                        int week = Integer.parseInt(parts[1]);
                        int day = Integer.parseInt(parts[2]);
                        int value = Integer.parseInt(parts[3]);
                        habit.setDailyValue(week, day, value);  // Устанавливаем значение в историю
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке истории привычек: " + e.getMessage());
        }
    }

    // Метод для сохранения истории привычек в файл
    public void saveHabitHistory(Habit habit, int week, int day, int value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.write(habit.getId() + "," + week + "," + day + "," + value);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении истории привычки: " + e.getMessage());
        }
    }
}
