package com.hp.hpl.stitcher.testutils;

class Circle extends ColouredPolygon {

    public Circle(final String id) {
        super(id);
    }

    @Override
    public Circle setId(String id) {
        return (Circle) super.setId(id);
    }

    @Override
    public Circle setArea(double area) {
        this.area = area;
        this.refLenght = Math.sqrt(area / Math.PI);
        return this;
    }

    @Override
    public Circle setRefLenght(double refLenght) {
        this.refLenght = refLenght;
        this.area = Math.PI * refLenght * refLenght;
        return this;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * refLenght;
    }

    @Override
    public Circle setPerimeter(double perimeter) {
        setRefLenght(perimeter / (2 * Math.PI));
        return this;
    }

    @Override
    public String kind() {
        return "Circle";
    }
}
