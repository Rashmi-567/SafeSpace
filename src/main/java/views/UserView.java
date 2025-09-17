package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public record UserView(String email) {

    public void home() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.id() + " - " + file.fileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path:");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);

                        if (files.isEmpty()) {
                            System.out.println("No files to unhide.");
                            continue;
                        }

                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.id() + " - " + file.fileName());
                        }

                        System.out.println("Enter the ID of file to unhide:");
                        int id = Integer.parseInt(sc.nextLine());

                        boolean isValidID = files.stream().anyMatch(file -> file.id() == id);

                        if (isValidID) {
                            DataDAO.unhide(id);
                            System.out.println("File unhidden successfully.");
                        } else {
                            System.out.println("Wrong ID.");
                        }
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
