package hkc.car_rent_mvc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.web.multipart.MultipartFile;

@Table
public class Car {

    @Id
    @Column
    private Integer id;

    @Column
    private String type;
    @Column
    private int dailyPrice;
    @Column
    private boolean active;

    public Car(Integer id, String type, int dailyPrice, boolean active) {
        this.id = id;
        this.type = type;
        this.dailyPrice = dailyPrice;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(int dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
