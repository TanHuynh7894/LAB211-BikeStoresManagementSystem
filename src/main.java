
import java.io.IOException;
import java.util.Scanner;
import MyTools.myTools;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TruongNX
 */
public class main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String fileName = "Product.txt";
        listProduct listOfProduct = new listProduct();
        int choice = 0;
        boolean ValidChoice;
        String cont;
        listOfProduct.readData(fileName);
        do {
            ValidChoice = false;
            do {
                try {
                    choice = menu(" Create a Product.", "Search Product information by name.", "Update Product information.", " Delete Product information.", " Save to file.", "Print all lists from file", "Quit Menu.");
                    if (choice < 1 && choice > 6) {
                        System.out.println("PLEASE CHOOSE THE RIGHT NUMBER!");
                    } else {
                        ValidChoice = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("INVALID CHOICE! PLEASE CHOOSE AGAIN!");
                }
            } while (!ValidChoice);

            switch (choice) {
                case 1:
                    do {
                        listOfProduct.addProduct(fileName);
                        cont = myTools.checkTrueFalse("Would you like to return to the main menu?(y/n): ");
                    } while (cont.equalsIgnoreCase("N"));

                    break;
                case 2:
                    do {
                        String findName = myTools.checkString("Enter the product name: ");
                        listOfProduct.searchProduct(findName);
                        cont = myTools.checkTrueFalse("Would you like to return to the main menu?(y/n): ");
                    } while (cont.equalsIgnoreCase("n"));
                    break;
                case 3:
                    do {
                        listOfProduct.displayAll();
                        String findId = myTools.checkString("Enter the product id: ");
                        listOfProduct.updateProduct(findId, fileName);
                        cont = myTools.checkTrueFalse("Would you like to return to the main menu?(y/n): ");
                    } while (cont.equalsIgnoreCase("n"));

                    break;
                case 4:

                    do {
                        listOfProduct.displayAll();
                        String findID = myTools.checkString("Enter the find ID: ");
                        listOfProduct.deleteProduct(findID);
                        cont = myTools.checkTrueFalse("Would you like to return to the main menu?(y/n): ");
                    } while (cont.equalsIgnoreCase("n"));
                    break;
                case 5:

                    do {
                        listOfProduct.saveFile(fileName);
                        cont = myTools.checkTrueFalse("Would you like to return to the main menu?(y/n): ");
                    } while (cont.equalsIgnoreCase("n"));
                    break;
                case 6:

                    do {
                        listOfProduct.displayAll();
                        cont = myTools.checkTrueFalse("Would you like to return to the main menu?(y/n): ");
                    } while (cont.equalsIgnoreCase("n"));
                    break;
                default:
                    System.out.println("GOODBYE");
                    break;
            }

        } while (choice >= 1 && choice <= 6);

    }

    public static int menu(Object... options) {
        int L = options.length;
        for (int i = 0; i < L; i++) {
            System.out.println((i + 1) + "-" + options[i].toString());
        }
        System.out.println("Choose (1.." + L + "): ");
        return Integer.parseInt(sc.nextLine());
    }
    public static final Scanner sc = new Scanner(System.in);
}
