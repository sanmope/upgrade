package upgrade;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

public interface ReservationService {
    public Reservation getReservationByDate(DateTime date);
}
