package com.tui.dwh.erdplus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Detail {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("text")
    private String text;
    @JsonProperty("isIdentifying")
    private boolean isIdentifying = false;
    @JsonProperty("isDerived")
    private boolean isDerived = false;
    @JsonProperty("isMultivalued")
    private boolean isMultivalued = false;
    @JsonProperty("isOptional")
    private boolean isOptional = false;
    @JsonProperty("isComposite")
    private boolean isComposite = false;
    @JsonProperty("isUnique")
    private boolean isUnique = false;
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("height")
    private int height;
    @JsonProperty("width")
    private int width;
    private int slotIndex;
    @JsonProperty("slots")
    private List<Slot> slots;

    public Detail() {
    }

    public Detail(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "com.tui.dwh.erdplus.Detail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", isIdentifying=" + isIdentifying +
                ", isDerived=" + isDerived +
                ", isMultivalued=" + isMultivalued +
                ", isOptional=" + isOptional +
                ", isComposite=" + isComposite +
                ", isUnique=" + isUnique +
                ", x=" + x +
                ", y=" + y +
                ", height=" + height +
                ", width=" + width +
                ", slotIndex=" + slotIndex +
                ", slots=" + slots +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Detail)) return false;
        Detail detail = (Detail) o;
        return getId() == detail.getId() &&
                isIdentifying() == detail.isIdentifying() &&
                isDerived() == detail.isDerived() &&
                isMultivalued() == detail.isMultivalued() &&
                isOptional() == detail.isOptional() &&
                isComposite() == detail.isComposite() &&
                isUnique() == detail.isUnique() &&
                getX() == detail.getX() &&
                getY() == detail.getY() &&
                getHeight() == detail.getHeight() &&
                getWidth() == detail.getWidth() &&
                getSlotIndex() == detail.getSlotIndex() &&
                getName().equals(detail.getName()) &&
                getType().equals(detail.getType()) &&
                Objects.equals(getText(), detail.getText()) &&
                getSlots().equals(detail.getSlots());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public void setComposite(boolean composite) {
        isComposite = composite;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isIdentifying() {
        return isIdentifying;
    }

    public void setIdentifying(boolean identifying) {
        isIdentifying = identifying;
    }

    public boolean isDerived() {
        return isDerived;
    }

    public void setDerived(boolean derived) {
        isDerived = derived;
    }

    public boolean isMultivalued() {
        return isMultivalued;
    }

    public void setMultivalued(boolean multivalued) {
        isMultivalued = multivalued;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
