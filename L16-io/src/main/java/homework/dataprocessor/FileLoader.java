package homework.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import homework.model.Measurement;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileLoader implements Loader {

    private final Gson gson;
    private final String fileName;
    private final ClassLoader classLoader;

    public FileLoader(String fileName) {
        this.classLoader = getClass().getClassLoader();
        this.gson = new GsonBuilder().create();
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try (var inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(fileName));
            var reader = new InputStreamReader(inputStream)) {

            Measurement[] result = gson.fromJson(reader, Measurement[].class);
            return Arrays.asList(result);

        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
