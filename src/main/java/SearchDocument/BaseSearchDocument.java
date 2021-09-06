package SearchDocument;

import java.util.Collection;
import java.util.List;

public abstract class BaseSearchDocument {
    public abstract void index(Collection<List<String>> preparedRows);

    public abstract Collection<List<String>> GetAllRows();

    public abstract Collection<Collection<String>> Search(String value);
}
