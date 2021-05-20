package com.omottec.demoapp.lang;

public class Computer {
    protected final String name;
    public final String model;

    public Computer(String name, String model) {
        this.name = name;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Computer{" + "name='" + name + '\'' + ", model='" + model + '\'' + '}';
    }
}
