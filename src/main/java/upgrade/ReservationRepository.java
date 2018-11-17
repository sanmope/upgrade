package upgrade;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value="select * from reservation where id=(Select reservation_id from campsite c Where c.date between ?1 and ?2)",nativeQuery = true)
    Reservation findByDate(DateTime from);

}
