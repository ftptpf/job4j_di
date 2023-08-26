package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.di");
        context.refresh();

        Store store = context.getBean(Store.class);
        store.add("Dmitriy Dmitriev");
        Store another = context.getBean(Store.class);
        another.getAll().forEach(System.out::println);

        StartUI ui = context.getBean(StartUI.class);
        ui.add("Petr Arsentev");
        ui.add("Ivan ivanov");
        ui.print();
/*        ui.askStr("Some text from ConsoleInput. Please press Enter.");
        StartUI uiOther = context.getBean(StartUI.class);
        uiOther.add("Sergey Sergeev");
        uiOther.print();*/
    }
}
