package com.hp.hpl.stitcher;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.StopWatch;

import io.stitcher.WordIndex.CharFilterMode;
import io.stitcher.extras.CoraRecord;
import io.stitcher.extras.CoraRecordXMLParser;

public class CoraBenchmark {

    // VARIABLES -----------------------------------------------------------------------------------

    Map<String, CoraRecord> itemsById;
    WordIndex<CoraRecord, String> authorWordIndex, titleWordIndex, publicationWordIndex;

    // VARIABLES - END -----------------------------------------------------------------------------

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public CoraBenchmark() {
        // Create indexes
        itemsById = new HashMap<String, CoraRecord>();
    }

    // CONSTRUCTORS - END --------------------------------------------------------------------------

    // METHODS -------------------------------------------------------------------------------------

    public Collection<CoraRecord> retrieveRecordsFromFile(String path) {
        return new CoraRecordXMLParser().parseXmlFile(path);
    }

    public void printCoraRecordsToFile(Collection<CoraRecord> coll, String pathToFile) {
        Map<String, Integer> idCounter = new HashMap<String, Integer>();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToFile), "utf-8"))) {
            for (CoraRecord c : coll) {
                writer.write(String.format("[%s, %s] %s - %s\n", c.getId(), c.getPublicationId(), c.getAuthors(),
                        c.getTitle()));

                if (!idCounter.containsKey(c.getPublicationId())) {
                    idCounter.put(c.getPublicationId(), 0);
                }
                idCounter.put(c.getPublicationId(), idCounter.get(c.getPublicationId()) + 1);
            }

            writer.write(String.format("\n"));

            int total = 0, current;
            for (String s : idCounter.keySet()) {
                current = idCounter.get(s);
                writer.write(String.format("#(%s) = %d\n", s, current));
                total += current;
            }
            writer.write(String.format("\nTotal of records: %d\n", total));
        } catch (IOException ex) {
            // handle me
        }
    }

    // METHODS - END -------------------------------------------------------------------------------

    // HELPER METHODS ------------------------------------------------------------------------------

    private void generateIndexes(Collection<CoraRecord> coll) {

        Collection<String> stopWords = WordIndex.generateDefaultStopWords();

        // Index items
        coll.stream().forEach(record -> {
            itemsById.put(record.getId(), record);
        });

        // Index authors
        authorWordIndex = new WordIndex<CoraRecord, String>();
        authorWordIndex.addStopWords(Arrays.asList("and")); // To avoid meaningless matches!
        authorWordIndex.setMinimumWordLenght(2);
        authorWordIndex.setCharFilterMode(CharFilterMode.WHITE_LIST, WordIndex.LETTERS_AND_NUMBERS);
        authorWordIndex.index(coll, CoraRecord::getAuthors);

        // Index titles
        titleWordIndex = new WordIndex<CoraRecord, String>();
        titleWordIndex.addStopWords(stopWords);
        // titleWordIndex.addStopWords(Arrays.asList("learning")); // For testing...
        titleWordIndex.setCharFilterMode(CharFilterMode.WHITE_LIST, WordIndex.LETTERS_AND_NUMBERS);
        titleWordIndex.index(coll, CoraRecord::getTitle);

        // Index publication IDs
        publicationWordIndex = new WordIndex<CoraRecord, String>();
        publicationWordIndex.index(coll, CoraRecord::getPublicationId);
    }

    private Map<CoraRecord, Collection<CoraRecord>> generatePotentialStitches(Collection<CoraRecord> toBeStitched) {
        Map<CoraRecord, Collection<CoraRecord>> potentialStitches =
                new ConcurrentHashMap<CoraRecord, Collection<CoraRecord>>(toBeStitched.size());

        toBeStitched.parallelStream().forEach(record -> {
            potentialStitches.put(record, toCoraRecordCollection(findCandidates(record)));
        });

        return potentialStitches;
    }

    private Collection<String> findCandidates(CoraRecord record) {
        Collection<String> candidates = new TreeSet<String>();

        for (String token : authorWordIndex.filterTokens(record.getAuthors())) {
            candidates.addAll(authorWordIndex.getItems(token));
        }

        for (String token : titleWordIndex.filterTokens(record.getTitle())) {
            candidates.addAll(titleWordIndex.getItems(token));
        }

        return candidates;
    }

    private Collection<CoraRecord> toCoraRecordCollection(Collection<String> ids) {
        Collection<CoraRecord> records = ids.stream().map(id -> {
            return itemsById.get(id);
        }).collect(Collectors.toList());

        return records;
    }

    private int countPotentialStitches(Map<CoraRecord, Collection<CoraRecord>> potentialStitches) {
        return potentialStitches.keySet().stream().mapToInt(record -> {
            return potentialStitches.get(record).size();
        }).sum();
    }

    private void printIndex(WordIndex<CoraRecord, String> index, String headerFooter) {
        System.out.printf("*** %s: ***\n", headerFooter);
        index.printWordIndexInOrder(System.out);
        System.out.printf("*** %s end ***\n\n", headerFooter);
    }

    private void printPotentialStitches(Map<CoraRecord, Collection<CoraRecord>> potentialStitches) {
        System.out.printf("*** Potential stitches: ***\n");
        potentialStitches
                .keySet()
                .stream()
                .forEach(
                        record -> {
                            Collection<CoraRecord> candidates = potentialStitches.get(record);
                            System.out.printf(String.format("%s -> |%s| %s\n", record.getId(), candidates.size(),
                                    candidates.stream().map(nestedRecord -> {
                                        return nestedRecord.getId();
                                    }).collect(Collectors.toList())));
                        });
        System.out.printf("*** Potential stitches end ***\n\n");
    }

    public static void main(String args[]) {
        CoraBenchmark cora = new CoraBenchmark();
        StopWatch watch = new StopWatch();
        Map<CoraRecord, Collection<CoraRecord>> potentialStitches;

        Collection<CoraRecord> coll =
                cora.retrieveRecordsFromFile("C:\\Users\\user\\Documents\\Cora\\cora-all-id.xml");

        cora.generateIndexes(coll);

        // cora.printIndex(cora.authorWordIndex, "Author word index");
        cora.printIndex(cora.titleWordIndex, "Title word index");
        cora.printIndex(cora.publicationWordIndex, "Publication word index");


        watch.start();
        potentialStitches =
                cora.generatePotentialStitches(Arrays.asList(cora.itemsById.get("20"), cora.itemsById.get("43")));
        watch.stop();
        cora.printPotentialStitches(potentialStitches);
        System.out.printf("Number of calculated potential stitches: %d\n",
                cora.countPotentialStitches(potentialStitches));
        System.out.printf("Exec time: %s\n", watch.toString());
        System.out.println();

        watch.reset();
        watch.start();
        potentialStitches = cora.generatePotentialStitches(coll);
        watch.stop();
        System.out.printf("Number of calculated potential stitches: %d\n",
                cora.countPotentialStitches(potentialStitches));
        System.out.printf("Exec time: %s\n", watch.toString());
        System.out.println();

        System.out.println("End of program.");
    }

    // HELPER METHODS - END ------------------------------------------------------------------------

}
