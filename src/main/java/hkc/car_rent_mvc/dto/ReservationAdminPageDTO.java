package hkc.car_rent_mvc.dto;

import java.time.LocalDate;

public class ReservationAdminPageDTO {
    private String name;
    private Integer carId;
    private String email;
    private String address;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;

    public ReservationAdminPageDTO(String name, Integer carId, String email, String address, String phone, LocalDate startDate, LocalDate endDate, int price) {
        this.name = name;
        this.carId = carId;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}