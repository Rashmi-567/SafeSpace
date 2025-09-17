package model;

public record Data(int id, String fileName, String path, String email) {
    // Constructor without email
    public Data(int id, String fileName, String path) {
        this(id, fileName, path, null); // delegate to main constructor
    }

    // Constructor with email


    // Optional: for debugging/printing
    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", path='" + path + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
