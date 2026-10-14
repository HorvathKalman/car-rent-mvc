package hkc.car_rent_mvc.repository;

import hkc.car_rent_mvc.model.Car;
import hkc.car_rent_mvc.model.Reservation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    @Query("""
        SELECT c.id, c.type, c.daily_price, c.active
        FROM car c
        WHERE c.active = 1
          AND NOT EXISTS (
              SELECT 1
              FROM reservation r
              WHERE r.car_id = c.id
                AND r.start_date < :end_date
                AND r.end_date > :start_date
          )
        """)
    List<Car> getAllFreeCars(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate
    );
}
