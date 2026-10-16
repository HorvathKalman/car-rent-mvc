package hkc.car_rent_mvc.dto;

import org.springframework.web.multipart.MultipartFile;

public class CarDTO {

    private Integer id;
    private String type;
    private Integer price;
    private Boolean active;
    private byte[] picture;

    public CarDTO(Integer id, String type, Integer price, Boolean active, byte[] picture) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.active = active;
        this.picture = picture;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
