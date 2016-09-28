package com.hp.hpl.stitcher.testutils;

import io.stitcher.StitchChecker;

public abstract class PolygonColourComparator implements StitchChecker<ColouredPolygon, ColouredPolygon> {

    @Override
    public double checkStitch(ColouredPolygon baseElement, ColouredPolygon candidateElement) {
        return (baseElement.getColour().equals(candidateElement.getColour()) ? 1.0 : 0.0);
    }

}
