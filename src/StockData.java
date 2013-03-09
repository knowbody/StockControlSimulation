/*
 * Author: Mateusz Zatorski
 * Student ID: 000738254
 *
*/
import java.util.HashMap;
import java.util.Map;


public class StockData {

    private static class Item {
        Item(String n, double p, int q) {
            name = n;
            price = p;
            quantity = q;
            
        }

        // get methods
      
        public String getName() {
        	return name; 
        }
      
        public double getPrice() { 
        	return price; 
        }
        
        public int getQuantity() { 
        	return quantity; 
        }
        

        // instance variables 
        private String name;
        private double price;
        private int quantity;
        
    }

    // with a Map you use put to insert a key, value pair 
    // and get(key) to retrieve the value associated with a key
    // You don't need to understand how this works!
    private static Map<String, Item> stock = new HashMap<String, Item>();

    static {
        // if you want to have extra stock items, put them in here
        // use the same style - keys should be Strings
        stock.put("00", new Item("Bath towel", 5.50, 10));
        stock.put("11", new Item("Plebney light", 20.00, 5));
        stock.put("22", new Item("Gorilla suit", 30.00, 7));
        stock.put("33", new Item("Whizz games console", 50.00, 8));
        stock.put("44", new Item("Oven", 200.00, 4));
        stock.put("69", new Item("Sex", 1000.0, 1));
    }

    public static String getName(String key) {
        Item item = stock.get(key);
        if (item == null) return null; // null means no such item
        else return item.getName();
    }

    public static double getPrice(String key) {
        Item item = stock.get(key);
        if (item == null) return -1.0; // negative price means no such item
        else return item.getPrice();
    }

    public static int getQuantity(String key) {
        Item item = stock.get(key);
        if (item == null) return -1; // negative quantity means no such item
        else return item.getQuantity();
    }

	// update stock levels
	// extra is +ve if adding stock
	// extra is -ve if selling stock
    public static void update(String key, int extra) {
        Item item = stock.get(key);
        if (item != null) item.quantity += extra;
    }

	 public static void close() {
        // Does nothing for this static version.
        // Write a statement to close the database when you are using one
    }
	
}
