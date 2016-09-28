package io.stitcher;

import java.util.Collection;
import java.util.Map;

public interface IncrementalStitcher<T extends Identifiable<String>> {

    public SelectiveStitcher<T, T> getStitcherCore();

    public Map<T, Collection<T>> increment(Collection<T> newItems, Collection<T> updatedItems,
            Collection<T> deletedItems);

    public Map<T, Collection<T>> increment(Collection<T> newItems, Collection<T> updatedItems,
            Collection<T> deletedItems, Map<T, Collection<T>> potentialStitches);

    public Map<T, Collection<T>> getAccumulatedStitches();

    public Map<String, Collection<String>> getAccumulatedStitchesById();

}
