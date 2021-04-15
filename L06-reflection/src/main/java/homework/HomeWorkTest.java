package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;
import java.util.Objects;

public class HomeWorkTest {

    private String data;

    @Before(order = 1)
    public void setUp() {
        System.out.println("----------setUp----------");
        data = "" + this.hashCode();
    }

    @Before(order = 2)
    public void setUp2() {
        System.out.println("Unique Data is: " + data);
    }

    @After(order = 1)
    public void afterEach() {
        System.out.println("--------afterEach--------");
        data = null;
    }

    @After(order = 2)
    public void afterEach2() {
        System.out.println("Data should be null: " + Objects.isNull(data) + "\n");
    }

    @Test
    public void firstTest() {
        System.out.println("FirstTest is completed");
        //throw new RuntimeException();
    }

    @Test
    public void secondTest() {
        System.out.println("SecondTest is completed");
    }

    @Test
    public void thirdTest() {
        System.out.println("ThirdTest is completed");
    }
}
