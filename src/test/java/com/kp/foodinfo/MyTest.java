package com.kp.foodinfo;

import com.kp.foodinfo.service.BrandMenuKindService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;

class MyExecutable implements Executable {
    @Override
    public void execute() throws Throwable {
        Integer.parseInt("3");
    }
}

class MySuperExecutable extends MyExecutable {

}


public class MyTest {
    private int age = 0;

    @BeforeEach
    public void beforeEach() {
        System.out.println("start!~!");
        age = 0;
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("create text file....");
    }

    @AfterEach
    public void afterEach() {
        Class<BrandMenuKindService> brandMenuKindServiceClass = BrandMenuKindService.class;
        Field[] declaredFields = brandMenuKindServiceClass.getDeclaredFields();

        for (Field field : declaredFields) {
            field.getDeclaredAnnotations();
            // field.set
        }

        brandMenuKindServiceClass.getDeclaredMethods();
    }

    @AfterAll
    public static void afterAll() {

    }

    @Test
    void test1() {
        System.out.println(1);
        age = 10;
    }

    @Test
    void test2() {
        Executable executable1 = new MyExecutable();

        Executable executable2 = new Executable() {
            @Override
            public void execute() throws Throwable {

            }
        };

        Executable executable = () -> {
            Integer.parseInt("3");
        };

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Integer.parseInt("3");
        });

        MyExecutable myExecutable = new MyExecutable() {
            @Override
            public void execute() throws Throwable {
                System.out.println("zzzz");
            }
        };

        Assertions.assertThrows(IllegalArgumentException.class, new MyExecutable());
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Integer.parseInt("3");
            }
        });
    }

    @Test
    void test3() {
        System.out.println(3);
    }
}
