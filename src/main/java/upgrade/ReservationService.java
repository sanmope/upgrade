package upgrade;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReservationService {
    Reservation getReservationByCheckin(DateTime checkin);

    List<Campsite> getCampsiteByDateRange(DateTime from, DateTime to);

    Long setCampsiteReservation(String name, DateTime from, DateTime to);

    void modifyCampsiteReservation(long reservationid, DateTime from, DateTime to);

    void deleteCampsiteReservation(long reservationId);
}
