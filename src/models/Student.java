package models;

public class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + "," + name;
    }

    public static Student fromString(String record) {
        String[] parts = record.split(",");
        return new Student(parts[0], parts[1]);
    }
}
