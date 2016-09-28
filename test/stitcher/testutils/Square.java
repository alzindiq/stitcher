package com.hp.hpl.stitcher.testutils;

class Square extends ColouredPolygon {

    public Square(final String id) {
        super(id);
    }

    @Override
    public Square setId(String id) {
        return (Square) super.setId(id);
    }

    @Override
    public Square setArea(double area) {
        this.area = area;
        this.refLenght = Math.sqrt(area);
        return this;
    }

    @Override
    public Square setRefLenght(double refLenght) {
        this.refLenght = refLenght;
        this.area = refLenght * refLenght;
        return this;
    }

    @Override
    public double getPerimeter() {
        return 4 * refLenght;
    }

    @Override
    public Square setPerimeter(double perimeter) {
        setRefLenght(perimeter / 4);
        return this;
    }

    @Override
    public String kind() {
        return "Square";
    }
}
