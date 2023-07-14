package ru.job4j.cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class CacheCas {
    private final Map<Integer, BaseDataCas> memory = new ConcurrentHashMap<>();

    /**
     putIfAbsent() выполняет методы сравнения и вставки, только делает это атомарно
     */
    public boolean add(BaseDataCas model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * computeIfPresent(key, BiFunction) производит замену в мапе по ключу
     * заменяемого(обновляемого) объекта а так же принемает функциональный
     * интерфейс с логикой/проверкой и возвращаемым результатом, который и
     * заменяет исходный объект.
     */
    public boolean update(BaseDataCas model) {
        BiFunction<Integer, BaseDataCas, BaseDataCas> biFunction = (key, base) -> {
            if (model.getVersion() != base.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            BaseDataCas newBase = new BaseDataCas(key, base.getVersion() + 1);
            newBase.setName(model.getName());
            return newBase;
        };
        return memory.computeIfPresent(model.getId(), biFunction) != null;
    }

    /**
     * remove(Obj key, Obj val) удаляет запись для указанного ключа только в том случае,
     * если она в данный момент сопоставлена указанному значению. (атомарный)
     */
    public void delete(BaseDataCas model) {
        memory.remove(model.getId(), model);
    }

    public BaseDataCas getModel(int id) {
        return memory.get(id);
    }
}
