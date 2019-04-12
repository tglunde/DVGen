package com.tui.dwh.erdplus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Slot {
    /**
     "slotIndex": 0,
     "minimum": "",
     "maximum": "",
     "participation": "optional",
     "cardinality": "many",
     "role": "",
     "entityId": 5
     **/
    @JsonProperty("slotIndex")
    private int index;
    @JsonProperty("minimum")
    private String minimum;
    @JsonProperty("maximum")
    private String maximum;
    @JsonProperty("participation")
    private String participation;
    @JsonProperty("cardinality")
    private String cardinality;
    @JsonProperty("role")
    private String role;
    @JsonProperty("entityId")
    private int entityId;

    public Slot() {
    }

    @Override
    public String toString() {
        return "com.tui.dwh.erdplus.Slot{" +
                "index=" + index +
                ", minimum='" + minimum + '\'' +
                ", maximum='" + maximum + '\'' +
                ", participation='" + participation + '\'' +
                ", cardinality='" + cardinality + '\'' +
                ", role='" + role + '\'' +
                ", entityId=" + entityId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot)) return false;
        Slot slot = (Slot) o;
        return getIndex() == slot.getIndex() &&
                getEntityId() == slot.getEntityId() &&
                Objects.equals(getMinimum(), slot.getMinimum()) &&
                Objects.equals(getMaximum(), slot.getMaximum()) &&
                Objects.equals(getParticipation(), slot.getParticipation()) &&
                Objects.equals(getCardinality(), slot.getCardinality()) &&
                Objects.equals(getRole(), slot.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getEntityId());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
