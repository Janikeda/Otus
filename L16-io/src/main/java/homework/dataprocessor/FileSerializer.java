package homework.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final Gson gson;
    private final String fileName;

    public FileSerializer(String fileName) {
        this.gson = new GsonBuilder().create();
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
