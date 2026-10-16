package hkc.car_rent_mvc.repository;

import hkc.car_rent_mvc.model.Car;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

//    Iterable<Car> findByActive(int active);


    @Modifying
    @Query("""
        UPDATE car
        SET type = :type,
            daily_price = :dailyPrice,
            active = :active
        WHERE id = :carId
        """)
    int updateCar(
            @Param("carId") Integer carId,
            @Param("type") String type,
            @Param("dailyPrice") int dailyPrice,
            @Param("active") boolean active
    );

}
