package com.StardewValley.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.StardewValley.controllers.LoginMenuController;
import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.User;

public class LoginView implements Screen{
    private Stage stage;
    private Table rootTable;

    private final LoginMenuController controller;
    private Texture bgTexture;
    private Image background;

    private TextField usernameField, passwordField;
    private Label usernamePlaceholder, passwordPlaceholder;
    private TextButton loginButton, backButton, forgetPasswordButton;
    private CheckBox stayLoggedInCheckBox;
    private Label messageLabel;
    private Skin skin;

    public LoginView(LoginMenuController controller, Skin skin) {
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

        usernamePlaceholder = createPlaceholder("   " + "Enter Username");
        passwordPlaceholder = createPlaceholder("   " + "Enter Password");

        loginButton = new TextButton("Login", skin);
        backButton = new TextButton("Back", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);
        stayLoggedInCheckBox = new CheckBox("Stay Login",skin);
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
        App.addFlashingTitleLabel(stage, skin, "Login Menu");
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
        rootTable.padTop(230).padLeft(80);

        float w = stage.getViewport().getWorldWidth() * 0.3f;
        float h = stage.getViewport().getWorldHeight() * 0.07f;

        rootTable.defaults().pad(8).width(w).height(h);

// افزودن فیلد نام کاربری
        addFieldWithPlaceholder(rootTable, usernameField, usernamePlaceholder);
        rootTable.row().padTop(10);

// افزودن فیلد رمز عبور
        addFieldWithPlaceholder(rootTable, passwordField, passwordPlaceholder);
        rootTable.row().padTop(15);

// گزینه ماندن در سیستم
        rootTable.add(stayLoggedInCheckBox).colspan(2).left();
        rootTable.row().padTop(8);

// دکمه فراموشی رمز
        rootTable.add(forgetPasswordButton).colspan(2).left().width(w).height(h);
        rootTable.row().padTop(10);

// پیام‌ها (پیام خطا یا وضعیت)
        rootTable.add(messageLabel).colspan(2).center().width(w).height(h);
        rootTable.row().padTop(15);

// ساخت جدول دکمه‌ها (ورود / بازگشت)
        Table buttonTable = new Table();
        buttonTable.defaults().pad(6).height(h * 1.1f);

// دکمه ورود
        buttonTable.add(loginButton).width(w * 0.6f).padRight(10);

// دکمه بازگشت
        buttonTable.add(backButton).width(w * 0.3f);

// افزودن جدول دکمه‌ها به جدول اصلی
        rootTable.add(buttonTable).colspan(2).left();
        rootTable.row().padTop(20);

// افزودن کل جدول به صحنه
        stage.addActor(rootTable);

        setupPlaceholderLogic();
        setupListeners();
    }

    private void addFieldWithPlaceholder(Table table, TextField field, Label placeholder) {
        Stack stack = new Stack();
        stack.add(field);
        stack.add(placeholder);
        table.add(stack).left();
    }

    private void setupPlaceholderLogic() {
        stage.addAction(Actions.forever(Actions.run(() -> {
            usernamePlaceholder.setVisible(usernameField.getText().isEmpty());
            passwordPlaceholder.setVisible(passwordField.getText().isEmpty());
        })));

        usernamePlaceholder.addListener(clickToFocus(usernameField));
        passwordPlaceholder.addListener(clickToFocus(passwordField));
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
        addClickListenerWithSound(loginButton, () -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            controller.login(username, password);
        });

        addClickListenerWithSound(backButton, controller::back);

        addClickListenerWithSound(forgetPasswordButton, () -> {
            controller.forgotPassword(usernameField.getText());
        });

        addHoverEffect(loginButton);
        addHoverEffect(backButton);
        addHoverEffect(forgetPasswordButton);
    }

    private void addClickListenerWithSound(TextButton button, Runnable action) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(0.95f, 0.95f, 0.05f),
                    Actions.scaleTo(1f, 1f, 0.05f)
                ));
                action.run();
            }
        });
    }

    private void addHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                button.setColor(0.7f, 0.85f, 1f, 1f);
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setColor(1f, 1f, 1f, 1f);
            }
        });
    }

    public void fogetPassWindow(User user) {
        Dialog dialog = new Dialog("Security Question", skin) {
            @Override
            protected void result(Object object) {
                boolean ok = (Boolean) object;
                if (ok) {
                    TextField inputField = (TextField) getContentTable().findActor("inputField");
                    String answer = inputField.getText().trim();
                    controller.checkQuestion(answer, user);
                }
            }
        };

        TextField inputField = new TextField("", skin);
        inputField.setName("inputField");

        String message =user.getSecurityQuestion();
        inputField.setMessageText(message);

        dialog.getContentTable().add(inputField).width(800).height(100).center().pad(10);

        dialog.button("Ok", true);
        dialog.button("Cancel", false);

        dialog.key(com.badlogic.gdx.Input.Keys.ENTER, true);
        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, false);

        dialog.show(stage);
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
    public TextButton getLoginButton() { return loginButton; }
    public TextButton getBackButton() { return backButton; }
}
