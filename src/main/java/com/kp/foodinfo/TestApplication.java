package com.kp.foodinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Util {
    public void test();

//    public void test2();
}

class UtilImpl implements Util {
    @Override
    public void test() {

    }
}

interface Gogo {
    public String getName(int a);
}

public class TestApplication {
    public static void main(String[] args) {
        Util util = new Util() {
            @Override
            public void test() {

            }
        };

        Util util2 = () -> System.out.println("d");

        Gogo gogo1 = new Gogo() {
            @Override
            public String getName(int a) {
                System.out.println(a);
                return "ddd";
            }
        };

        Gogo gogo = (a) -> {
            System.out.println(a);
            return "ddd";
        };

        List<String> names = new ArrayList<>();

        List<String> newNames = names
                .stream()
                .map(s -> s + "님")
                .collect(Collectors.toList());

        names
                .stream()
                .map(s -> s + "님")
                .filter(s -> s.contains("D"))
                .map(s -> s + "*")
                .collect(Collectors.toList());

        map(new Gogo() {
            @Override
            public String getName(int a) {
                return null;
            }
        });

        map(a -> "ss");

    }

    public static void map(Gogo gogo) {

    }
}
