package com.hp.hpl.stitcher.testutils;

import java.util.function.ToDoubleFunction;

import io.stitcher.StitchChecker;

public abstract class PolygonMeasureComparator implements StitchChecker<ColouredPolygon, ColouredPolygon> {

    private ToDoubleFunction<ColouredPolygon> function;
    private double delta;

    public PolygonMeasureComparator(ToDoubleFunction<ColouredPolygon> function, double delta) {
        this.function = function;
        this.delta = delta;
    }

    @Override
    public double checkStitch(ColouredPolygon baseElement, ColouredPolygon candidateElement) {
        return (Math.abs(function.applyAsDouble(baseElement) - function.applyAsDouble(candidateElement)) <= delta ? 1.0
                : 0.0);
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public static double getArea(ColouredPolygon p) {
        return p.getArea();
    }

    public static double getPerimeter(ColouredPolygon p) {
        return p.getPerimeter();
    }

    public static double getRefLength(ColouredPolygon p) {
        return p.getRefLenght();
    }
}
