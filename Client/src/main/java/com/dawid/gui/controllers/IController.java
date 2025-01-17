package com.dawid.gui.controllers;

import com.dawid.IClient;

public interface IController {
    void print(String message);
    void setClient(IClient client);
    void lateInitialize();
    void refresh();
}
