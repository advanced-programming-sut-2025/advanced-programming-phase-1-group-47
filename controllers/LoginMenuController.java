package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Gender;
import models.enums.Menu;
import models.enums.SecurityQuestion;
import models.enums.commands.LoginMenuCommands;

import java.util.Scanner;
import java.security.SecureRandom;
import java.util.regex.Matcher;

import static models.enums.Gender.getGenderEnum;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginMenuController {

    public Result<String> SignUp(String username, String password, String passwordConfirm, String nickname, String email, String gender, Scanner scanner) {

        if(App.getUserByUsername(username) != null) {

            System.out.println("Username already exists!");

            for(int i = 0; i < 100; i++){
                String newUsername = username + i;
                if(App.getUserByUsername(newUsername) == null){
                    System.out.println("You can pick this : " + newUsername + " Y/N ?");
                    String input = scanner.nextLine();
                    if(input.equals("Y")){
                        username = newUsername;
                        break;
                    } else {
                        return new Result<>(false, "username invalid!");
                    }
                }

            }
        }

        if(!checkRegex(username, "^[a-zA-Z0-9-]+$")) {
            return new Result<>(false, "username format invalid!");
        }

        if (!checkRegex(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,}$")) {
            return new Result<>(false, "Password must be at least 8 characters long, containing uppercase, lowercase, number, and special character.");
        }

        String newPassword = " ";
        if(!password.equals(passwordConfirm)){
            System.out.println("password-confirm doesn't match!");
            System.out.println("Enter password again: (Enter Random for random password!)");

            newPassword = scanner.nextLine();
            if(newPassword.equals("Random")){
                newPassword = generatePassword(12);
                System.out.println("Your password is : " + newPassword + "\n");
            } else if(!newPassword.equals(password)){
                return new Result<>(false, "password-confirm doesn't match!");
            }
        }

        if (!checkRegex(email, "^[a-zA-Z][a-zA-Z0-9_.-]{1,63}@(?!-)[a-zA-Z][-a-zA-Z.]{2,63}\\.(org|com|net|edu)$")) {
            return new Result<>(false, "Invalid email format! Please provide a valid domain!");
        }

        if(!checkRegex(gender, "^(male|female)$")){
            return new Result<>(false, "Gender not allowed!");
        }

        System.out.println("There are 5 security questions choose one:");
        for (SecurityQuestion question : SecurityQuestion.values()) {
            System.out.println(question.getQuestion());
        }

        String input = scanner.nextLine();
        Matcher matcher = LoginMenuCommands.Question.getMatcher(input);
        if(matcher == null) {
            return new Result<>(false, "Invalid command!");
        }

        if(!matcher.group("questionNumber").matches("^\\d+$")) {
            System.out.println(matcher.group("questionNumber"));
            return new Result<>(false, "invalid format of questionNumber!");
        }

        if(!matcher.group("answer").equals(matcher.group("answerConfirm")))
            return new Result<>(false, "answerConfirm is not same as answer!");

        int i = Integer.parseInt(matcher.group("questionNumber"));
        SecurityQuestion question = SecurityQuestion.getByIndex(i - 1);
        String answer = matcher.group("answer");
        if(!newPassword.equals(" "))
            password = newPassword;
        String userPassword = convertToHash(password);

        User newUser = new User(username, userPassword, email, nickname, getGenderEnum(gender), question.getQuestion(), answer);

        App.addUser(newUser);
        //App.setLoggedInUser(newUser);
        return new Result<>(true, "User added successfully!");
    }

    public Result<String> LogIn(String username, String password, Scanner scanner) {

        if(App.getUserByUsername(username) != null){
            if(!App.getUserByUsername(username).getPassword().equals(convertToHash(password))){
                System.out.println("Password is not correct! please answer to the security question:");
                System.out.println(App.getUserByUsername(username).getSecurityQuestion());

                String securityAnswer = scanner.nextLine();
                if(!securityAnswer.equals(App.getUserByUsername(username).getSecurityAnswer())){
                    return new Result<>(false, "Wrong answer!");
                }
            }

            App.setLoggedInUser(App.getUserByUsername(username));
            App.setCurrentMenu(Menu.MainMenu);
            return new Result<>(true, "User logged in successfully, You are now in MainMenu!");
        }

        return new Result<>(false, "User not found");
    }

    public Result<String> LogInStayLoggedIn(String username, String password) {
        return null;
    }

    public Result<String> ForgetPassword(String username, Scanner scanner) {

        if(!(App.getUserByUsername(username) == null)){
            System.out.println(App.getUserByUsername(username).getSecurityQuestion().toString());
            String answer = scanner.nextLine();
            if(answer.equals(App.getUserByUsername(username).getSecurityAnswer())){
                System.out.println("Your password is: " + App.getUserByUsername(username).getPassword());
            } else{
                System.out.println("Wrong answer!\n");
            }

            return new Result<>(true, "");
        }
        return new Result<>(false, "User not found!");
    }

    public Result<String> ShowCurrentMenu() {
        System.out.println(App.getCurrentMenu().toString());
        return new Result<>(true, "");
    }

    public boolean checkRegex(String string, String Regex) {
        return string.matches(Regex);
    }

    public static String generatePassword(int length) {

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!#$%^&*()=+{}[]|\\/;:'\",<>?";
        String allChars = upper + lower + digits + special;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        char[] chars = password.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }
    public Result<String> goMenu(Matcher matcher) {
        if(App.getLoggedInUser() == null)
            return new Result<>(false, "Please login first");
        App.setCurrentMenu(Menu.valueOf(matcher.group("menu")));
        return new Result<>(true, "you are now in " +  matcher.group("menu") + "!");
    }

    public static String convertToHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }

    public static boolean validateHash(String input, String hash) {
        String generatedHash = convertToHash(input);

        return generatedHash.equals(hash);
    }
}
