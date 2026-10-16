package hkc.car_rent_mvc.service;

import hkc.car_rent_mvc.config.UserReservationStatuses;
import hkc.car_rent_mvc.dto.*;
import hkc.car_rent_mvc.model.Car;
import hkc.car_rent_mvc.model.Reservation;
import hkc.car_rent_mvc.repository.CarRepository;
import hkc.car_rent_mvc.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private CarRepository carRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public UserService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public FreeCarsDTO getAllCars(ResDateDTO dateRange) {
        FreeCarsDTO requestDTO = null;

        List<CarDTO> carListDTO = new ArrayList<>();

        Iterable<Car> freeCars = carRepository.findAll();

        for (Car tempCar : freeCars) {

            CarDTO tempCarDTO = new CarDTO(
                    tempCar.getId(),
                    tempCar.getType(),
                    tempCar.getDailyPrice(),
                    tempCar.isActive(),
                    null
            );
            carListDTO.add(tempCarDTO);
        }
        requestDTO = new FreeCarsDTO(
                carListDTO,
                dateRange
        );
        return requestDTO;
    }

    public UserSelectionDTO getSelectedCar(UserSelectionDTO selectedCar) {
        UserSelectionDTO requestDTO = null;

        Optional<Car> carOpt = carRepository.findById(selectedCar.getCarDTO().getId());

        if (carOpt.isPresent()) {

            Car car = carOpt.get();

            CarDTO carDTO = new CarDTO(
                    car.getId(),
                    car.getType(),
                    car.getDailyPrice(),
                    car.isActive(),
                    car.getPicture()
            );

            requestDTO = new UserSelectionDTO(
                    carDTO,
                    selectedCar.getResDateDTO()
            );
        }
        return requestDTO;
    }

    public SimpleResponseDTO saveReservation(UserResDTO requestUserResDTO) {
        SimpleResponseDTO request = null;

        int numOfDates = calculateDaysByDate(
                requestUserResDTO.getUserSelectionDTO().getResDateDTO().getStartDate(),
                requestUserResDTO.getUserSelectionDTO().getResDateDTO().getEndDate());

        Car tempCarForPrice = carRepository.findById(requestUserResDTO.getUserSelectionDTO().getCarDTO().getId()).orElse(null);

        int carDailyPrice = tempCarForPrice.getDailyPrice();
        int reservationDailyPrice = carDailyPrice * numOfDates;

        Reservation saveResult = reservationRepository.save(new Reservation(
                null,
                requestUserResDTO.getUserSelectionDTO().getCarDTO().getId(),
                requestUserResDTO.getName(),
                requestUserResDTO.getEmail(),
                requestUserResDTO.getAddress(),
                requestUserResDTO.getPhone(),
                requestUserResDTO.getUserSelectionDTO().getResDateDTO().getStartDate(),
                requestUserResDTO.getUserSelectionDTO().getResDateDTO().getEndDate(),
                reservationDailyPrice
        ));
        if (saveResult.getId() != null) {
            return request = new SimpleResponseDTO(UserReservationStatuses.OK_RESERVED.toString());
        } else
            return request = new SimpleResponseDTO(UserReservationStatuses.NOT_OK.toString());
    }

    private int calculateDaysByDate(LocalDate startDate, LocalDate endDate) {
        return Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
    }
}
