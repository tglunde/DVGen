package com.tui.dwh.erdplus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Shape {
    private String type;
    @JsonProperty("details")
    private Detail detail;

    public Shape() {
    }

    @Override
    public String toString() {
        return "com.tui.dwh.erdplus.Shape{" +
                "type='" + type + '\'' +
                ", detail=" + detail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape)) return false;
        Shape shape = (Shape) o;
        return Objects.equals(getType(), shape.getType()) &&
                getDetail().equals(shape.getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetail());
    }

    public Shape(String type) {
        this.type = type;
    }

    public Shape(String type, Detail detail) {
        this.type = type;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
