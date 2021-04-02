package ru.otus;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.util.Set;

/**
 * To start the application: gradlew build java -jar ./L01-gradle/build/libs/gradleHelloWorld-0.1.jar
 *
 * To unzip the jar: unzip -l L01-gradle.jar unzip -l gradleHelloWorld-0.1.jar
 */
public class HelloOtus {

    private static final Set<String> VALUES = ImmutableSet.of("John", "Mike", "Jack", "Bob");

    public static void main(String... args) {
        print(Strings.commonPrefix("Cake", "Cat"));
        print(Strings.repeat("_", 20));
        VALUES.stream()
            .map(name -> Strings.padStart(name, 15, '.'))
            .forEach(HelloOtus::print);
    }

    private static void print(String value) {
        System.out.println(value);
    }
}
