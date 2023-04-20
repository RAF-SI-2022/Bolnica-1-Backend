import java.util.Objects;

public class Car {

    private Integer key;
    private String parent;

    public Car(Integer key, String parent) {
        this.key = key;
        this.parent = parent;
    }

    public Integer getKey() {
        return key;
    }

    public String getParent() {
        return parent;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return key.equals(car.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
