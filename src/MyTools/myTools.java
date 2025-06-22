/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTools;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author TruongNX
 */
public class myTools {

    public static final Scanner sc = new Scanner(System.in);

    public static boolean parseBoolean(String input) {
        input = input.trim().toUpperCase();
        char c = input.charAt(0);
        return c == 'T' || c == '1' || c == 'Y';
    }

    public static String normalizeDateStr(String dateStr) {
        String result = dateStr.replaceAll("(\\s]+", "");
        result = result.replaceAll("[./-]+", "");
        return result;
    }

    public static Date parseDate(String inputStr, String dateFormat) {
        inputStr = normalizeDateStr(inputStr);
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(inputStr);
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }

    public static String toString(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static int getPart(Date d, int calendarPart) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(calendarPart);
    }

    public static boolean readBoolean(String prompt) {
        System.out.print(prompt + ": ");
        return parseBoolean(sc.nextLine());
    }

    public static String readstr(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }

    public static String readStr(String prompt, String pattern) {
        String input;
        boolean valid = false;
        do {
            System.out.print(prompt + ": ");

            input = sc.nextLine().trim();
            valid = input.matches(pattern);
        } while (valid == false);
        return input;
    }

    public static Date readDate(String prompt, String dateFormat) {
        String input;
        Date d;
        do {
            System.out.print(prompt + ": ");

            input = sc.nextLine().trim();
            d = parseDate(input, dateFormat);
            if (d == null) {
                System.out.println("Date data is not valid!");
            }
        } while (d == null);
        return d;
    }

    public static Date readDateAfter(String prompt, String dateFormat, Date markerDate) {
        String input;
        Date d;
        boolean ok = false;
        do {
            System.out.print(prompt + ": ");
            input = sc.nextLine().trim();

            d = parseDate(input, dateFormat);
            ok = (d != null && d.after(markerDate));
            if (d == null) {
                System.out.println("Date data is not valid!");
            }
        } while (!ok);
        return d;
    }

    public static String generateCode(String prefix, int length, int curNumber) {
        String formStr = "40" + length + "d";
        return prefix + String.format(formStr, curNumber);
    }

    public static int getNumberInCode(String code, String prefix) {
        return Integer.parseInt(code.substring(prefix.length()));
    }

    public static int getNumberInCode(String code, int prefixLength) {
        return Integer.parseInt(code.substring(prefixLength));
    }

    public static int menu(Object... options) {
        int L = options.length;
        for (int i = 0; i < L; i++) {
            System.out.println((i + 1) + "-" + options[i].toString());
        }
        System.out.println("Choose (1.." + L + "): ");
        return Integer.parseInt(sc.nextLine());
    }

    public static int menu(List options) {
        int L = options.size();
        for (int i = 0; i < L; i++) {
            System.out.println((i + 1) + "-" + options.get(i).toString());
        }
        System.out.print("Choose (1.." + L + "): ");
        return Integer.parseInt(sc.nextLine());
    }

    public static Object chooseOne(List options) {
        int pos = menu(options);
        if (pos < 0 || pos >= options.size()) {
            return null;
        }
        return options.get(pos - 1);
    }

    public static Object chooseOne(Object... options) {
        int pos = menu(options);
        if (pos < 0 || pos >= options.length) {
            return null;
        }
        return options[pos - 1];
    }

    public static String checkString(String str) {
        String value;
        while (true) {
            try {
                System.out.print(str);
                value = sc.nextLine();
                if (value.trim().isEmpty()) {
                    throw new Exception("Please input value!!!");
                }
                return value;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double checkDouble(String str) {
        double value;
        while (true) {
            try {
                System.out.print(str);
                value = Double.parseDouble(sc.nextLine());
                if (value <= 0) {
                    throw new Exception("Please input greater than 0!!!");
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a float value!!!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int checkYear(String str) {
        int modelYear;
        Date dateNow = new Date();
        while (true) {
            try {
                System.out.print(str);
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again!!!");
                    continue;
                }

                modelYear = Integer.parseInt(input);
                Date checkYear = new Date(modelYear-1900, 1, 1);
                if (checkYear.before(dateNow)) {
                    
                    break;
                }else{
                    System.out.println("Please try again!!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your number format is incorrect. Please enter a valid number!!!");
            }
        }
        return modelYear;
    }

    public static double checkPrice(String str) {
        double listPrice;
        while (true) {
            try {
                System.out.print(str);
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again!!!");
                    continue;
                }

                listPrice = Double.parseDouble(input);
                if (listPrice >= 0) {
                    break;
                } else {
                    System.out.println("List price must be greater than 0!!!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Your number format is incorrect. Please enter a valid number!!!");
            }
        }
        return listPrice;
    }

    public static boolean checkFileExists(String fileName) {
        File f = new File(fileName);

        try {
            if (f.exists()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String checkDelete(String str) {
        String value;
        while (true) {
            try {
                System.out.print(str);
                value = sc.nextLine();
                if (value.trim().isEmpty()) {
                    throw new Exception("Please input value!");
                }
                if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N")) {
                    return value;
                } else {
                    throw new Exception("Please input Y = Yes Or N = No!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean checkSpecialCharacter(String name) {
        if (name.matches("^[a-zA-Z\\s]+$")) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatName(String str) {

        String input;
        while (true) {
            try {
                System.out.print(str);
                input = sc.nextLine();
                if (checkSpecialCharacter(input) != false) {
                    input = input.trim();
                    input = input.toLowerCase();
                    String[] words = input.split("\\s+");
                    StringBuilder formattedName = new StringBuilder();
                    for (String word : words) {
                        if (!word.isEmpty()) {
                            formattedName.append(Character.toUpperCase(word.charAt(0)));
                            formattedName.append(word.substring(1));
                            formattedName.append(" ");
                        }
                    }
                    return formattedName.toString().trim();
                } else {
                    System.out.println("Please re-enter your name");
                }

            } catch (Exception e) {
                System.out.println("Please re-enter your name");
            }
        }
    }
    
    public static String checkTrueFalse(String str) {
        String value;
        while (true) {
            try {
                System.out.print(str);
                value = sc.nextLine();
                if (value.trim().isEmpty()) {
                    throw new Exception("Please input value!");
                }
                if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N")) {
                    return value;
                } else {
                    throw new Exception("Please input Y = Yes Or N = No!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
