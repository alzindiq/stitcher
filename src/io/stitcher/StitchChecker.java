package io.stitcher;

/**
 * This helper interface allows comparing different objects to check their degree of similarity. It
 * is meant to be used in the <tt>Stitcher</tt> to compare base and candidate elements.
 * <tt>Filter</tt>s may be used to avoid comparisons between base elements and certain candidates
 * expected not to match it.
 *
 * @param <B> Base item as used in the <tt>Stitcher</tt>.
 * @param <C> Candidate item as used in the <tt>Stitcher</tt>.
 */
public interface StitchChecker<B, C> {

    /**
     * Compares the base element and candidate element to decide which is their degree of
     * similarity.
     * 
     * @param baseElement First object to be compared.
     * @param candidateElement Second object to be compared.
     * @return A float in the range <tt>[0.0, 1.0]</tt>. <tt>0.0</tt> implies that these objects are
     *         different. <tt>1.0</tt> implies that these objects are the same one. <tt>0.5</tt>
     *         means that no decision about their similarity could be made.
     */
    public double checkStitch(B baseElement, C candidateElement);

}
