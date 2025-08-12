package com.StardewValley.View;

import com.StardewValley.Main;
import com.StardewValley.controllers.ProfileMenuController;
import com.StardewValley.model.App;
import static com.StardewValley.model.App.addClickListenerWithSound;
import static com.StardewValley.model.App.addHoverEffect;
import com.StardewValley.model.User;
import com.StardewValley.model.enums.Gender;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ProfileView implements Screen {
    private Stage stage;
    private Table rootTable;

    private final ProfileMenuController controller;
    private Texture bgTexture;
    private Image background;

    private TextField usernameField, passwordField, emailField;
    private SelectBox<String> genderSelectBox;
    private Label usernameLabel, passwordLabel, emailLabel, genderLabel, messageLabel;
    private TextButton saveButton, backButton, continueButton;
    private Skin skin;
    private User currentUser;

    public ProfileView(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.currentUser = App.getLoggedInUser();
        initFields();
        controller.setView(this);
    }

    private void initFields() {
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        emailField = new TextField("", skin);

        genderSelectBox = new SelectBox<>(skin);
        genderSelectBox.setItems("Male", "Female");

        usernameLabel = new Label("Username:", skin);
        passwordLabel = new Label("Password:", skin);
        emailLabel = new Label("Email:", skin);
        genderLabel = new Label("Gender:", skin);

        saveButton = new TextButton("Save Changes", skin);
        backButton = new TextButton("Back", skin);
        continueButton = new TextButton("Continue to Game", skin);

        messageLabel = new Label("", skin);
        messageLabel.setAlignment(Align.center);
        messageLabel.setFontScale(0.9f);
        messageLabel.setWrap(true);
        messageLabel.setVisible(false);

        // Populate fields with current user data
        if (currentUser != null) {
            usernameField.setText(currentUser.getUsername());
            passwordField.setText(currentUser.getPassword());
            emailField.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "");
            if (currentUser.getGender() != null) {
                genderSelectBox.setSelected(currentUser.getGender().valueOf());
            }
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);

        loadBackground();
        setupUI();
        App.addFlashingTitleLabel(stage, skin, "Profile Menu");
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

        float w = stage.getViewport().getWorldWidth() * 0.3f;
        float h = stage.getViewport().getWorldHeight() * 0.07f;

        rootTable.defaults().pad(8).width(w).height(h);

        // Title
        Label titleLabel = new Label("Your Profile", skin);
        titleLabel.setFontScale(1.5f);
        titleLabel.setColor(Color.BLUE);
        rootTable.add(titleLabel).colspan(2).center().padBottom(20).row();

        // Username field
        rootTable.add(usernameLabel).left();
        rootTable.add(usernameField).left();
        rootTable.row().padTop(10);

        // Password field
        rootTable.add(passwordLabel).left();
        rootTable.add(passwordField).left();
        rootTable.row().padTop(10);

        // Email field
        rootTable.add(emailLabel).left();
        rootTable.add(emailField).left();
        rootTable.row().padTop(10);

        // Gender field
        rootTable.add(genderLabel).left();
        rootTable.add(genderSelectBox).left();
        rootTable.row().padTop(20);

        // Message label
        rootTable.add(messageLabel).colspan(2).center().row();

        // Buttons
        Table buttonTable = new Table();
        buttonTable.defaults().pad(6).height(h * 1.1f);
        buttonTable.add(saveButton).width(w * 0.4f).padRight(10);
        buttonTable.add(continueButton).width(w * 0.4f).padRight(10);
        buttonTable.add(backButton).width(w * 0.3f);
        rootTable.add(buttonTable).colspan(2).left();

        stage.addActor(rootTable);
        setupListeners();
    }

    private void setupListeners() {
        addClickListenerWithSound(saveButton, () -> {
            String newUsername = usernameField.getText().trim();
            String newPassword = passwordField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newGender = genderSelectBox.getSelected();

            // Validate and save changes
            if (newUsername.isEmpty() || newPassword.isEmpty() || newEmail.isEmpty() || newGender.isEmpty()) {
                showMessage(false, "All fields must be filled!");
                return;
            }

            // Update user information
            if (!newUsername.equals(currentUser.getUsername())) {
                var result = controller.ChangeUsername(newUsername);
                if (!result.isSuccess()) {
                    showMessage(false, result.getMessage());
                    return;
                }
            }

            if (!newPassword.equals(currentUser.getPassword())) {
                var result = controller.ChangePassword(currentUser.getPassword(), newPassword);
                if (!result.isSuccess()) {
                    showMessage(false, result.getMessage());
                    return;
                }
            }

            if (!newEmail.equals(currentUser.getEmail())) {
                var result = controller.ChangeEmail(newEmail);
                if (!result.isSuccess()) {
                    showMessage(false, result.getMessage());
                    return;
                }
            }

            // Update gender if changed
            if (!newGender.equals(currentUser.getGender() != null ? currentUser.getGender().valueOf() : "")) {
                currentUser.setGender(Gender.getGenderEnum(newGender));
            }

            showMessage(true, "Profile updated successfully!");
        });

        addClickListenerWithSound(continueButton, () -> {
            // Go to game mode selection
            controller.continueToGame();
        });

        addClickListenerWithSound(backButton, () -> {
            // Go back to main menu
            controller.back();
        });

        addHoverEffect(saveButton);
        addHoverEffect(continueButton);
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
}
