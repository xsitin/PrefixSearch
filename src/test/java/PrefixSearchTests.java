import SearchDocument.PrefixSearchDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrefixSearchTests {
    @BeforeEach
    public void SetUp() {
        sut = new PrefixSearchDocument(1);
    }

    private PrefixSearchDocument sut;

    @Test
    public void SearchingBySelectedColumn() {
        var doc = List.of(Arrays.asList("some", "other"));

        sut.index(doc);

        Assertions.assertEquals(doc, sut.Search("so"));
        Assertions.assertEquals(Collections.emptyList(), sut.Search("ot"));
    }

    @Test
    public void ReturnOnlyWithPrefix() {
        var doc = List.of(Arrays.asList("a", "something"), Arrays.asList("b", "flag"), Arrays.asList("c", "something"));

        sut.index(doc);

        Assertions.assertEquals(List.of(
                Arrays.asList("b", "flag")),
                sut.Search("b")
        );
    }

    @Test
    public void ReturnOrderedByAscending() {
        var doc = List.of(Arrays.asList("bz", "something"), Arrays.asList("bb", "flag"), Arrays.asList("bj", "something"));

        sut.index(doc);

        Assertions.assertEquals(
                List.of(Arrays.asList("bb", "flag"), Arrays.asList("bj", "something"), Arrays.asList("bz", "something")),
                sut.Search("b")
        );
    }

    @Test
    public void ShouldReturnAll() {
        var doc = Arrays.asList(Arrays.asList("1", "s"), Arrays.asList("1", "sd"), Arrays.asList("2", "s"));

        sut.index(doc);

        Assertions.assertEquals(3, sut.GetAllRows().size());
    }

}
