package com.dawid.gui;

public interface IController {
    public void print(String message);
    public void setClient(GUI client);
    public void initialize();
    public void refresh();
}
