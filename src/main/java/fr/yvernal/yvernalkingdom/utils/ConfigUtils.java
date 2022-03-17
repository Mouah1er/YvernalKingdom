package fr.yvernal.yvernalkingdom.utils;

import fr.yvernal.yvernalkingdom.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {

    public static void writeToFile(String category, File file) {
        final URL localInputUrl = Main.class.getResource("/" + category + "/" + file.getName());

        if (localInputUrl == null) {
            throw new RuntimeException("File does not exist in items file !");
        }

        final StringBuilder stringBuilder = new StringBuilder();
        try {
            final FileSystem fileSystem = initFileSystem(localInputUrl.toURI());
            Files.readAllLines(Paths.get(localInputUrl.toURI())).forEach(s -> stringBuilder.append(s).append(System.lineSeparator()));
            fileSystem.close();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            final FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(stringBuilder.toString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileSystem initFileSystem(URI uri) throws IOException {
        try {
            return FileSystems.getFileSystem(uri);
        } catch (FileSystemNotFoundException e) {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            return FileSystems.newFileSystem(uri, env);
        }
    }
}
