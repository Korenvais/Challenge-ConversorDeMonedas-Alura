package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class OutputFileGenerator {
    public static void saveAsJsonFile(Object history, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writter = new FileWriter(fileName);
        writter.write(gson.toJson(history));
        writter.close();
    }
}
