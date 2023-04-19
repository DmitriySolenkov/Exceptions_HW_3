import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import MyExceptions.SaveFailedException;
import MyExceptions.WrongDateInputException;
import MyExceptions.WrongFullNameException;
import MyExceptions.WrongPhoneNumberInputException;
import MyExceptions.WrongSexInputException;

public class Ex1 {
    public static void main(String[] args) {
        String[] input = input();
        try {
            String birthdate = parseBirthDate(input);
            input = deleteNote(input, birthdate);
            String phoneNumber = parsePhoneNumber(input);
            input = deleteNote(input, phoneNumber);
            String sex = parseSex(input);
            input = deleteNote(input, sex);
            String[] fullName = parseFullName(input);
            for (int i = 0; i < 3; i++) {
                input = deleteNote(input, fullName[i]);
            }
            if (input.length != 0) {
                for (String element : input) {
                    System.out.println(element);
                }
                throw new WrongFullNameException(
                        "Ошибка ввода! После записи необходимых строк, перечисленные данные остались незадействованы!");
            } else {
                System.out.printf("ФИО: %s %s %s\n", fullName[0], fullName[1], fullName[2]);
                System.out.printf("Дата рождения: %s\n", birthdate);
                System.out.printf("Номер телефона: %s\n", phoneNumber);
                System.out.printf("Пол: %s\n", sex);
                saveFile(fullName, birthdate, phoneNumber, sex);
                System.out.println("Данные успешно сохранены!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    static String[] input() {
        while (true) {
            String[] input = null;
            try (Scanner scanner = new Scanner(System.in, "cp866")) {
                System.out.println("Введите данные в формате 'Фамилия Имя Отчество Датарождения Номертелефона Пол'");
                System.out.println("в произвольном порядке:");
                input = scanner.nextLine().split(" ");
                return input;
            } catch (Exception e) {
                System.out.println("Ввод не был осуществлен, попробуйте еще раз!");
            }

        }
    }

    // Убрать------------
    static void print(String[] array) {
        System.out.println("-------------");
        for (String e : array) {
            System.out.println(e);
        }
    }

    static String[] parseFullName(String[] input) throws WrongFullNameException {
        if (input.length >= 3) {
            String[] fullName = new String[3];
            for (int i = 0; i < fullName.length; i++) {
                fullName[i] = input[i];
            }
            return fullName;
        } else {
            throw new WrongFullNameException("Неверно введено ФИО! Не хватает данных!");
        }
    }

    static String parseBirthDate(String[] input) throws WrongDateInputException {
        String date = null;
        int count = 0;
        for (String element : input) {
            if (element.contains(".")) {
                String[] possibleDate = element.split("\\.");
                if (possibleDate.length != 3) {
                    throw new WrongDateInputException("В дате больше или меньше 3ех чисел: " + element);
                } else {
                    int day = Integer.parseInt(possibleDate[0]);
                    int month = Integer.parseInt(possibleDate[1]);
                    int year = Integer.parseInt(possibleDate[2]);
                    if (year < 1900 || year > 2022) {
                        throw new WrongDateInputException("Неверно введен год: " + element);
                    }
                    if (month < 1 || month > 12) {
                        throw new WrongDateInputException("Неверно введен месяц: " + element);
                    }
                    if (month == 2) {
                        if (day < 1 || day > 28) {
                            throw new WrongDateInputException("Неверно введен день: " + element);
                        }
                    }
                    if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (day < 1 || day > 30) {
                            throw new WrongDateInputException("Неверно введен день: " + element);
                        }
                    } else {
                        if (day < 1 || day > 31) {
                            throw new WrongDateInputException("Неверно введен день: " + element);
                        }
                    }
                    date = element;
                    count++;
                }
            }
        }
        if (count == 1) {
            return date;
        } else if (count > 1) {
            throw new WrongDateInputException("Слишком много дат!");
        } else {
            throw new WrongDateInputException("Не введена дата рождения! Ожидаемый формат: dd.mm.yyyy!");
        }
    }

    static String parsePhoneNumber(String[] input) throws WrongPhoneNumberInputException {
        String phoneNumberString = null;
        int count = 0;
        for (String element : input) {
            try {
                long phoneNumber = Long.parseLong(element);
                phoneNumberString = element;
                count++;
            } catch (Exception e) {

            }
        }
        if (count == 1) {
            return phoneNumberString;
        } else if (count > 1) {
            throw new WrongPhoneNumberInputException("Слишком много номеров телефонов!");
        } else {
            throw new WrongPhoneNumberInputException(
                    "Не введен номер телефона! Ожидаемый формат: целое беззнаковое число без форматирования!");
        }

    }

    static String parseSex(String[] input) throws WrongSexInputException {
        String sex = null;
        int count = 0;
        for (String element : input) {
            if (element.equals("f") || element.equals("m")) {
                sex = element;
                count++;
            }
        }
        if (count == 1) {
            return sex;
        } else if (count > 1) {
            throw new WrongSexInputException("Слишком много вводов пола!");
        } else {
            throw new WrongSexInputException(
                    "Не введен пол! Ожидаемый формат: символ латиницей f или m!");
        }

    }

    static String[] deleteNote(String[] input, String note) {
        String[] newInput = new String[input.length - 1];
        int count = 0;
        for (String element : input) {
            if (element != note) {
                newInput[count] = element;
                count++;
            }
        }
        return newInput;
    }

    static void saveFile(String[] fullName, String birthdate, String phoneNumber, String sex)
            throws SaveFailedException {
        String filePath = fullName[0] + ".txt";
        String note = "<" + fullName[0] + "> <" + fullName[1] + "> <" + fullName[2] + "> <" + birthdate + "> <"
                + phoneNumber + "> <" + sex + ">\n";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(note);
        } catch (IOException e) {
            throw new SaveFailedException("Ошибка записи в файл!");
        }
    }

}
