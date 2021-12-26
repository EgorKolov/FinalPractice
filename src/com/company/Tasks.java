package com.company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Tasks {
    public static void task1() {
        try {
            var dbm = DatabaseManager.connect("Catalogue.db");
            var products = dbm.selectAllProducts();
            var groups = Processing.getGroupsOf(products);
            
            var current = new ArrayList<>(groups.keySet().stream().toList());
            var selected = new HashMap<String, ArrayList<Product>>();
            Collections.shuffle(current);
            current.stream()
                   .limit(10)
                   .forEach(s -> selected.put(s, groups.get(s)));
            
            AveragePriceGraphing.makeGraph(selected, "Задача №1");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void task2() {
        try {
            var dbm = DatabaseManager.connect("Catalogue.db");
            var infraredLamps =  dbm.selectAllFromProducts("WHERE Name LIKE 'Лампа инфракрасная%'");
    
            System.out.println("Задача №2");
            System.out.println("Средняя стоимость инфракрасных ламп: " + Processing.getAveragePrice(infraredLamps));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void task3() {
        try {
            var dbm = DatabaseManager.connect("Catalogue.db");
            var topFive = dbm.selectTopFromProducts(5, "WHERE InStock > 10 ORDER BY Price DESC");
            
            System.out.println("Задача №3");
            System.out.println("5 самых дорогих товаров, которых на складе более 10: ");
            topFive.forEach(System.out::println);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

