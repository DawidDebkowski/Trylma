package com.dawid.gui;

public interface IController {
    public void print(String message);
    public void setClient(GUI client);
    public void lateInitialize();
    public void refresh();
}
