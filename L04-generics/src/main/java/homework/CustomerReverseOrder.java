package homework;

import java.util.LinkedList;

public class CustomerReverseOrder {

    private final LinkedList<Customer> queue;

    public CustomerReverseOrder() {
        queue = new LinkedList<>();
    }

    public void add(Customer customer) {
        queue.push(customer);
    }

    public Customer take() {
        return queue.pop();
    }
}
