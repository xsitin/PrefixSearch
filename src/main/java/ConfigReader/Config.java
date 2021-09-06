package ConfigReader;

import java.nio.file.Path;

public class Config {

    private String path;
    private int Column = -1;


    public Path getDataPath() {
        return path != null && path.length() > 0 ? Path.of(path) : Path.of("./airports.dat");
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getColumn() {
        return Column;
    }

    public void setColumn(int column) {
        if (column < 1 || column > 13) throw new IllegalArgumentException("Selected column should be between 1 and 13");
        Column = column;
    }
}
