package com.example.cn004assignmentgui;

public class Callerinformation {
    String name,lastname, description,address,phonenumber;




    public  Callerinformation(String Name, String LastName, String Address,String PhoneNumber,String Description){
        name = Name;
        lastname = LastName;
        address = Address;
        phonenumber = PhoneNumber;
        description = Description;

    }

    public String getName() {
        return name;
    }

    public String getLastname() {

        return lastname;
    }

    public String getDescription() {

        return description;
    }

    public String getAddress() {

        return address;
    }

    public String getPhonumber() {

        return phonenumber;
    }
    /*public static Callerinformation getCallerInformation() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for their name
        System.out.println("Please enter your name:");

        String name;
        while (true) {
            name = scanner.nextLine();
            // Input validation for name (accepts only letters)
            if (name.matches("^[a-zA-Z]+$")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter only letters for your name:");
            }
        }



        // Prompt the user for their last name
        System.out.println("Please enter your last name:");

        String lastName;
        while (true) {
            lastName = scanner.nextLine();
            // Input validation for last name (accepts only letters)
            if (lastName.matches("^[a-zA-Z]+$")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter only letters for your last name:");
            }
        }


        // Prompt the user for their phone number
        System.out.println("Please enter your phone number (without spaces or special characters):");

        String phoneNumber;
        while (true) {
            phoneNumber = scanner.nextLine();
            // Check if the input is a number and its length is 10
            if (phoneNumber.matches("[0-9 ]+") && phoneNumber.length() == 10) {
                // Add the international dialing code prefix "+44" to the phone number
                phoneNumber = "+44" + phoneNumber;
                break;
            } else {
                System.out.println("Invalid input. Please enter a 10-digit phone number without spaces or special characters:");
            }
        }

        // Prompt the user for their address
        System.out.println("Please enter your address:");
        String address;
        while (true) {
            // Input validation for address (accepts only letters, numbers, and spaces)
            address = scanner.nextLine();
            if (address.matches("[a-zA-Z0-9 ]+")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter only letters, numbers, and spaces for your address:");
            }
        }

        // Prompt the user for a description of their issue
        System.out.println("Please provide a description of your issue:");
        String description;
        while (true) {
            // Input validation for description (accepts letters, numbers, and specified symbols)
            description = scanner.nextLine();
            if (description.matches("[a-zA-Z0-9 ,.:;!?']+")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter only letters, numbers, and the following symbols: , . : ; ! ?");
            }
        }




        // Return CallerInformation object with the collected information
        return new Callerinformation(name, lastName, address,phoneNumber , description);

    }*/

    public static Callerinformation CreateCaller(String Name, String LastName, String Address, String PhoneNumber, String Description){
        return new Callerinformation(Name,LastName,Address,PhoneNumber,Description);
    }

}
