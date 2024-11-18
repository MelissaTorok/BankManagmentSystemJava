import java.util.Scanner;

public class ATM implements MoneyMagment{

    static Scanner myObjForScanner = new Scanner(System.in);
    public FileHandaling fileHandaling = new FileHandaling();


    public double getingTheBalanceIntoDoubleValue(String entireName){

        String money = fileHandaling.readingFromAnExistingFile(entireName).getBalance();
        System.out.println("Your balance is: "+ money);

        //transforming the information from the file from a string in double
        Double moneyDouble = Double.valueOf(money);

        return moneyDouble;

    }

    public void deposit(String nameFile){

        System.out.println("How much money do you want to deposit?");
        Double addingMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameFile);


        if(addingMoney < 100){


            //subtract the withdrawal money from the account
            double minus =  moneyDouble + addingMoney;
            System.out.println("Now your balance is: "+ minus);

            User user1 = new User(fileHandaling.readingFromAnExistingFile(nameFile).getEntireName(),fileHandaling.readingFromAnExistingFile(nameFile).getPassword(),Double.toString(minus), fileHandaling.readingFromAnExistingFile(nameFile).getCurrency());
            FileHandaling.writingAnObjectINAnFIle(user1, nameFile);


        }else{

            System.out.println("You can't add more than 100");
        }
    }

    public void withdraw(String nameFile ){

        System.out.println("How much money do you want to withdraw?");
        Double withdrawMoney = myObjForScanner.nextDouble();

        //transforming the information from the file from a string in double
        Double moneyDouble = getingTheBalanceIntoDoubleValue(nameFile);

        if(withdrawMoney <= 50) {

            if (withdrawMoney > moneyDouble) {
                System.out.println("Your balance is: " + moneyDouble + " you don't have enough money");
            } else {
                //subtract the withdrawal money from the account
                double minus = moneyDouble - withdrawMoney;
                System.out.println("Now your balance is: " + minus);

                User user1 = new User(fileHandaling.readingFromAnExistingFile(nameFile).getEntireName(), fileHandaling.readingFromAnExistingFile(nameFile).getPassword(), Double.toString(minus), fileHandaling.readingFromAnExistingFile(nameFile).getCurrency());
                FileHandaling.writingAnObjectINAnFIle(user1, nameFile);

            }
        }else{
            System.out.println("The maximum amount to withdraw is 50");
        }

    }

}
