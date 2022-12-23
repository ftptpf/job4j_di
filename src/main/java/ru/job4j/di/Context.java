package ru.job4j.di;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Регистрация классов.
 * els - карта с объектами. В ней мы будем хранить проинициализированные объекты.
 */
public class Context {
    private Map<String, Object> els = new HashMap<>();

    /**
     * Метод регистрации классов.
     * Получаем все конструкторы класса, если их больше одного - мы не знаем как загружает этот класс и кидаем исключение.
     * Когда мы нашли конструктор, мы собираем аргументы этого конструктора и ищем уже зарегистрированные объекты,
     * чтобы внедрить их в конструктор.
     * Последний этап - это создание объекта и добавление его в карту.
     * @param cl
     */
    public void reg(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw new IllegalStateException("Class has multiple constructors : " + cl.getCanonicalName());
        }
        Constructor con = constructors[0];
        List<Object> args = new ArrayList<>();
        for (Class arg : con.getParameterTypes()) {
            if (!els.containsKey(arg.getCanonicalName())) {
                throw new IllegalStateException("Object doesn't found in context : " + arg.getCanonicalName());
            }
            args.add(els.get(arg.getCanonicalName()));
        }
        try {
            els.put(cl.getCanonicalName(), con.newInstance(args.toArray()));
        } catch (Exception e) {
            throw new IllegalStateException("Can't create an instance of : " + cl.getCanonicalName(), e);
        }
    }

    /**
     * Метод возвращает проинициализированный объект
     * @param inst
     * @return инициализированный объект
     * @param <T>
     */
    public <T> T get(Class<T> inst) {
        return (T) els.get(inst.getCanonicalName());
    }
}
