package com.dawid.gui.controllers;

import com.dawid.IClient;
import com.dawid.gui.ClientGUI;

public interface IController {
    public void print(String message);
    public void setClient(IClient client);
    public void lateInitialize();
    public void refresh();
}
