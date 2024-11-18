import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileHandaling {


    static String directory =  "C:\\Users\\MelissaAsus\\Desktop\\faculta\\anl2,sem1\\BankManagementSystem\\" ;

    //creating the file if it doesn't exist and returns 1 or return 0 if the file doesn't exist and catching an error if appears and returns -1
    public int creatingFile(String fileName) {

        try {
            File myObj = new File(directory + fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + fileName);
                return 1;
            } else {
                return 0;
            }
        } catch (IOException e) {

            e.printStackTrace();
            return -1;
        }

    }

    //method WritingInAnExistingFile is writing a string in an existing file and catch
    public void WritingInAnExistingFile(String fileName, String content) {


        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(content);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

    }

    //method appendingInAnExistingFile is appending a string in a file that exist and that already has content iin it
    public void appendingInAnExistingFile(String fileName, String content) {
        try {
            Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.out.println("An error occurred at currency choosing.");
        }
    }


//    fun (User ){
//
//
//
//        f>>user.name>>\n>>currecy>>
//
//    }

    public static void writingAnObjectINAnFIle(User user, String fileName){


        try (PrintWriter out = new PrintWriter(directory + fileName)) {
            out.println(user.getPassword() + "\n" + user.getBalance() + "\n" + user.getCurrency());

//            Files.write(Paths.get(fileName), user.getBalance().getBytes(), StandardOpenOption.APPEND);
//            Files.write(Paths.get(fileName), user.getCurrency().getBytes(), StandardOpenOption.APPEND);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }




    public User readingFromAnExistingFile(String fileName) {

        FileReader fr = null;
        try {
            fr = new FileReader(directory + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred at the reading file." + directory + fileName);
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(fr);

        try {
            if (br.ready()) {

                //returns the first line from the file(the password)
                String password = br.readLine();
                String balance = br.readLine();
                String currency = br.readLine();


                User userr = new User(fileName,password,balance,currency);
                return userr;

            }
        } catch (IOException e) {
            System.out.println("An error occurred at the reading file");
            return null;
        }

        return null;

    }


}
