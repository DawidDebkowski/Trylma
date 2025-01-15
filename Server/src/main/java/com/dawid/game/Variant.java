package com.dawid.game;


public enum Variant {
    NORMAL("NORMAL"),
    ORDER_CHAOS("ORDER_CHAOS");


    private final String name;
    Variant(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static Variant getVariantByName(String name) {
        for (Variant variant : Variant.values()) {
            if (variant.getName().equals(name)) {
                return variant;
            }
        }
        return null;
    }
    public static GameVariant getGameVariant(Variant variant) {
        switch (variant) {
            case NORMAL:
                return new StandardVariant();
            case ORDER_CHAOS:
                return new OrderOfChaosVariant();
            default:
                return null;
        }
    }
}