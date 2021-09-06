package SearchDocument;

import com.google.common.collect.TreeMultimap;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PrefixSearchDocument extends BaseSearchDocument {

    private final int selectedColIndex;

    private final TreeSet<String> keys;
    private final TreeMultimap<String, List<String>> cache;

    public int GetSelectedColumn() {
        return selectedColIndex + 1;
    }

    public PrefixSearchDocument(int selectedColumn) {
        keys = new TreeSet<>(Comparator.comparing(String::toString));
        selectedColIndex = selectedColumn - 1;
        cache = TreeMultimap.create(Comparator.comparing(String::toString), Comparator.comparingInt(List::hashCode));
    }


    public void index(Collection<List<String>> preparedRows) {
        for (var x : preparedRows) {
            var key = x.get(selectedColIndex);
            cache.put(key, x);
            keys.add(key);
        }
    }

    public Collection<List<String>> GetAllRows() {
        return cache.values();
    }

    public Collection<Collection<String>> Search(String value) {
        var lastChar = value.charAt(value.length() - 1);
        var increasedValue = value.substring(0, value.length() - 1) + Character.toString(lastChar + 1);
        return keys.subSet(value, increasedValue).stream()
                .map(cache::get)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
