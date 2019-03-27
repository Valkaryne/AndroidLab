package com.epam.utils;

public abstract class Node {

    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        Node node = (Node) obj;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
