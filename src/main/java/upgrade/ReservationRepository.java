package upgrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@Transactional

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value="select * from reservation where reservation_id=(Select c.reservation_id from campsite c Where c.date=? fetch first row only)",nativeQuery = true)
    Reservation findByDate(Date from);

    @Query(value="select * from reservation where reservation_id=(Select c.reservation_id from campsite c Where c.date=? fetch first row only)",nativeQuery = true)
    Reservation getReservation(Date from);

}
