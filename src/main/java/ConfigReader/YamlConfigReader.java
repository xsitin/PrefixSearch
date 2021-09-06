package ConfigReader;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlConfigReader {

    public static Config standartRead() throws IOException {
        var path =Path.of("src/main/java/settings.yaml");
        path = Files.exists(path)?path:Path.of("./settings.yaml");
        return ReadFrom(path);
    }

    public static Config ReadFrom(Path path) throws IOException {
        if (!Files.exists(path))
            return new Config();
        var yaml = new Yaml();
        String config;
        config = Files.readString(path);

        return yaml.loadAs(config, Config.class);
    }
}
