package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map;


    public CustomerService() {
        map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return copyEntry(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Entry<Customer, String> higherEntry = map.higherEntry(customer);
        if (higherEntry == null) {
            return null;
        }
        return copyEntry(higherEntry);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Entry<Customer, String> entry) {
        Customer key = entry.getKey();
        Customer customerCopy = new Customer(key.getId(), key.getName(), key.getScores());
        return Map.entry(customerCopy, entry.getValue());
    }
}
