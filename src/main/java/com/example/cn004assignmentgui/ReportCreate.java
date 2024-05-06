package com.example.cn004assignmentgui;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ReportCreate {
    public static void CreateFolder() {
        File folder = new File("report");
        if (!folder.exists()) {
            folder.mkdir();
            System.out.println("Folder Created");
        } else {
            System.out.println("The folder already exists");
        }
    }

    public static void generateNextReport(String reportDescription) {

        File folder = new File("report");
        File[] files = folder.listFiles();

        int nextReportNumber = 1;
        if (files != null) {
            nextReportNumber += files.length;
        }

        String reportName = "report" + nextReportNumber + ".txt";
        File newReport = new File(folder, reportName);

        try {
            if (newReport.createNewFile()) {
                System.out.println("Report Created: " + reportName);
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);

                FileWriter myWriter = new FileWriter(newReport);
                myWriter.write("Report create at the following date and time: " + formattedDate + "\n");

                myWriter.write(reportDescription);
                myWriter.close();
                System.out.println("File created successfully");
            } else {
                System.out.println("The report " + reportName + " already exist");
            }
        } catch (IOException e) {
            System.out.println("Error during report creation " + e.getMessage());
        }
    }

    public static boolean ReportReader() {
        String directoryPath = "report";
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        File directory = new File(directoryPath);

        if (directory.isDirectory() && directory.exists()) {

            File[] files = directory.listFiles();

            System.out.println("Contents of the folder: " + directoryPath);
            for (File file : files) {
                System.out.println(file.getName());
            }

            boolean validInput = false;
            String fileName = null;

            while (!validInput) {
                System.out.println("Choose the name of the file you want to read (type only the name of the report without the .txt, like report1):");
                fileName = scanner.nextLine().toLowerCase();


                // Regular expression to match only report name without additional characters
                if (fileName.matches("^report\\d+$")) {

                    validInput = true;
                    fileName += ".txt";
                } else {
                    System.out.println("Invalid input format. Please enter the report name without additional characters.");
                }
            }

            File chosenFile = new File(directory, fileName);

            if (chosenFile.isFile() && chosenFile.exists()) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(chosenFile))) {

                    System.out.println("Content of the file " + fileName + ":");

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    success = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("The specified file does not exist in the folder.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }

        return success;
    }

    /*public static boolean ResolveCases() {
        String directoryPath = "report";
        File directory = new File(directoryPath);
        boolean success = true;

        if (directory.isDirectory() && directory.exists()) {

            File[] files = directory.listFiles();

            System.out.println("Reports that need resolution: ");
            boolean unresolvedFound = false;

            for (File file : files) {
                if (!IsEmergencyResolved(file)) {
                    unresolvedFound = true;
                    System.out.println(file.getName());
                }
            }

            if (!unresolvedFound) {
                System.out.println("All reports have been resolved.");
                success = false;
            } else {
                Scanner scanner = new Scanner(System.in);
                boolean validResponse = false;
                String fileName = null;

                while (!validResponse) {
                    System.out.println("Choose the name of the file you want to resolve (type only the name of the report without the .txt, like report1):");
                    fileName = scanner.nextLine().toLowerCase();
                    validResponse = fileName.matches("^report\\d+$");
                    if (!validResponse) {
                        System.out.println("Invalid input format. Please enter the report name without additional characters.");
                    }
                }

                File chosenFile = new File(directory, fileName + ".txt");

                if (chosenFile.isFile() && chosenFile.exists()) {
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(chosenFile))) {
                        boolean isResolved = IsEmergencyResolved(chosenFile);
                        if (isResolved) {
                            System.out.println("This case has already been resolved.");
                            success = false;
                        } else {
                            validResponse = false;

                            while (!validResponse) {
                                System.out.println("Do you want to resolve this case? (yes or no only)");
                                String response = scanner.nextLine().toLowerCase();
                                validResponse = response.equals("yes") || response.equals("no");
                                if (!validResponse) {
                                    System.out.println("Invalid input. Please enter 'yes' or 'no' only.");
                                } else if (response.equals("yes")) {
                                    try (FileWriter fileWriter = new FileWriter(chosenFile, true)) {
                                        fileWriter.write("\nEmergency Resolved: yes");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        success = false;
                                    }
                                } else {
                                    success = false;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        success = false;
                    }
                } else {
                    System.out.println("The specified file does not exist in the folder.");
                    success = false;
                }
            }
        } else {
            System.out.println("The specified path is not a directory.");
            success = false;
        }

        return success;
    }

    public static boolean IsEmergencyResolved(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("Emergency Resolved: yes")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean ReportReaderMain() {
        boolean success = true;

        System.out.println("Do you want to read a report or resolve an emergency? (Type 'read' or 'resolve'):");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("read")) {
            success = ReportReader();
            if (!success) {
                System.out.println("Error reading the report. Please try again.");
            }
        } else if (choice.equals("resolve")) {
            success = ResolveCases();
            if (!success) {
                System.out.println("Error resolving the cases. Please try again.");
            }
        } else {
            System.out.println("Invalid input. Please type 'read' or 'resolve'.");
            success = false;
        }

        return success;
    }*/
}








