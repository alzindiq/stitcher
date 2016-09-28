package io.stitcher;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import io.stitcher.extras.Verifier;
import no.priv.garshol.duke.DataSource;
import no.priv.garshol.duke.Logger;
import no.priv.garshol.duke.Record;
import no.priv.garshol.duke.RecordImpl;
import no.priv.garshol.duke.RecordIterator;

import com.hp.hpl.loom.adapter.hpcloud.item.HpCloudItem;
import com.hp.hpl.loom.adapter.hpcloud.item.HpCloudItemAttributes;

/**
 * <tt>DataSource</tt> implementation to be used with Duke which provides <tt>Record</tt>s
 * containing properties retrieved from <tt>HpCloudItem</tt> objects.
 *
 * @param <T> Child class of <tt>HpCloudItem</tt> whose objects are going to be provided to this
 *        <tt>DataSource</tt>.
 */
public class HpCloudDataSource<T extends HpCloudItem<? extends HpCloudItemAttributes>> implements DataSource {

    // VARIABLES -----------------------------------------------------------------------------------
    protected Collection<T> items;
    protected Map<String, String> propertiesInfo;
    protected DukeStitcherRecordIterator<T> it;

    // VARIABLES - END -----------------------------------------------------------------------------

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructs an <tt>HpCloudDataSource</tt>.
     * <p>
     *
     * <b>NOTE</b>: One entry in propertiesInfo should be: <tt>&lt;"ID","_id"&gt;</tt>
     *
     * @param items Items which will be processed and returned as <tt>Record</tt>s when the
     *        <tt>getRecords()</tt> method is called.
     * @param propertiesInfo <tt>Map</tt> which contains an entry for each property that is going to
     *        be considered by Duke. For each property, this <tt>Map</tt> should contain a
     *        <tt>String</tt> which will denote which attribute from the corresponding
     *        <tt>HpCloudItem</tt> is to be used as its value.
     */
    public HpCloudDataSource(final Collection<T> items, final Map<String, String> propertiesInfo) {
        // Verify arguments
        Verifier.illegalArgumentIfNull(items, propertiesInfo);
        Verifier.isTrue(propertiesInfo.containsKey("ID"));
        Verifier.isTrue(propertiesInfo.get("ID").equals("_id"));

        this.items = items;
        this.propertiesInfo = propertiesInfo;
    }

    // CONSTRUCTORS - END --------------------------------------------------------------------------

    // METHODS -------------------------------------------------------------------------------------

    @Override
    public RecordIterator getRecords() {
        it = new DukeStitcherRecordIterator<T>();

        // Sequential implementation
        // for (T item : items) {
        // it.addRecord(convertToRecord(item));
        // }

        // Parallel implementation
        Collection<Record> records =
                items.parallelStream().map(item -> convertToRecord(item)).collect(Collectors.toList());
        it.addRecords(records);

        return it.start();
    }

    @Override
    public void setLogger(final Logger logger) {
        // Ignore any logger
    }

    /**
     * Helper method which receives an <tt>HpCloudItem</tt> and creates a new <tt>Record</tt> to be
     * used by Duke.
     *
     * @param item The object where the properties' values are going to be read from.
     * @return A <tt>Record</tt> containing all the properties specified in the constructor of this
     *         class.
     */
    protected Record convertToRecord(final T item) {
        RecordImpl newRecord = new RecordImpl();

        // This method is called from a parallelised one. Hence, there is no need for this one to be
        // parallelised.
        for (String property : propertiesInfo.keySet()) {
            String value = item.getCore().getAttributeAsString(propertiesInfo.get(property));
            newRecord.addValue(property, value);
        }

        return newRecord;
    }

    // METHODS - END -------------------------------------------------------------------------------

}
