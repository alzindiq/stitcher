package io.stitcher;

import java.util.Collection;
import java.util.Map;

public interface SelectiveStitcher<B, C> extends Stitcher<B, C> {

    /**
     * Given a list of potential stitches (for each base element, a collection of candidate elements
     * is provided), verifies which of them are actual stitches. All the provided elements will be
     * checked against the filters (base and candidate) that this <tt>Stitcher</tt> contains.
     * 
     * @param potentialStitches A map of base elements and collections of candidate elements which
     *        may potentially be stitched.
     * @return All the verified stitches from the input map.
     */
    public Map<B, Collection<C>> stitch(Map<B, Collection<C>> potentialStitches);

}
