package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Processing {
    public static HashMap<String, ArrayList<Product>> getGroupsOf(ArrayList<Product> selection) {
        var result = new HashMap<String, ArrayList<Product>>();
        
        for (var product : selection) {
            var string = product.toString();
            var groupName = string.substring(string.indexOf('\'') + 1, string.indexOf(' ')).replace(",", "");
            if (!result.containsKey(groupName))
                result.put(groupName, new ArrayList<>());
            result.get(groupName).add(product);
        }
        result.keySet()
                .removeIf(key -> result.get(key).size() == 1 ||
                        key.endsWith("ая") || key.endsWith("яя") ||
                        key.endsWith("ый") || key.endsWith("ий") || key.endsWith("ой") ||
                        key.endsWith("ое") || key.endsWith("ее") ||
                        key.endsWith("ие") || key.endsWith("ые"));
        
        return result;
    }
    
    public static double getAveragePrice(ArrayList<Product> selection) {
        var aggregatedPrice = 0.0;
        var amount = 0;
        for (var product : selection) {
            aggregatedPrice += product.getPrice() * product.getInStock();
            amount += product.getInStock();
        }
        return aggregatedPrice / amount;
    }
    
    public static class Pair<L, R> {
        private final L left;
        private final R right;
        
        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
        
        public L getLeft() {
            return left;
        }
        
        public R getRight() {
            return right;
        }
    }
}
