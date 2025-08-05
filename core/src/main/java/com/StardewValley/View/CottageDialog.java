package com.StardewValley.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class CottageDialog {
    private Dialog dialog;
    private Stage stage;
    private Skin skin;

    public CottageDialog(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        createDialog();
    }

    private void createDialog() {
        dialog = new Dialog("Cottage Menu", skin);

        // تنظیم محتوای دیالوگ
        Table contentTable = dialog.getContentTable();
        contentTable.pad(20);

        // اضافه کردن دکمه‌ها
        TextButton cookingButton = new TextButton("آشپزی", skin);
        TextButton craftingButton = new TextButton("صنعتگری", skin);
        TextButton exitButton = new TextButton("خروج", skin);

        // استایل‌دهی دکمه‌ها
        cookingButton.getLabel().setFontScale(1.2f);
        craftingButton.getLabel().setFontScale(1.2f);
        exitButton.getLabel().setFontScale(1.1f);

        // اضافه کردن به دیالوگ
        contentTable.add(cookingButton).width(200).height(60).padBottom(15).row();
        contentTable.add(craftingButton).width(200).height(60).padBottom(15).row();
        contentTable.add(exitButton).width(150).height(50);

        // تنظیم اندازه و موقعیت دیالوگ
        dialog.setSize(400, 350);
        dialog.setPosition(
                (Gdx.graphics.getWidth() - 400) / 2,
                (Gdx.graphics.getHeight() - 350) / 2
        );
        dialog.setModal(true);
        dialog.setMovable(false);
    }

    public void show() {
        dialog.show(stage);
    }

    public void setCookingListener(ClickListener listener) {
        dialog.getContentTable().findActor("آشپزی").addListener(listener);
    }

    public void setCraftingListener(ClickListener listener) {
        dialog.getContentTable().findActor("صنعتگری").addListener(listener);
    }

    public void setExitListener(ClickListener listener) {
        dialog.getContentTable().findActor("خروج").addListener(listener);
    }

    public void hide() {
        dialog.hide();
    }
}
