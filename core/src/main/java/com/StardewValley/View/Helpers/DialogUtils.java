package com.StardewValley.View.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.HashMap;
import java.util.Map;

public class DialogUtils {
    public static class DialogButton {
        private final String text;
        private final Object result;
        private final Runnable action;

        public DialogButton(String text, Object result, Runnable action) {
            this.text = text;
            this.result = result;
            this.action = action;
        }
    }

    // نگهداری آخرین دیالوگ باز شده
    private static Dialog currentOpenDialog;

    public static void openDialog(
            Skin skin,
            com.badlogic.gdx.scenes.scene2d.Stage dialogStage,
            String title,
            String message,
            String imagePath,
            float width,
            float height,
            float xPosition,
            float yPosition,
            DialogButton... buttons
    ) {
        try {
            currentOpenDialog = new Dialog(title, skin) {
                @Override
                protected void result(Object obj) {
                    for (DialogButton button : buttons) {
                        if (button.result.equals(obj) && button.action != null) {
                            button.action.run();
                        }
                    }
                    this.hide();
                }
            };

            // تنظیم فونت و استایل برای متن
            BitmapFont bigFont = new BitmapFont();
            bigFont.getData().setScale(2f);
            Label.LabelStyle bigLabelStyle = new Label.LabelStyle(bigFont, Color.WHITE);

            // تنظیم محتوای دیالوگ
            currentOpenDialog.getContentTable().clear();
            currentOpenDialog.getContentTable().add(new Label(message, bigLabelStyle)).pad(20).row();
            if (imagePath != null && !imagePath.isEmpty()) {
                currentOpenDialog.getContentTable().add(new Image(new Texture(Gdx.files.internal(imagePath)))).size(100, 100).pad(10);
            }

            // افزودن دکمه‌ها
            for (DialogButton button : buttons) {
                currentOpenDialog.button(button.text, button.result).pad(20);
            }

            // تنظیم اندازه و موقعیت دیالوگ
            currentOpenDialog.setSize(width, height);
            currentOpenDialog.setPosition(xPosition, yPosition);
            currentOpenDialog.show(dialogStage);

            Gdx.app.log("Dialog", "Opened dialog: " + title);
        } catch (Exception e) {
            Gdx.app.error("Dialog", "Error opening dialog: " + e.getMessage());
        }
    }

    public static void closeDialog() {
        if (currentOpenDialog != null) {
            currentOpenDialog.hide();
            currentOpenDialog = null;
            Gdx.app.log("Dialog", "Dialog closed successfully.");
        }
    }
}