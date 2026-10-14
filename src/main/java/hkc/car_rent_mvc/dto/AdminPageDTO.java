package hkc.car_rent_mvc.dto;

import java.util.List;

public class AdminPageDTO {

    private List<ReservationAdminPageDTO> allReservations;
    private List<CarDTO> allCars;
    private String response;

    public AdminPageDTO(List<ReservationAdminPageDTO> allReservations, List<CarDTO> allCars, String response) {
        this.allReservations = allReservations;
        this.allCars = allCars;
        this.response = response;
    }

    public List<ReservationAdminPageDTO> getAllReservations() {
        return allReservations;
    }

    public void setAllReservations(List<ReservationAdminPageDTO> allReservations) {
        this.allReservations = allReservations;
    }

    public List<CarDTO> getAllCars() {
        return allCars;
    }

    public void setAllCars(List<CarDTO> allCars) {
        this.allCars = allCars;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
