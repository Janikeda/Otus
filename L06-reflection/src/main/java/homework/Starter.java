package homework;

public class Starter {

    public static void main(String[] args) {
        TestAnnotationProcessor<HomeWorkTest> testAnnotationProcessor = new TestAnnotationProcessor<>();
        Result result = testAnnotationProcessor.testAnnotationProcessor(HomeWorkTest.class);
        System.out.println(result.getResult());
    }
}
