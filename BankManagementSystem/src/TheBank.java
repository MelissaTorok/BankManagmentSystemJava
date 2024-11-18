import java.util.Scanner;

public class TheBank implements MoneyMagment{

    static Scanner myObjForScanner = new Scanner(System.in);
    public FileHandaling fileHandaling = new FileHandaling();


    public double getingTheBalanceIntoDoubleValue(String entireName){

        //getting the balance from the user file
        String money = fileHandaling.readingFromAnExistingFile(entireName).getBalance();
        System.out.println("Your balance was: "+ money);

        //transforming the information from the file from a string in double
        Double moneyDouble = Double.valueOf(money);

        return moneyDouble;

    }

    public void deposit(String nameFile){

        System.out.println("How much money do you want to deposit?");
        Double addingMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameFile);


        //subtract the withdrawal money from the account
        double plus =  moneyDouble + addingMoney;
        System.out.println("Now your balance is: "+ plus);

        User user1 = new User(fileHandaling.readingFromAnExistingFile(nameFile).getEntireName(),fileHandaling.readingFromAnExistingFile(nameFile).getPassword(),Double.toString(plus), fileHandaling.readingFromAnExistingFile(nameFile).getCurrency());
        FileHandaling.writingAnObjectINAnFIle(user1, nameFile);



    }

    public void withdraw(String nameFile ){

        System.out.println("How much money do you want to withdraw?");
        Double withdrawMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameFile);


        if (withdrawMoney > moneyDouble) {
            System.out.println("Your balance is: " + moneyDouble + " you don't have enough money");
        } else {
            //subtract the withdrawal money from the account
            double minus = moneyDouble - withdrawMoney;
            System.out.println("Now your balance is: " + minus);

            User user1 = new User(fileHandaling.readingFromAnExistingFile(nameFile).getEntireName(), fileHandaling.readingFromAnExistingFile(nameFile).getPassword(), Double.toString(minus), fileHandaling.readingFromAnExistingFile(nameFile).getCurrency());
            FileHandaling.writingAnObjectINAnFIle(user1, nameFile);

        }
    }



}
