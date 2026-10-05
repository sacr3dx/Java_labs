package com.coffeevan.io;

import java.util.List;

public final class CoffeeRecord {

    private final int lineNumber;
    private final String type;
    private final List<String> fields;

    public CoffeeRecord(int lineNumber, String type, List<String> fields) {
        this.lineNumber = lineNumber;
        this.type = type;
        this.fields = fields;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getType() {
        return type;
    }

    /** Возвращает поле по индексу или null, если такого поля нет в строке. */
    public String getField(int index) {
        return index >= 0 && index < fields.size() ? fields.get(index) : null;
    }

    public int getFieldCount() {
        return fields.size();
    }
}
