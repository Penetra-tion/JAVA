package com.company;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Main
{
    static Serializer Ser= new Serializer();
    @JsonTypeInfo(include=As.WRAPPER_OBJECT, use=Id.NAME)
   public static HashMap <Integer,Student> Journal = new HashMap<>();
   public static Scanner Scan= new Scanner(System.in);
   public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    static void AddStudent() {
        try {
            System.out.println("Введите ФИО студента: ");
            String FIO = reader.readLine();
            boolean containsSpecialCharacters = Pattern.compile("[^а-яА-Яa-zA-Z]").matcher(FIO).find();
            if (containsSpecialCharacters) {
                System.out.println("Неверный формат попробуйте снова");
                AddStudent();
            }
            System.out.println("Введите номер зачётной книжки студента: ");
            int Number = Integer.parseInt(reader.readLine());
            if (Number < 99999 || Number > 999999) {
                System.out.println("Неверное значение попробуйте снова");
                AddStudent();
            }
            if (Journal.containsKey(Number)) {
                System.out.println("Студент с таким номером зачётной книжки уже существует, введите другое значение");
                AddStudent();
            }
            int[] checkPoints = new int[5];
            for (int i = 0; i < 5; i++) {
                System.out.println("Введите оценку за " + (i + 1) + "-ую контрольную точку: ");
                checkPoints[i] = Integer.parseInt(reader.readLine());
                if (checkPoints[i] < 0 || checkPoints[i] > 100) {
                    System.out.println("Неверное значение попробуйте снова");
                    AddStudent();
                }
            }
            Student student = new Student(FIO, checkPoints, Number);
            Journal.put(Number, student);
            System.out.println("Студент успешно добавлен в журнал.");
            Select();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка, некорректный ввод числовых данных. Попробуйте снова.");
            AddStudent();
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
    static int FindStudent() throws IOException {
        System.out.println("Введите номер зачётной книжки студента, которого хотите найти:");
        int findStudent;
        try {
            findStudent = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка, некорректный ввод числовых данных. Попробуйте снова.");
            return FindStudent();
        }
        Student student = Journal.get(findStudent);
        if (student == null) {
            System.out.println("Студент с таким номером не найден.");
            System.out.println("1) Попробовать снова\n2) На главную");
            int select=0;
            try {
               select = Integer.parseInt(reader.readLine());
            }catch (NumberFormatException e)
            {
                System.out.println("Некорректный выбор. Возвращаюсь на главную.");
                Select();
            }
            switch (select) {
                case 1 -> {
                    return FindStudent();
                }
                case 2 -> Select();
                default -> {
                    System.out.println("Некорректный выбор. Возвращаюсь на главную.");
                    Select();
                }
            }
        } else {
            return findStudent;
        }
        return 0;
    }
    static void ShowFindStudent(int FindStudent){
            System.out.println("=========================================");
            System.out.print("ФИО: ");
            System.out.println(Journal.get(FindStudent).getFIO());
            System.out.print("Оценки за кт: ");
            System.out.println(Arrays.toString(Journal.get(FindStudent).getCheckPoints()));
            System.out.print("Номер зачётной книжки студента: ");
            System.out.println((Journal.get(FindStudent).getNumber()));
            System.out.println("=========================================");
    }
    static void ShowStudents(){
        if (!Journal.isEmpty()) {
            Journal.forEach((key, value) -> {
                System.out.println("=========================================");
                System.out.print("ФИО: ");
                System.out.println(value.getFIO());
                System.out.print("Оценки за кт: ");
                System.out.println(Arrays.toString(value.getCheckPoints()));
                System.out.print("Номер зачётной книжки студента: ");
                System.out.println((value.getNumber()));
                System.out.println("=========================================");
            });
        }
        else {
            System.out.println("Список пуст");
        }
    }
    static void RemoveStudent() throws IOException {

        System.out.print("Введите номер зачётной книжки студента, которого вы хотите удалить: ");
        try {
            int delStudent= Integer.parseInt(reader.readLine());
            if (Journal.get(delStudent)==null) {
                System.out.println("Студент с таким номером зачётной книжки не найден");
            }
            else {
                Journal.remove(delStudent);
                System.out.println("Контакт успешно удалён");
            }
        }catch (NumberFormatException e){
            System.out.println();
            System.out.println("Ошибка, некорректный ввод числовых данных. Попробуйте снова.");
            RemoveStudent();
        }

    }
    static void Select() throws IOException {
        int Select;
        while (true) {
            System.out.println("""
                Выберите действие:
                1) Добавить студента
                2) Поиск студента по номеру
                3) Показать всех студентов
                4) Выгрузить данные
                5) Загрузить данные
                6) Удалить студента
                7) Очистить все данные
                8) Завершить работу""");
            String input = Scan.nextLine();
            try {
                Select = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Некорректный выбор. Введите число от 1 до 8.");
                continue;
            }
            switch (Select) {
                case 1 -> AddStudent();
                case 2 -> ShowFindStudent(FindStudent());
                case 3 -> ShowStudents();
                case 4 -> Ser.Serialization();
                case 5 -> Ser.Deserialization();
                case 6 -> RemoveStudent();
                case 7 -> {
                    System.out.print("""
                            Вы действительно хотите это сделать ?
                            1) Да
                            2) Нет
                            """);
                    int select = Integer.parseInt(Scan.nextLine());
                    switch (select) {
                        case 1:
                            Journal.clear();
                            System.out.println("Данные удалены");
                            break;
                        case 2:
                            // Просто продолжаем цикл
                            break;
                        default:
                            System.out.println("Некорректный выбор. Введите число от 1 до 2.");
                    }
                }
                case 8 -> {
                    System.out.println("Завершение работы.");
                    System.exit(0);
                }
                default -> System.out.println("Некорректный выбор. Введите число от 1 до 8.");
            }
        }
    }
    public static void main(String[] args)throws IOException  {
        Select();
    }
}