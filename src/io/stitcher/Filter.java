package io.stitcher;

import java.util.Collection;

/**
 * Objects implementing this interface may receive type T objects and decide whether they fulfil
 * certain conditions or not. This may be useful to remove specific objects in a collection.
 *
 * @param <T> The type of elements to be filtered.
 */
public interface Filter<T> {

    /**
     * This method analyses an element and decides whether it should be removed from the sample or
     * not.
     * 
     * @param element Element to be analysed.
     * @return <tt>true</tt> if this element should be removed from the sample.
     */
    public boolean filter(T element);

    /**
     * This method receives a list of elements to be analysed and returns a <u>new</u> one in which
     * those that did not fulfil the required conditions have been removed.
     * 
     * @param elements List of elements to be filtered.
     * @return A <u>new</u> list containing only those objects which fulfil the filtering
     *         conditions.
     */
    public Collection<T> filter(Collection<T> elements);

}
