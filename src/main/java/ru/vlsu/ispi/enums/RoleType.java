package ru.vlsu.ispi.enums;

public enum RoleType {
    User(1),
    Manager(2),
    Admin(3);
    private final int value;
    private RoleType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
