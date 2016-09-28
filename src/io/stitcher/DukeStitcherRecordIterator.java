package io.stitcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import no.priv.garshol.duke.Record;
import no.priv.garshol.duke.RecordIterator;

public class DukeStitcherRecordIterator<T> extends RecordIterator {

    private Collection<Record> records;
    private Iterator<Record> it;

    public DukeStitcherRecordIterator() {
        records = new ArrayList<Record>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public void addRecords(Collection<Record> recordsToAdd) {
        records.addAll(recordsToAdd);
    }

    public DukeStitcherRecordIterator<T> start() {
        it = records.iterator();
        return this;
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public Record next() {
        return it.next();
    }

}
