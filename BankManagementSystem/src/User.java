import java.io.File;
import java.lang.Object;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class User implements LogInSingIn {


    //objects of classes
    public Options options = new Options();          // Member of type A
    public FileHandaling fileHandaling = new FileHandaling();
    static Scanner myObjForScanner = new Scanner(System.in);
    public ATM atm = new ATM();
    public TheBank theBank = new TheBank();



    //variables
    private String balance;
    private String entireName;
    private String password;
    private String  currency;

    //constructor
    public User(String entireName, String password,String balance,String currency) {

        this.entireName = entireName;
        this.password = password;
        this.currency = currency;
        this.balance = balance;

    }

    //getters
    public String getEntireName() {return entireName;}

    public String getPassword() {return password;}

    public String getBalance() {return balance;}

    public String getCurrency() {return currency;}

    @Override
    public void logINorSingUp(){


        switch(options.singInOrSingUp()){
            case 1:
               SingUp();
                break;
            case 2:
               logIn("", "");
                break;
            case 3:
                exit(0);
                break;
            default:
                System.out.println("You didn't entered a right option");
                break;

        }
    }



    static public void SingUp() {

        FileHandaling fileHandaling = new FileHandaling();

        String entireName;

        //entireName it will be the username
        entireName = creatingUserName();


        //function that creates the file and verify if the account already exist, if not is returning 1
        int verify = fileHandaling.creatingFile(entireName);


        switch (verify) {
            case 1:
                System.out.println("Create a password for the account");

                creatingTheAccount(entireName);

                System.out.println("Your account was created successfully.");
                break;
            case 0:
                System.out.println("You already have an account");

            case -1:
                System.out.println("An error occurred while creating the account.");
                break;
        }

    }

    public static void  creatingTheAccount(String entireName) {
        FileHandaling fileHandaling = new FileHandaling();

        String password;

        password = myObjForScanner.nextLine();
        fileHandaling.WritingInAnExistingFile(entireName,password);
        //appending the balance to the user file that already has the password in it;
        fileHandaling.appendingInAnExistingFile(entireName, "0");

        //going on a new line in the file
        fileHandaling.appendingInAnExistingFile(entireName, "\n");

        //appending the currency to the user file that already has the password in it
        String currency =  Options.currencyOption();
        fileHandaling.appendingInAnExistingFile(entireName, currency);


        User user1 = new User(entireName,password,"0", currency);
        FileHandaling.writingAnObjectINAnFIle(user1, entireName);

    }


    public void logIn(  String username, String password ) {

        if(Objects.equals(username, ""))
            entireName = creatingUserName();
        else
            entireName = username;

        //if the account exist the function will return 0 if not we will create an account and the function will return 1
        int verify = fileHandaling.creatingFile(entireName);

        switch (verify) {
            case 1:
                System.out.println("You don't have an account, please create an account.");


                System.out.println("Please enter your currency for your account");

                //appending the currency to the user file that already has the password in it
                fileHandaling.appendingInAnExistingFile(entireName, Options.currencyOption());

                System.out.println("Your account was created successfully.");
                break;
            case 0:
                System.out.println("You have an account");

                //checkingPassword() returns 1 if password id correct and 0 if not
                if(checkingPassword(password) == 1) {

                    options.editOptions();

                    switch (myObjForScanner.nextInt()) {
                        case 1:
                            manageYourMoney(entireName);
                            break;
                        case 2:
                            viewDetailsAccount(entireName);
                            break;
                        case 3:
                            transferMoney(entireName);
                            break;
                        case 4:
                            DeleteAccount(entireName);
                            break;
                        default:
                            System.out.println("You didn't entered a right option");
                            break;
                     }
                }
            break;

        case -1:
            System.out.println("An error occurred while login in the account.");

        }

    }

    public void manageYourMoney(String entireName) {

        System.out.println("Press 1 for adding the money");
        System.out.println("Press 2 for withdrawing the money");
        System.out.println("Press 3 for exit");

        int verify = myObjForScanner.nextInt();

        switch (verify) {
            case 1:
                System.out.println("Press 1 for ATM");
                System.out.println("Press 2 for adding the money from the bank");
                System.out.println("Press 3 for exit");

                int atmOrBank = myObjForScanner.nextInt();

                switch (atmOrBank) {
                    case 1:
                        atm.deposit(entireName);
                        break;
                    case 2:
                        theBank.deposit(entireName);

                        break;
                    case 3:
                        exit(0);
                        break;

                    default:
                        System.out.println("You entered the wrong option.");
                        break;

                }

                break;
            case 2:
                System.out.println("Press 1 for ATM");
                System.out.println("Press 2 for withdrawing the money from the bank");
                System.out.println("Press 3 for exit");

                int atmOrBank2 = myObjForScanner.nextInt();

                switch (atmOrBank2) {
                    case 1:
                        atm.withdraw(entireName);
                        break;
                    case 2:
                        theBank.withdraw(entireName);
                        break;
                    case 3:
                        exit(0);
                        break;

                    default:
                        System.out.println("You entered the wrong option.");
                        break;
                }
                break;


            case 3:
                exit(0);
                break;

            default:
                System.out.println("You entered the wrong option.");
                break;
        }

    }



    public double getingTheBalanceInDoubleValue(String entireName){

        String money = fileHandaling.readingFromAnExistingFile(entireName).getBalance();
        System.out.println("Your balance is: "+ money);

        //transforming the information from the file from a string in double
        Double moneyDouble = Double.valueOf(money);

        return moneyDouble;

    }


    public void transferMoney(String entireName) {

        //getting the money from the entireName account
        Double moneyDouble = getingTheBalanceInDoubleValue(entireName);

        System.out.println("How much money do you want to send?");
        Double withdrawMoney = myObjForScanner.nextDouble();


        if(withdrawMoney > moneyDouble) {

            System.out.println("Your balance is: "+ moneyDouble +" you don't have enough money");
        }
        else
        {
            System.out.println("What is the username of the person?");
            myObjForScanner.nextLine();
            String recevivUsername = myObjForScanner.nextLine();


            int verify = fileHandaling.creatingFile(recevivUsername);

            switch (verify) {
                case 1:
                    System.out.println("The account doesn't exist");
                    break;
                case 0:
                    System.out.println("The account is active:");

                    String money = fileHandaling.readingFromAnExistingFile(recevivUsername).getBalance();

                    //transforming the information from the file from a string in double
                    Double reciverBalance = Double.valueOf(money);

                    reciverBalance = reciverBalance + withdrawMoney;

                    User userr = new User(recevivUsername,password,Double.toString(reciverBalance), currency);
                    FileHandaling.writingAnObjectINAnFIle(userr, recevivUsername);

                    //subtract the withdrawal money from the account
                    double minus =  moneyDouble - withdrawMoney;
                    System.out.println("Now your balance is: "+ minus);

                    User user1 = new User(entireName,password,Double.toString(minus), currency);
                    FileHandaling.writingAnObjectINAnFIle(user1, entireName);


                    break;
                case -1:
                    System.out.println("An error occurred while transferring the money.");
                    break;
            }
        }
    }


    public void viewDetailsAccount(String entireName){

        System.out.println(fileHandaling.readingFromAnExistingFile(entireName).getEntireName());
        System.out.println("Password: "+fileHandaling.readingFromAnExistingFile(entireName).getPassword());
        System.out.println("Your account balanced: "+fileHandaling.readingFromAnExistingFile(entireName).getBalance());
        System.out.println("Your currency: " +fileHandaling.readingFromAnExistingFile(entireName).getCurrency());

    }

    public void DeleteAccount(String entireName){

        File myObj = new File(entireName);
        try{
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + entireName);
            } else {
                System.out.println("Failed to delete the file.");
            }
        }catch (Exception e) {
            System.out.println("An error occurred while deleting the file.");
        }
//        try {
//            if (!myObj.exists()) {
//                System.out.println("File does not exist: " + myObj.getAbsolutePath());
//                return;
//            }
//            if (myObj.isDirectory()) {
//                System.out.println("Specified path is a directory, not a file: " + myObj.getAbsolutePath());
//                return;
//            }
//            if (myObj.delete()) {
//                System.out.println("Deleted the file: " + entireName);
//            } else {
//                System.out.println("Failed to delete the file. Check if the file is in use or permissions are sufficient.");
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred while deleting the file: " + e.getMessage());
//            e.printStackTrace();
//        }

    }

    public int checkingPassword(String pass){

        String password = pass;
        if (Objects.equals(password, "")) {
            System.out.println("Enter your password");
            password = myObjForScanner.nextLine();
        }

        //comparing the password from the user input and the password from the account(from the file with the name entireName)
        //fileHandaling.readingFromAnExistingFile(entireName) returns an object of type User with the data read from the file
        //fileHandaling.readingFromAnExistingFile(entireName).getPassword()) returns the password from the object

        try {
            if (checkIfTwoStringsAreEqual(password, fileHandaling.readingFromAnExistingFile(entireName).getPassword())) {
                System.out.println("Your password is correct");
                return 1;
            } else {
                System.out.println("password does not match");
                checkingPassword(password);
                return 1;
            }
        }catch (Exception e) {
            System.out.println("An error occurred while checking the password.");
            return 0;
        }


    }

    public static boolean checkIfStringHasOnlyLetters(String entireName){

        if (entireName == null || entireName == "") {
            return false;
        }

        int n = entireName.length();

        for (int i = 0; i < n; i++) {
            // Check if the specified character is not a letter then return false,else return true
            if (!Character.isLetter(entireName.charAt(i))) {
                return false;
            }
        }
        return true;


    }

    public static String creatingUserName(){

        //saving the first and last name from the users in variables
        System.out.println("Please enter your first name");
        String firstName = myObjForScanner.nextLine();
        System.out.println("Please enter your last name");
        String LastaName =myObjForScanner.nextLine();


        //concatenate the first name with the last name;
        String entireName = firstName;
        entireName = firstName.concat(LastaName);

        entireName = entireName.toLowerCase();

        try {
            System.out.println(entireName.charAt(0));
        } catch(NullPointerException e) {
            System.out.println("NullPointerException..");
        }

        if(checkIfStringHasOnlyLetters(entireName)){
            return entireName;
        }else{
            System.out.println("You need to enter only letters");
            return creatingUserName();
        }


    }

    public boolean checkIfTwoStringsAreEqual(String firstString, String secondString) {

        //checking if two strings are equal
        if(firstString.equals(secondString))
            return true;
        else
            return false;

    }

}
