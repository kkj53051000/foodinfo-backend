package com.kp.foodinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAutowired {

}

class PostController {
    // @MyAutowired
    // @Resource
    @Autowired
    private PostRepository postRepository;

    // @PostConstruct
    public void init() {

    }

    public void print() {
        System.out.println(postRepository);
    }
}

class PostRepository {

}

////@Builder
//@ToString
//class Event {
//
//    @MyAutowired
//    @Autowired
//    private String name = "dooli";
//
//    public Long age = 5L;
//
///*    public Event(String name, Long age) {
//        System.out.println("constructor");
//        this.name = name;
//        this.age = age;
//    }*/
//}

public class BuilderTest {
    @Test
    void test() throws IllegalAccessException {
        PostController postController = new PostController();
        Class<PostController> postControllerClass = PostController.class;


        postController.print();

        for (Field field : postControllerClass.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof MyAutowired || annotation instanceof Resource) {
                    field.getName();
                    field.setAccessible(true);
                    field.set(postController, new PostRepository());
                }
            }
        }

//        postControllerClass.getDeclaredMethods()[0].invoke()
//        postController.print();


//        Class<Event> eventClass = Event.class;
//
//        Event event = new Event();
//
//        for(Field field: eventClass.getDeclaredFields()) {
//            for(Annotation annotation: field.getDeclaredAnnotations()) {
//                if(annotation instanceof Autowired) {
//                    field.setAccessible(true);
//                    // field.set();
//                }
//            }
//            field.setAccessible(true);
//            System.out.println(field);
//            System.out.println(field.get(event));
//
//            if(field.getName().equals("name")) {
//                field.set(event, "mic");
//            }
//        }

//        System.out.println(event);

        // Event event = new Event();


//        Event event = Event.builder()
//                .name("dooli")
//                .age(5L)
//                .build();
//
//        System.out.println(event);
    }
}
