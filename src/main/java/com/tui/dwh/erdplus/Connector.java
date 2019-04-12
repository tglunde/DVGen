package com.tui.dwh.erdplus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Connector {
    private String type;
    private int source;
    private int destination;
    @JsonProperty("details")
    private Detail detail;

    public Connector() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connector)) return false;
        Connector connector = (Connector) o;
        return getSource() == connector.getSource() &&
                getDestination() == connector.getDestination() &&
                getType().equals(connector.getType()) &&
                getDetail().equals(connector.getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetail());
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
