package com.server;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(Collections.nCopies(10, null));
        System.out.println(list);
    }
}
