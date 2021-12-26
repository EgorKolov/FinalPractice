package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CatalogueParser {
    private CatalogueParser() {
        
    }
    
    public static ArrayList<Product> Parse(String path) {
        var file = Paths.get(path);
        var result = new ArrayList<Product>();
        
        try (var stream = Files.lines(file, Charset.forName("Windows-1251"))) {
            stream.skip(1).forEach(s -> addProductTo(result, s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    private static void addProductTo(ArrayList<Product> list, String string) {
        var data = string.split(";");
        var product = new Product(data[0], data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]));
        list.add(product);
    }
}
