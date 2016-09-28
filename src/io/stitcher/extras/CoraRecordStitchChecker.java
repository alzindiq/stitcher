package io.stitcher.extras;

import java.util.Set;

import io.stitcher.StitchChecker;

public class CoraRecordStitchChecker implements StitchChecker<CoraRecord, CoraRecord> {

    private double authorsWeight, titleWeight;

    public CoraRecordStitchChecker() {
        authorsWeight = titleWeight = 0.5;
    }

    public double getAuthorsWeight() {
        return authorsWeight;
    }

    public double getTitleWeight() {
        return titleWeight;
    }
    
    public void setWeights(double authors, double titles) {
        if (authors + titles != 1.0) {
            throw new IllegalArgumentException("Weights should sum up to one");
        }
        
        authorsWeight = authors;
        titleWeight = titles;
    }

    @Override
    public double checkStitch(CoraRecord baseElement, CoraRecord candidateElement) {

        // Calculate author similarity
        double authorsJaccard =
                jaccardSimilarity(baseElement.getAuthorsShingles(), candidateElement.getAuthorsShingles());

        // Calculate title similarity
        double titleJaccard = jaccardSimilarity(baseElement.getTitleShingles(), candidateElement.getTitleShingles());

        return authorsWeight * authorsJaccard + titleWeight + titleJaccard;
    }

    public static <T> double jaccardSimilarity(Set<T> set1, Set<T> set2) {
        if (set1.size() == 0 || set2.size() == 0) {
            return 0.0;
        }

        double intersectionSize = SetOperations.intersection(set1, set2).size();
        double unionSize = SetOperations.union(set1, set2).size();

        return intersectionSize / unionSize;
    }
}
