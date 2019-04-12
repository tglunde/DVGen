package com.tui.dwh.erdplus;

import java.util.List;
import java.util.Objects;

public class Model {
    private String version;
    private String www;
    private int width;
    private int height;
    private List<Shape> shapes;
    private List<Connector> connectors;

    public Model(String version, String www, int width, int height, List<Shape> shapes, List<Connector> connectors) {
        this.version = version;
        this.www = www;
        this.width = width;
        this.height = height;
        this.shapes = shapes;
        this.connectors = connectors;
    }

    public Model() {
    }

    @Override
    public String toString() {
        return "com.tui.dwh.erdplus.Model{" +
                "version='" + version + '\'' +
                ", www='" + www + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", shapes=" + shapes +
                ", connectors=" + connectors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model model = (Model) o;
        return getWidth() == model.getWidth() &&
                getHeight() == model.getHeight() &&
                Objects.equals(getVersion(), model.getVersion()) &&
                Objects.equals(getWww(), model.getWww()) &&
                Objects.equals(getShapes(), model.getShapes()) &&
                Objects.equals(getConnectors(), model.getConnectors());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }
}
