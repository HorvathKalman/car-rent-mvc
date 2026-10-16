package hkc.car_rent_mvc.service;

import hkc.car_rent_mvc.config.UserReservationStatuses;
import hkc.car_rent_mvc.dto.*;
import hkc.car_rent_mvc.model.Car;
import hkc.car_rent_mvc.model.Reservation;
import hkc.car_rent_mvc.repository.CarRepository;
import hkc.car_rent_mvc.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
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
        Iterable<Car> allFreeCarsFromDB = carRepository.findAll();


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

    public CarDTO getCarById(Integer carId) {
        CarDTO responseDTO = null;

        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            responseDTO = new CarDTO(
                    car.getId(),
                    car.getType(),
                    car.getDailyPrice(),
                    car.isActive(),
                    null
            );
        }
        return responseDTO;
    }

    public SimpleResponseDTO saveEditedCar(CarDTO carDTO) {
        SimpleResponseDTO responseDTO = null;

        Car car = carRepository.findById(carDTO.getId())
                .orElse(null);

        if (car == null) {
            return new SimpleResponseDTO(
                    UserReservationStatuses.NOT_OK.toString());
        }

        car.setType(carDTO.getType());
        car.setDailyPrice(carDTO.getPrice());
        car.setActive(carDTO.isActive());

        byte[] picture = carDTO.getPicture();

        if (picture != null && picture.length > 0) {
            car.setPicture(picture);
        }

        carRepository.save(car);
        return responseDTO = new SimpleResponseDTO(UserReservationStatuses.OK_SAVED.toString());
    }

    public SimpleResponseDTO saveCar(NewCarDTO newCarDTO) {
        SimpleResponseDTO responseDTO = null;

        Car newCar = null;
        try {
            newCar = carRepository.save(new Car(
                    null,
                    newCarDTO.getType(),
                    newCarDTO.getPrice(),
                    newCarDTO.isActive(),
                    newCarDTO.getPicture().getBytes()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (newCar != null) {
            responseDTO = new SimpleResponseDTO(UserReservationStatuses.OK_SAVED.toString());
        } else {
            responseDTO = new SimpleResponseDTO(UserReservationStatuses.NOT_OK.toString());
        }
        return responseDTO;
    }
}
