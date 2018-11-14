package upgrade;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@Transactional

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByCheckin(Date checkin);
    //@Query("Select r from reservation r Where r.checkin= ?1")
    //Reservation getReservationByCheckin(DateTime checkin);
}
