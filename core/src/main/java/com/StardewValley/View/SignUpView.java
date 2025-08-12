    package com.StardewValley.View;

    import com.StardewValley.Main;
    import com.StardewValley.controllers.SignUpMenuController;
    import com.StardewValley.model.App;
    import static com.StardewValley.model.App.addClickListenerWithSound;
    import static com.StardewValley.model.App.addFieldWithPlaceholder;
    import static com.StardewValley.model.App.addHoverEffect;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.InputEvent;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.actions.Actions;
    import com.badlogic.gdx.scenes.scene2d.ui.Image;
    import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

    public class SignUpView implements Screen {
        private Stage stage;
        private Table rootTable;

        private final SignUpMenuController controller;
        private Texture bgTexture;
        private Image background;

        private TextField usernameField, passwordField, emailField, securityQuestionField;
        private Label usernamePlaceholder, passwordPlaceholder, emailPlaceholder, securityLabel, messageLabel;
        private SelectBox<String> selectBox, genderSelectBox;
        private TextButton signUpButton, backButton, resetDbButton, copyPasswordButton;
        private Skin skin;

        public SignUpView(SignUpMenuController controller, Skin skin) {
            this.controller = controller;
            this.skin = skin;
            initFields();
            controller.setView(this);
        }

            private void initFields() {
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        emailField = new TextField("", skin);
        securityQuestionField = new TextField("", skin);

        usernamePlaceholder = createPlaceholder("Enter username");
        passwordPlaceholder = createPlaceholder("Enter password");
        emailPlaceholder = createPlaceholder("Enter email");

        securityLabel = new Label("City of Birth", skin);
        securityLabel.setColor(new Color(1f, 0.8f, 0.2f, 0.8f));

        selectBox = new SelectBox<>(skin);
        selectBox.setItems("1-Which City Born?", "2-Your Favourite Singer?", "3-Your Role Model?");

        genderSelectBox = new SelectBox<>(skin);
        genderSelectBox.setItems("Male", "Female");

        signUpButton = new TextButton("-" + "Sign Up" + "-", skin);
        backButton = new TextButton("-" + "Back" +"-", skin);
        
        // Add a reset database button for troubleshooting
        TextButton resetDbButton = new TextButton("Reset Database", skin);
        resetDbButton.setColor(Color.RED);
        resetDbButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    com.StardewValley.DataBase.DataBaseInit.resetDatabase();
                    showMessage(true, "Database reset successfully! Please try signing up again.");
                } catch (Exception e) {
                    showMessage(false, "Failed to reset database: " + e.getMessage());
                }
            }
        });

        messageLabel = new Label("", skin);
        messageLabel.setAlignment(Align.center);
        messageLabel.setFontScale(0.9f);
        messageLabel.setWrap(true);
        messageLabel.setVisible(false);
    }

        private Label createPlaceholder(String text) {
            Label placeholder = new Label(text, skin);
            placeholder.setColor(new Color(0.7f, 0.7f, 0.7f, 0.5f));
            return placeholder;
        }

        @Override
        public void show() {
            stage = new Stage(new ScreenViewport(), Main.getBatch());
            Gdx.input.setInputProcessor(stage);
            loadBackground();
            setupUI();
            App.addFlashingTitleLabel(stage, skin, "Sign Up");
        }

        private void loadBackground() {
            bgTexture = new Texture(Gdx.files.internal("BackGrounds/VahidInit.jpg"));
            bgTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            background = new Image(bgTexture);
            background.setFillParent(true);
            stage.addActor(background);
        }

        private void setupUI() {
            if (rootTable != null) rootTable.clear();

            rootTable = new Table();
            rootTable.setFillParent(true);
            rootTable.align(Align.topLeft);
            rootTable.padTop(200).padLeft(80);

            float w = stage.getViewport().getWorldWidth() * 0.22f;
            float h = stage.getViewport().getWorldHeight() * 0.08f;
            rootTable.defaults().pad(4).width(w).height(h);

            // Left column - First half of fields
            Table leftColumn = new Table();
            leftColumn.defaults().pad(4).width(w).height(h);

                    addFieldWithPlaceholder(leftColumn, usernameField, usernamePlaceholder);
        leftColumn.row().padTop(5);
        
        // Password field with random password button
        Table passwordRow = new Table();
        passwordRow.defaults().pad(2);
        
        // Password field takes most of the width
        passwordRow.add(passwordField).width(w * 0.7f).height(h);
        
        // Random password button
        TextButton randomPasswordButton = new TextButton("🎲", skin);
        randomPasswordButton.setColor(Color.BLUE);
        randomPasswordButton.setWidth(h * 0.8f);
        randomPasswordButton.setHeight(h * 0.8f);
        
        // Add tooltip for better UX
        randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String randomPassword = generateRandomPassword();
                passwordField.setText(randomPassword);
                showMessage(true, "Random password generated: " + randomPassword);
                
                // Show the copy button after generating a password
                copyPasswordButton.setVisible(true);
            }
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Show tooltip on hover
                showMessage(true, "Click to generate a secure random password");
            }
            
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Hide tooltip when not hovering
                messageLabel.setVisible(false);
            }
        });
        
        // Copy password button (appears after password is generated)
        copyPasswordButton = new TextButton("📋", skin);
        copyPasswordButton.setColor(Color.GREEN);
        copyPasswordButton.setWidth(h * 0.8f);
        copyPasswordButton.setHeight(h * 0.8f);
        copyPasswordButton.setVisible(false); // Hidden by default
        
        copyPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String password = passwordField.getText();
                if (!password.isEmpty()) {
                    // Copy to clipboard (platform-specific)
                    try {
                        java.awt.Toolkit.getDefaultToolkit()
                            .getSystemClipboard()
                            .setContents(
                                new java.awt.datatransfer.StringSelection(password),
                                null
                            );
                        showMessage(true, "Password copied to clipboard!");
                    } catch (Exception e) {
                        showMessage(false, "Could not copy to clipboard: " + e.getMessage());
                    }
                } else {
                    showMessage(false, "No password to copy!");
                }
            }
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showMessage(true, "Click to copy password to clipboard");
            }
            
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                messageLabel.setVisible(false);
            }
        });
        
        passwordRow.add(randomPasswordButton).width(h * 0.8f).height(h * 0.8f);
        passwordRow.add(copyPasswordButton).width(h * 0.8f).height(h * 0.8f);
        
        // Add password placeholder
        passwordPlaceholder.setPosition(passwordField.getX(), passwordField.getY());
        stage.addActor(passwordPlaceholder);
        
        leftColumn.add(passwordRow).left();
        leftColumn.row().padTop(2);
        
        // Password strength indicator
        Label passwordStrengthLabel = new Label("", skin);
        passwordStrengthLabel.setColor(Color.WHITE);
        passwordStrengthLabel.setFontScale(0.7f);
        leftColumn.add(passwordStrengthLabel).left();
        leftColumn.row().padTop(3);
        
        // Add real-time password strength checking
        passwordField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = passwordField.getText();
                String strengthMessage = getPasswordStrengthMessage(password);
                passwordStrengthLabel.setText(strengthMessage);
                
                // Update color based on strength
                if (strengthMessage.contains("Strong")) {
                    passwordStrengthLabel.setColor(Color.GREEN);
                } else if (strengthMessage.contains("Medium")) {
                    passwordStrengthLabel.setColor(Color.YELLOW);
                } else if (strengthMessage.contains("Weak")) {
                    passwordStrengthLabel.setColor(Color.RED);
                } else {
                    passwordStrengthLabel.setColor(Color.WHITE);
                }
            }
        });
            addFieldWithPlaceholder(leftColumn, emailField, emailPlaceholder);
            leftColumn.row().padTop(5);

            leftColumn.add(new Label("Gender:", skin)).left();
            leftColumn.row().padTop(5);
            leftColumn.add(genderSelectBox).left();

            // Right column - Second half of fields
            Table rightColumn = new Table();
            rightColumn.defaults().pad(4).width(w).height(h);
            rightColumn.padLeft(50); // Add spacing between columns

            rightColumn.add(new Label("Choose Security Question:", skin)).left();
            rightColumn.row().padTop(5);
            rightColumn.add(selectBox).left();
            rightColumn.row().padTop(5);
            rightColumn.add(securityLabel).left();
            rightColumn.row().padTop(5);
            addFieldWithPlaceholder(rightColumn, securityQuestionField, securityLabel);

            // Add both columns to main table
            rootTable.add(leftColumn).left();
            rootTable.add(rightColumn).left();
            rootTable.row().padTop(20);

            // Buttons row
            Table buttonTable = new Table();
            buttonTable.defaults().pad(6);

            buttonTable.defaults().width(w * 0.7f).height(h * 1.1f);
            buttonTable.add(signUpButton).padRight(10);
            buttonTable.defaults().width(w * 0.3f).height(h * 0.9f);
            buttonTable.add(backButton);

            rootTable.add(buttonTable).left();

            rootTable.row().padTop(10);
            rootTable.add(messageLabel).width(w * 2 + 50).center(); // Span both columns
            
            // Add reset database button below the message
            rootTable.row().padTop(5);
            rootTable.add(resetDbButton).colspan(2).center().width(w * 0.8f).height(h * 0.8f);

            stage.addActor(rootTable);

            setupPlaceholderLogic();
            setupListeners();
        }

        private void setupPlaceholderLogic() {
            stage.addAction(Actions.forever(Actions.run(() -> {
                usernamePlaceholder.setVisible(usernameField.getText().isEmpty());
                passwordPlaceholder.setVisible(passwordField.getText().isEmpty());
                emailPlaceholder.setVisible(emailField.getText().isEmpty());
                securityLabel.setVisible(securityQuestionField.getText().isEmpty());
            })));

            usernamePlaceholder.addListener(clickToFocus(usernameField));
            passwordPlaceholder.addListener(clickToFocus(passwordField));
            emailPlaceholder.addListener(clickToFocus(emailField));
            securityLabel.addListener(clickToFocus(securityQuestionField));
        }

        private ClickListener clickToFocus(final TextField field) {
            return new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.setKeyboardFocus(field);
                }
            };
        }

        private void setupListeners() {
            selectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    switch (selectBox.getSelected()) {
                        case "1-Which City Born?":
                            securityLabel.setText("City of Birth ?");
                            break;
                        case "2-Your Favourite Singer?":
                            securityLabel.setText( "Favourite Singer ?");
                            break;
                        case "3-Your Role Model?":
                            securityLabel.setText("3-Your Role Model?" +" ?");
                            break;
                        default:
                            securityLabel.setText("Answer Question");
                    }
                }
            });

            addClickListenerWithSound(signUpButton, () -> controller.signUp(
                usernameField.getText().trim(),
                passwordField.getText().trim(),
                emailField.getText().trim(),
                genderSelectBox.getSelected(),
                selectBox.getSelected(),
                securityQuestionField.getText().trim()
            ));

            addClickListenerWithSound(backButton, controller::back);


            addHoverEffect(signUpButton);
            addHoverEffect(backButton);
        }
        public void showMessage(boolean success, String message) {
            messageLabel.clearActions();
            messageLabel.setText(message);
            messageLabel.setColor(success ? Color.GREEN : Color.RED);
            messageLabel.setVisible(true);
            messageLabel.getColor().a = 1f;

            messageLabel.addAction(Actions.sequence(
                Actions.delay(3f),
                Actions.fadeOut(0.5f),
                Actions.run(() -> messageLabel.setVisible(false))
            ));
        }

        @Override
        public void render(float delta) {
            ScreenUtils.clear(0, 0, 0, 1);
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        @Override
        public void resize(int width, int height) {
            stage.getViewport().update(width, height, true);
            setupUI();
        }

        @Override public void pause() {}
        @Override public void resume() {}

        @Override
        public void hide() {
            if (stage != null) stage.clear();
        }

        @Override
        public void dispose() {
            if (stage != null) stage.dispose();
            if (bgTexture != null) bgTexture.dispose();
        }

        public TextField getUsernameField() { return usernameField; }
        public TextField getPasswordField() { return passwordField; }
        public SelectBox<String> getSelectBox() { return selectBox; }
        public TextButton getSignUpMenu() { return signUpButton; }
        public TextButton getBackButton() { return backButton; }
        
        /**
         * Generates a secure random password that meets the requirements
         */
        private String generateRandomPassword() {
            // Password requirements: 8+ characters with uppercase, digit, and symbol
            String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowercase = "abcdefghijklmnopqrstuvwxyz";
            String digits = "0123456789";
            String symbols = "@_()*&%$#";
            
            StringBuilder password = new StringBuilder();
            java.util.Random random = new java.util.Random();
            
            // Ensure at least one character from each required category
            password.append(uppercase.charAt(random.nextInt(uppercase.length()))); // Uppercase
            password.append(digits.charAt(random.nextInt(digits.length())));       // Digit
            password.append(symbols.charAt(random.nextInt(symbols.length())));    // Symbol
            password.append(lowercase.charAt(random.nextInt(lowercase.length()))); // Lowercase
            
            // Fill the rest with random characters (total length: 12-16 characters)
            int remainingLength = 8 + random.nextInt(5); // 8-12 additional characters
            String allChars = uppercase + lowercase + digits + symbols;
            
            for (int i = 0; i < remainingLength; i++) {
                password.append(allChars.charAt(random.nextInt(allChars.length())));
            }
            
            // Shuffle the password to make it more random
            char[] passwordArray = password.toString().toCharArray();
            for (int i = passwordArray.length - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                char temp = passwordArray[i];
                passwordArray[i] = passwordArray[j];
                passwordArray[j] = temp;
            }
            
            return new String(passwordArray);
        }
        
        /**
         * Checks password strength and returns a descriptive message
         */
        private String getPasswordStrengthMessage(String password) {
            if (password.isEmpty()) {
                return "";
            }
            
            boolean hasUppercase = password.matches(".*[A-Z].*");
            boolean hasLowercase = password.matches(".*[a-z].*");
            boolean hasDigit = password.matches(".*\\d.*");
            boolean hasSymbol = password.matches(".*[@_()*&%$#].*");
            boolean hasMinLength = password.length() >= 8;
            
            int strength = 0;
            if (hasUppercase) strength++;
            if (hasLowercase) strength++;
            if (hasDigit) strength++;
            if (hasSymbol) strength++;
            if (hasMinLength) strength++;
            
            if (strength == 5) {
                return "Strong password ✓";
            } else if (strength >= 3) {
                return "Medium strength password";
            } else {
                return "Weak password - needs improvement";
            }
        }
    }
