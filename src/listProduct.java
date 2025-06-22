
import MyTools.myTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TruongNX
 */
public class listProduct extends ArrayList<Product> {

    private static Scanner sc = new Scanner(System.in);
    ArrayList<String> listBrandsID = new ArrayList<>();
    ArrayList<String> listCategoryId = new ArrayList<>();

    public void displayAll() {
        for (Product r : this) {
            System.out.println(r);
        }
    }

    public void addProduct(String filename) throws IOException, ClassNotFoundException {
        String id, name, brandId, categoryId;
        int modelYear;
        double listPrice;
        do {
            id = myTools.checkString("Enter product id: ");
        } while (checkIdExist(id));

        name = myTools.formatName("Enter product name: ");

        do {
            System.out.println("Enter  id in form Bxxx where xxx is number");
            brandId = myTools.checkString("Enter product brand id: ");
            if(isExistBrandId(brandId)){
                System.out.println("Invalid Option!");
            }
        } while (isExistBrandId(brandId));

        do {
            System.out.println("Enter  id in form Cxxx where xxx is number");
            categoryId = myTools.checkString("Enter product category id: ");
            if(isExistCategoryId(categoryId)){
                System.out.println("Invalid Option!");
            }
        } while (isExistCategoryId(categoryId));
        modelYear = myTools.checkYear("Enter product model year: ");
        listPrice = myTools.checkPrice("Enter product price: ");
        Product pro = new Product(id, name, brandId, categoryId, modelYear, listPrice);
        add(pro);
        saveFile(filename);
        System.out.println("Add product successful!!!");
    }

    public listProduct searchName(String findName) {
        listProduct result = new listProduct();
        for (Product pro : this) {
            if (pro.getName().toLowerCase().contains(findName.toLowerCase())) {
                result.add(pro);
            }
        }
        return result;
    }

    public void sortByYear(listProduct list) {
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(p1.getModelYear(), p2.getModelYear());
            }
        });
    }

    public listProduct searchProduct(String findName) {
        listProduct result = new listProduct();
        result = this.searchName(findName);
        if (result.isEmpty()) {
            System.out.println("Have no any Product");
        } else {
            sortByYear(result);
            for (Product p : result) {
                System.out.println(p.toString());
            }
        }
        return result;
    }

    public void updateProduct(String findId, String filename) {

        if (searchId(findId) == null) {
            System.out.println("Product does not exist");
        } else {
            Product pro = searchId(findId);
            System.out.println("Press 'Enter' to re-enter the old value.");
            System.out.print("Enter newName: ");
            String name = sc.nextLine();
            if (!name.trim().isEmpty()) {
                pro.setName(name);
            }
            System.out.println("Press 'Enter' to re-enter the old value.");
            System.out.print("Enter new brandID: ");
            String brandId = sc.nextLine();
            if (!brandId.isEmpty()) {
                pro.setBrandId(brandId);
            }
            System.out.println("Press 'Enter' to re-enter the old value.");
            System.out.print("Enter new categoryID: ");
            String categoryId = sc.nextLine();
            if (!categoryId.isEmpty()) {
                pro.setCategoryId(categoryId);
            }
            int mYear;
            System.out.println("Press 'Enter' to re-enter the old value.");
            mYear = myTools.checkYear("Enter new Model Year: ");
            pro.setModelYear(mYear);
            System.out.print("Enter new List Price: ");
            String lPrice = sc.nextLine();
            if (!lPrice.isEmpty()) {
                double listPrice = Double.parseDouble(lPrice);
                pro.setListPrice(listPrice);
            }
            saveFile(filename);
            System.out.println("Update success");
        }
    }

    public Product searchId(String findId) {

        for (Product pro : this) {
            if (pro.getId().trim().equals(findId)) {
                return pro;
            }
        }
        return null;
    }

    public void deleteProduct(String findId) {

        if (searchId(findId) == null) {
            System.out.println("Product does not exist");
        } else {
            System.out.println(searchId(findId).toString());
            System.out.println("Do you want delete product?");
            String input = myTools.checkDelete("Enter Y = Yes or N = No: ");
            if (input.trim().equalsIgnoreCase("Y")) {
                this.remove(searchId(findId));
                System.out.println("Delete success");
            } else {
                System.out.println("Delete failure");
            }
        }
    }

    public boolean checkIdExist(String id) {
        if (searchId(id) != null) {
            System.out.println("ID already exists");
            return true;
        } else {
            return false;
        }
    }

    public void saveFile(String fileName) {
        if (this.isEmpty()) {
            System.out.println("Empty product list");
            return;
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fileName);
            for (Product p : this) {
                pw.print(p.getId() + "," + p.getName() + "," + p.getBrandId() + "," + p.getCategoryId() + "," + p.getModelYear() + "," + p.getListPrice() + "\n");
                pw.flush();
            }
            System.out.println("Save success");
        } catch (Exception e) {
            System.out.println("Error writing file!!!");
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e) {
                System.out.println("Error closing file!!!");
            }
        }
    }

    public void printFromFile(String fileName) throws IOException {
        readData(fileName);
        Collections.sort(this, new Comparator<Product>() {
            @Override
            public int compare(Product x1, Product x2) {
                if (x1.getListPrice() != x2.getListPrice()) {
                    return -(Double.compare(x1.getListPrice(), x2.getListPrice()));
                }
                return x1.getName().compareTo(x2.getName());
            }
        });
        System.out.printf("%-20s %-20s %-20s %-20s %-20s       %-20s\n",
                "ID", "Name", "Brand ID", "Category ID", "Model Year", "List Price");
        for (Product pro : this) {
            System.out.printf("%-20s %-20s %-20s %-20s %-20d       %-10.2f\n", pro.getId(), pro.getName(), pro.getBrandId(), pro.getCategoryId(), pro.getModelYear(), pro.getListPrice());
        }
    }

    public void readData(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("File don't have data!!!\nPlease add more Product.");
        }
        FileReader f = new FileReader(file);
        BufferedReader bf = new BufferedReader(f);
        try {
            String s ;
            while (true) {
                s = bf.readLine();
                if (s == null) {
                    break;
                } else {
                    String[] arr = s.split(",");             
                        String id = arr[0].trim();
                        String name = arr[1].trim();
                        String brand = arr[2].trim();
                        String category = arr[3].trim();
                        int modelYear = Integer.parseInt(arr[4].trim());
                        double listPrice = Double.parseDouble(arr[5].trim());
                        Product pro = new Product(id, name, brand, category, modelYear, listPrice);
                        this.add(pro);
                        
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
                if (bf != null) {
                    bf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void readFileBrandID() throws IOException {
        String brandId = "01_Brand.txt";
        File file = new File(brandId);
        if (!file.exists()) {
            System.out.println("File don't exist");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(",");
                    String id = data[0];
                    String name = data[1];
                    String country = data[2];
                    listBrandsID.add(id);
                }
            }
        } catch (IOException c) {
            System.err.println("Error when write file: " + c.getMessage());
        }
    }

    public boolean isExistBrandId(String brandId) throws IOException, ClassNotFoundException {
        readFileBrandID();
        for (String brand : listBrandsID) {
            if (brand.equalsIgnoreCase(brandId)) {

                return false;
            }
        }
        return true;
    }

    public void readFileCatagoryId() throws IOException {
        String CategoryId = "01_Category.txt";
        File file = new File(CategoryId);
        if (!file.exists()) {
            System.out.println("File don't exits");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String name = data[1];
                listCategoryId.add(id);
            }
        } catch (IOException c) {
            System.err.println("Error when write file: " + c.getMessage());
        }
    }

    public boolean isExistCategoryId(String categoryId) throws IOException, ClassNotFoundException {
        readFileCatagoryId();
        for (String brand : listCategoryId) {
            if (brand.equalsIgnoreCase(categoryId)) {
                return false;
            }
        }
        return true;
    }

}
