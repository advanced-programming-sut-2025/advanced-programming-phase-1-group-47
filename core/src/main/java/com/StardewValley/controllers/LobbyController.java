package com.StardewValley.controllers;

import com.StardewValley.View.LobbyView;

public class LobbyController {
    
    private LobbyView view;

    public LobbyController(LobbyView view) {
        this.view = view;
    }

    // The LobbyView now handles all the networking directly
    // This controller is kept for compatibility but most methods are now handled in the view
    
    public void setView(LobbyView view) {
        this.view = view;
    }
}
