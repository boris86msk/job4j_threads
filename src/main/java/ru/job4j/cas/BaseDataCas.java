package ru.job4j.cas;

public class BaseDataCas {
    private final int id;
    private final int version;
    private String name;

    public BaseDataCas(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
