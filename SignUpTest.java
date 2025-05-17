public class SignUpTest {

    public static void main(String[] args) {
        SignUpTest test = new SignUpTest();
        test.runAllTests();
    }

    void runAllTests() {
        testValidInput();
        testUsernameExistsAndAcceptsSuggestion();
        testInvalidUsernameFormat();
        testWeakPassword();
        testPasswordMismatchRetryFail();
        testInvalidEmail();
        testInvalidGender();
        testInvalidSecurityAnswerMismatch();
    }

    void printResult(String testName, boolean passed) {
        System.out.println("[" + testName + "] " + (passed ? "✅ PASSED" : "❌ FAILED"));
    }

    // Dummy user store
    String[] existingUsers = { "ali", "reza", "john" };

    // Simulates the SignUp logic
    Result SignUp(String username, String password, String passwordConfirm, String nickname, String email, String gender, String questionNumber, String answer, String answerConfirm, boolean acceptSuggestedUsername, boolean retryPassword, String retryInput) {

        // Check if username exists
        for (String existing : existingUsers) {
            if (existing.equals(username)) {
                if (!acceptSuggestedUsername) {
                    return new Result(false, "username invalid!");
                } else {
                    // simulate choosing suggested username
                    for (int i = 0; i < 100; i++) {
                        String newUsername = username + i;
                        boolean found = false;
                        for (String u : existingUsers) {
                            if (u.equals(newUsername)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            username = newUsername;
                            break;
                        }
                    }
                }
            }
        }

        if (!username.matches("^[a-zA-Z0-9-]+$")) {
            return new Result(false, "username format invalid!");
        }

        if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,}")) {
            return new Result(false, "Password format invalid!");
        }

        if (!password.equals(passwordConfirm)) {
            if (!retryPassword || !retryInput.equals(password)) {
                return new Result(false, "password-confirm doesn't match!");
            }
        }

        if (!email.matches("^[a-zA-Z][a-zA-Z0-9_.-]{1,63}@(?!-)[a-zA-Z][-a-zA-Z.]{2,63}\\.(org|com|net|edu)$")) {
            return new Result(false, "Invalid email format!");
        }

        if (!(gender.equals("male") || gender.equals("female"))) {
            return new Result(false, "Gender not allowed!");
        }

        if (!(questionNumber.matches("\\d+"))) {
            return new Result(false, "invalid format of questionNumber!");
        }

        if (!answer.equals(answerConfirm)) {
            return new Result(false, "answerConfirm is not same as answer!");
        }

        return new Result(true, "User added successfully!");
    }

    // TEST CASES

    void testValidInput() {
        Result r = SignUp("newuser", "Strong1@", "Strong1@", "nick", "email@domain.com", "male", "1", "a", "a", false, false, "");
        printResult("Valid Input", r.success && r.message.equals("User added successfully!"));
    }

    void testUsernameExistsAndAcceptsSuggestion() {
        Result r = SignUp("ali", "Strong1@", "Strong1@", "nick", "email@domain.com", "female", "1", "a", "a", true, false, "");
        printResult("Username Exists and Accepts Suggestion", r.success);
    }

    void testInvalidUsernameFormat() {
        Result r = SignUp("inva!id", "Strong1@", "Strong1@", "nick", "email@domain.com", "male", "1", "a", "a", false, false, "");
        printResult("Invalid Username Format", !r.success && r.message.contains("username format"));
    }

    void testWeakPassword() {
        Result r = SignUp("user1", "weakpass", "weakpass", "nick", "email@domain.com", "male", "1", "a", "a", false, false, "");
        printResult("Weak Password", !r.success && r.message.contains("Password"));
    }

    void testPasswordMismatchRetryFail() {
        Result r = SignUp("user2", "Strong1@", "Wrong1@", "nick", "email@domain.com", "male", "1", "a", "a", false, true, "WrongAgain");
        printResult("Password Mismatch and Retry Fail", !r.success && r.message.contains("password-confirm"));
    }

    void testInvalidEmail() {
        Result r = SignUp("user3", "Strong1@", "Strong1@", "nick", "bad-email", "male", "1", "a", "a", false, false, "");
        printResult("Invalid Email", !r.success && r.message.contains("email"));
    }

    void testInvalidGender() {
        Result r = SignUp("user4", "Strong1@", "Strong1@", "nick", "email@domain.com", "alien", "1", "a", "a", false, false, "");
        printResult("Invalid Gender", !r.success && r.message.contains("Gender"));
    }

    void testInvalidSecurityAnswerMismatch() {
        Result r = SignUp("user5", "Strong1@", "Strong1@", "nick", "email@domain.com", "female", "1", "a", "b", false, false, "");
        printResult("Security Answer Mismatch", !r.success && r.message.contains("answerConfirm"));
    }

    // Simple Result class
    class Result {
        boolean success;
        String message;
        Result(boolean s, String m) {
            success = s;
            message = m;
        }
    }
}
