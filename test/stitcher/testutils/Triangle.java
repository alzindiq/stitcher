package com.hp.hpl.stitcher.testutils;

class Triangle extends ColouredPolygon {

    public Triangle(final String id) {
        super(id);
    }

    @Override
    public Triangle setId(String id) {
        return (Triangle) super.setId(id);
    }

    @Override
    public Triangle setArea(double area) {
        this.area = area;
        this.refLenght = Math.sqrt(2 * area);
        return this;
    }

    @Override
    public Triangle setRefLenght(double refLenght) {
        this.refLenght = refLenght;
        this.area = refLenght * refLenght / 2;
        return this;
    }

    @Override
    public double getPerimeter() {
        return (1 + Math.sqrt(5.0)) * refLenght;
    }

    @Override
    public Triangle setPerimeter(double perimeter) {
        setRefLenght(perimeter / (1 + Math.sqrt(5.0)));
        return this;
    }

    @Override
    public String kind() {
        return "Triangle";
    }
}
