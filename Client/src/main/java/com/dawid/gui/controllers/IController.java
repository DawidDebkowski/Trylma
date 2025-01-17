package com.dawid.gui.controllers;

import com.dawid.gui.ClientGUI;

public interface IController {
    public void print(String message);
    public void setClient(ClientGUI client);
    public void lateInitialize();
    public void refresh();
}
