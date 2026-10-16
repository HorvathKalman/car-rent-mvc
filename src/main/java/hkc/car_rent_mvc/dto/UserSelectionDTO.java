package hkc.car_rent_mvc.dto;

public class UserSelectionDTO {

    private CarDTO carDTO;
    private ResDateDTO resDateDTO;

    public UserSelectionDTO(CarDTO carDTO, ResDateDTO resDateDTO) {
        this.carDTO = carDTO;
        this.resDateDTO = resDateDTO;
    }

    public CarDTO getCarDTO() {
        return carDTO;
    }

    public void setCarDTO(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    public ResDateDTO getResDateDTO() {
        return resDateDTO;
    }

    public void setResDateDTO(ResDateDTO resDateDTO) {
        this.resDateDTO = resDateDTO;
    }
}