    package com.StardewValley.View;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.scenes.scene2d.*;
    import com.badlogic.gdx.scenes.scene2d.actions.Actions;
    import com.badlogic.gdx.scenes.scene2d.ui.*;
    import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
    import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
    import com.badlogic.gdx.utils.Align;
    import com.badlogic.gdx.utils.ScreenUtils;
    import com.badlogic.gdx.utils.viewport.ScreenViewport;
    import com.StardewValley.controllers.SignUpMenuController;
    import com.StardewValley.Main;
    import com.StardewValley.model.App;

    public class SignUpView implements Screen {
        private Stage stage;
        private Table rootTable;

        private final SignUpMenuController controller;
        private Texture bgTexture;
        private Image background;

        private TextField usernameField, passwordField, securityQuestionField;
        private Label usernamePlaceholder, passwordPlaceholder, securityLabel, messageLabel;
        private SelectBox<String> selectBox;
        private TextButton signUpButton, backButton, guestButton;
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
            securityQuestionField = new TextField("", skin);

            usernamePlaceholder = createPlaceholder("Enter username");
            passwordPlaceholder = createPlaceholder("Enter password");

            securityLabel = new Label("City of Birth", skin);
            securityLabel.setColor(new Color(1f, 0.8f, 0.2f, 0.8f));

            selectBox = new SelectBox<>(skin);
            selectBox.setItems("1-Which City Born?", "2-Your Favourite Singer?", "3-Your Role Model?");

            signUpButton = new TextButton("-" + "Sign Up" + "-", skin);
            backButton = new TextButton("-" + "Back" +"-", skin);
            guestButton = new TextButton("-" + "Login as Guest" + "-", skin);

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
            bgTexture = new Texture(Gdx.files.internal("BackGrounds/bg.png"));
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
            rootTable.padTop(238).padLeft(80);

            float w = stage.getViewport().getWorldWidth() * 0.24f;
            float h = stage.getViewport().getWorldHeight() * 0.08f;
            rootTable.defaults().pad(4).width(w).height(h);

            addFieldWithPlaceholder(rootTable, usernameField, usernamePlaceholder);
            rootTable.row().padTop(5);
            addFieldWithPlaceholder(rootTable, passwordField, passwordPlaceholder);
            rootTable.row().padTop(5);

            rootTable.add(new Label("Choose Security Question" +" :", skin)).left();
            rootTable.row().padTop(5);
            rootTable.add(selectBox).left();
            rootTable.row().padTop(5);
            rootTable.add(securityLabel).left();
            rootTable.row().padTop(5);
            rootTable.add(securityQuestionField).left();
            rootTable.row().padTop(8);

            Table buttonTable = new Table();
            buttonTable.defaults().pad(6);

            buttonTable.defaults().width(w * 0.7f).height(h * 1.1f);
            buttonTable.add(signUpButton).padRight(10);
            buttonTable.defaults().width(w * 0.3f).height(h * 0.9f);
            buttonTable.add(backButton);

            rootTable.add(buttonTable).left();
            rootTable.add(guestButton).left().width(w * 0.95f).height(h * 0.9f).padLeft(100);

            rootTable.row().padTop(10);
            rootTable.add(messageLabel).width(w).center();
            rootTable.padLeft(56);

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
                securityLabel.setVisible(securityQuestionField.getText().isEmpty());
            })));

            usernamePlaceholder.addListener(clickToFocus(usernameField));
            passwordPlaceholder.addListener(clickToFocus(passwordField));
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
                selectBox.getSelected(),
                securityQuestionField.getText().trim()
            ));

            addClickListenerWithSound(backButton, controller::back);


            addHoverEffect(signUpButton);
            addHoverEffect(backButton);
            addHoverEffect(guestButton);
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
        public TextButton getGuestButton() { return guestButton; }
    }
