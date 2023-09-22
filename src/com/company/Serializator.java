package com.company;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static com.company.Main.Journal;

class Serializer {
     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    void Serialization() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("Введите путь к файлу в следующем формате (D:\\test.json)");
        String path = reader.readLine();
        try {
            File file=new File(path);
            mapper.writeValue((file),Journal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
     void Deserialization() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Введите путь к файлу  в следующем формате (D:\\test.json)");
        String path = reader.readLine();
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("Файл не найден.");
                return;
            }
            TypeReference<HashMap<Integer, Student>> typeReference = new TypeReference<>() {
            };
            Journal = mapper.readValue(file, typeReference);
            System.out.println("Данные успешно загружены из файла.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла: " + e.getMessage());
        }
    }
}