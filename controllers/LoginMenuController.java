package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Gender;
import models.enums.Menu;
import models.enums.SecurityQuestion;

import java.util.Scanner;
import java.security.SecureRandom;

public class LoginMenuController {

    public Result<String> SignUp(String username, String password, String passwordConfirm, String nickname, String email, String gender, Scanner scanner) {

        if(App.getUserByUsername(username) != null) {

            System.out.println("Username already exists!\n");

            for(int i = 0; i < 100; i++){
                String newUsername = username + i;
                if(App.getUserByUsername(username) == null){
                    System.out.println("You can pick this : " + newUsername + " Y/N ?");

                    String input = scanner.nextLine();
                    if(input.equals("Y")){
                        username = newUsername;
                    } else {
                        return new Result<>(false, "username invalid!\n");
                    }
                }

            }
        }

        if(!checkRegex(username, "^[a-zA-Z0-9-]+$")) {
            return new Result<>(false, "username format invalid!\n");
        }

        if(!checkRegex(password, "^[a-zA-Z0-9!#$%^&*()=+{}[]|\\/:;'\",<>?]+$")) {
            return new Result<>(false, "password format invalid!\n");
        }

        if(!password.equals(passwordConfirm)){
            System.out.println("password-confirm doesn't match!\n");
            System.out.println("Enter password again:\n");

            String newPassword = scanner.nextLine();
            if(newPassword.equals("Random")){
                newPassword = generatePassword(12);
                System.out.println("Your password is : " + newPassword + "\n");
            } else if(!newPassword.equals(password)){
                return new Result<>(false, "password-confirm doesn't match!\n");
            }
        }

        checkPassword(password);
        if(!checkRegex(email, "^[a-zA-Z][a-zA-Z0-9_.-]{3,7}@(?!-)[a-zA-Z][-a-zA-Z.]{1,5}[a-zA-Z](\\\\.org|\\\\.com|\\\\.net|\\\\.edu)$")){
            return new Result<>(false, "Email format not correct!\n");
        }

        if(!checkRegex(gender, "^(male|female)$")){
            return new Result<>(false, "Gender not allowed!\n");
        }

        System.out.println("There are 5 security questions choose one:\n");
        for (SecurityQuestion question : SecurityQuestion.values()) {
            System.out.println(question.getQuestion());
        }

        int i = scanner.nextInt();
        String answer = scanner.nextLine();
        String question = String.valueOf(SecurityQuestion.values()[i - 1]);

        User newUser = new User(username, password, email, nickname, Gender.valueOf(gender), question, answer);
        App.addUser(newUser);
        return new Result<>(true, "User added successfully!\n");
    }

    public Result<String> LogIn(String username, String password, Scanner scanner) {

        if(App.getUserByUsername(username) != null){
            if(!App.getUserByUsername(username).getPassword().equals(password)){
                System.out.println("Password in incorrect! please answer to the security question:");
                System.out.println(App.getUserByUsername(username).getSecurityQuestion());

                String securityAnswer = scanner.nextLine();
                if(!securityAnswer.equals(App.getUserByUsername(username).getSecurityAnswer())){
                    return new Result<>(false, "Wrong answer!\n");
                }
            }

            App.setLoggedInUser(App.getUserByUsername(username));
            App.setCurrentMenu(Menu.MainMenu);
            return new Result<>(true, "User logged in successfully, You are now in MainMenu!\n");
        }

        return new Result<>(false, "User not found");
    }

    public Result<String> LogInStayLoggedIn(String username, String password) {
        return null;
    }

    public Result<String> ForgetPassword(String username, Scanner scanner) {
        if(!(App.getUserByUsername(username) == null)){
            System.out.println(App.getUserByUsername(username).getSecurityQuestion());
            String answer = scanner.nextLine();
            if(answer.equals(App.getUserByUsername(username).getSecurityAnswer())){
                System.out.println("Your password is: " + App.getUserByUsername(username).getPassword());
            } else{
                System.out.println("Wrong answer!\n");
            }

            return new Result<>(true, "");
        }
        return new Result<>(false, "User not found!\n");
    }

    public Result<String> ShowCurrentMenu() {
        System.out.println(App.getCurrentMenu().toString());
        return new Result<>(true, "");
    }

    public boolean checkRegex(String string, String Regex) {
        return string.matches(Regex);
    }

    public void checkPassword(String password){
        if(password.length() < 8){
            System.out.println("Warning: password is too short!\n");
        }

        if(!password.matches("[A-Z]")){
            System.out.println("Warning: password doesn't have [A-Z]!\n");
        }

        if(!password.matches("[a-z]")){
            System.out.println("Warning: password doesn't have [a-z]!\n");
        }

        if(!password.matches("[0-9]")){
            System.out.println("Warning: password doesn't have [0-9]!\n");
        }

        if(!password.matches("[!#$%^&*()=+{}]|\\/:;'\",<>?")){
            System.out.println("Warning: password doesn't have special character!\n");
        }
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
}
