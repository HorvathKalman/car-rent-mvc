package hkc.car_rent_mvc.repository;

import hkc.car_rent_mvc.model.Car;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

//    Iterable<Car> findByActive(int active);

    @Query("""
            SELECT * FROM car
            WHERE active = 1;
            """)
    List<Car> getAllFreeCars();

}
