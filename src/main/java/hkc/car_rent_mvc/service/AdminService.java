package hkc.car_rent_mvc.service;

import hkc.car_rent_mvc.dto.AdminPageDTO;
import hkc.car_rent_mvc.dto.CarDTO;
import hkc.car_rent_mvc.dto.ReservationAdminPageDTO;
import hkc.car_rent_mvc.dto.ReservationDTO;
import hkc.car_rent_mvc.model.Car;
import hkc.car_rent_mvc.model.Reservation;
import hkc.car_rent_mvc.repository.CarRepository;
import hkc.car_rent_mvc.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private CarRepository carRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public AdminService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public AdminPageDTO loadAdminPage() {
        AdminPageDTO requestDTO = null;

        Iterable<Reservation> allReservationFromDB = reservationRepository.findAll();
        Iterable<Car> allFreeCarsFromDB = carRepository.getAllFreeCars();


        List<ReservationAdminPageDTO> reservationAdminPageDTOS = new ArrayList<>();
        List<CarDTO> carDTOS = new ArrayList<>();

        for (Reservation tempReservation : allReservationFromDB) {
            ReservationAdminPageDTO tempDTO = new ReservationAdminPageDTO(
                    tempReservation.getName(),
                    tempReservation.getCarId(),
                    tempReservation.getEmail(),
                    tempReservation.getAddress(),
                    tempReservation.getPhone(),
                    tempReservation.getStartDate(),
                    tempReservation.getEndDate(),
                    tempReservation.getPrice()
            );
            reservationAdminPageDTOS.add(tempDTO);
        }

        for (Car car : allFreeCarsFromDB) {
            CarDTO carDTO = new CarDTO(
                    car.getId(),
                    car.getType(),
                    car.getDailyPrice(),
                    car.isActive(),
                    null
            );
            carDTOS.add(carDTO);
        }
        requestDTO = new AdminPageDTO(
                reservationAdminPageDTOS,
                carDTOS,
                null
        );
        return requestDTO;
    }
}
