package com.hp.hpl.stitcher.testutils;

import io.stitcher.Identifiable;

abstract class ColouredPolygon implements Identifiable<String> {

    protected String id;
    protected Colour colour;
    protected double area, refLenght;

    public ColouredPolygon(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public ColouredPolygon setId(String id) {
        this.id = id;
        return this;
    }

    public Colour getColour() {
        return colour;
    }

    public ColouredPolygon setColour(Colour colour) {
        this.colour = colour;
        return this;
    }

    public double getArea() {
        return area;
    }

    public double getRefLenght() {
        return refLenght;
    }

    @Override
    public String toString() {
        return getId();
    }

    public abstract ColouredPolygon setArea(double area);

    public abstract ColouredPolygon setRefLenght(double refLenght);

    public abstract double getPerimeter();

    public abstract ColouredPolygon setPerimeter(double perimeter);

    public abstract String kind();
}
