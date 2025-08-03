package com.StardewValley.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class MultiplayerChatView implements Disposable {
    private Stage stage;
    private Table chatTable;
    private ScrollPane chatScrollPane;
    private TextArea chatArea;
    private TextField inputField;
    private TextButton sendButton;
    private Array<String> chatMessages;
    private boolean isVisible;
    private float x, y, width, height;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public interface ChatMessageListener {
        void onSendMessage(String message);
    }

    private ChatMessageListener messageListener;

    public MultiplayerChatView(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.chatMessages = new Array<>();
        this.isVisible = false;
        
        stage = new Stage();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        
        createUI();
    }

    private void createUI() {
        // Create chat area
        chatArea = new TextArea("", new TextField.TextFieldStyle());
        chatArea.setDisabled(true);
        chatArea.setPrefRows(10);
        
        // Create scroll pane for chat
        chatScrollPane = new ScrollPane(chatArea);
        chatScrollPane.setScrollbarsVisible(true);
        
        // Create input field
        inputField = new TextField("", new TextField.TextFieldStyle());
        inputField.setMessageText("Type your message...");
        
        // Create send button
        sendButton = new TextButton("Send", new TextButton.TextButtonStyle());
        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendMessage();
            }
        });
        
        // Create table layout
        chatTable = new Table();
        chatTable.setPosition(x, y);
        chatTable.setSize(width, height);
        
        chatTable.add(chatScrollPane).expand().fill().row();
        
        Table inputTable = new Table();
        inputTable.add(inputField).expandX().fillX();
        inputTable.add(sendButton).width(80);
        
        chatTable.add(inputTable).fillX().row();
        
        stage.addActor(chatTable);
        
        // Add enter key listener to input field
        inputField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                    sendMessage();
                }
            }
        });
    }

    public void addMessage(String message) {
        chatMessages.add(message);
        updateChatDisplay();
    }

    public void addSystemMessage(String message) {
        addMessage("[System] " + message);
    }

    public void addPlayerMessage(String playerName, String message) {
        addMessage(playerName + ": " + message);
    }

    private void updateChatDisplay() {
        StringBuilder sb = new StringBuilder();
        for (String message : chatMessages) {
            sb.append(message).append("\n");
        }
        chatArea.setText(sb.toString());
        
        // Scroll to bottom
        chatScrollPane.setScrollY(chatScrollPane.getMaxY());
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty() && messageListener != null) {
            messageListener.onSendMessage(message);
            inputField.setText("");
        }
    }

    public void setMessageListener(ChatMessageListener listener) {
        this.messageListener = listener;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        chatTable.setVisible(visible);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void render(SpriteBatch batch) {
        if (isVisible) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (font != null) {
            font.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        chatTable.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        chatTable.setSize(width, height);
    }

    public Array<String> getChatMessages() {
        return chatMessages;
    }

    public void clearChat() {
        chatMessages.clear();
        updateChatDisplay();
    }
} 