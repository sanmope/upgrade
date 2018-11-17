package upgrade;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public interface ReservationService {
    Reservation getReservationByCheckin(DateTime checkin);

    Set<Date> getCampsiteByDateRange(DateTime from, DateTime to);

    Long CampsiteReservation(String name, DateTime from, DateTime to) throws Exception;

    Reservation modifyCampsiteReservation(long reservationid, DateTime from, DateTime to);

    void deleteCampsiteReservation(long reservationId);

    Reservation save(Reservation reservation);
}
