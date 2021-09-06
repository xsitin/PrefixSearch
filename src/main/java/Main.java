import ConfigReader.Config;
import ConfigReader.YamlConfigReader;
import SearchDocument.BaseSearchDocument;
import SearchDocument.PrefixSearchDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        Config config;
        config = prepairConfig(args);

        BaseSearchDocument doc = InitSearchDoc(config);
        var scanner = new Scanner(System.in);
        while (true) {
            System.out.print("input prefix: ");
            var prefix = scanner.next();
            if (prefix.equals("exit"))
                return;
            var start = System.currentTimeMillis();
            var result = doc.Search(prefix);
            var elapsed = System.currentTimeMillis() - start;
            result.forEach(x -> System.out.println(String.join(" ", x)));
            System.out.println("elapsed " + elapsed + " ms");
            System.out.println("founded " + result.size() + " rows");
        }
    }

    private static BaseSearchDocument InitSearchDoc(Config config) throws IOException {
        System.out.println("Begin indexing document");

        BaseSearchDocument doc = new PrefixSearchDocument(config.getColumn());
        Collection<List<String>> prepearedLines = prepearDocLines(config);
        doc.index(prepearedLines);

        System.out.println("Finished document indexing");
        return doc;
    }

    private static Config prepairConfig(String[] args) throws Exception {
        Config config;
        try {
            config = YamlConfigReader.standartRead();

        } catch (IOException e) {
            System.out.println("error during reading settings.yaml");
            throw e;
        }
        if (args.length >= 1) config.setColumn(Integer.parseInt(args[0]));
        if (config.getColumn() == -1)
            throw new Exception("Select column in args or settings.yaml");
        return config;
    }

    private static List<List<String>> prepearDocLines(Config config) throws IOException {
        return Files.lines(config.getDataPath())
                .map(line ->
                        Arrays.stream(line.split("[\",]"))
                                .filter(val -> val.length() > 0)
                                .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
