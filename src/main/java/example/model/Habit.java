package example.model;

public class Habit {
    private static int idCounter = 1;  // Счётчик для генерации уникальных ID
    private final int id;  // ID привычки
    private String name;  // Название привычки
    private int targetValue;  // Целевая цель для привычки
    private String unit;  // Единица измерения (например, "шклянки", "часы")
    private int[][] history;  // История по неделям

    // Конструктор для создания новой привычки
    public Habit(String name, int targetValue, String unit) {
        this.id = idCounter++;  // Генерация нового ID
        this.name = name;
        this.targetValue = targetValue;
        this.unit = unit;
        this.history = new int[5][7];  // История на 5 недель (по дням недели)
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setDailyValue(int week, int day, int value) {
        if (week >= 0 && week < history.length && day >= 0 && day < 7) {
            history[week][day] = value;
        }
    }

    public int[][] getHistory() {
        return history;
    }

    public int[] getWeeklyValues(int week) {
        return history[week];
    }

    public void setHistory(int[][] history) {
        this.history = history;
    }

}
