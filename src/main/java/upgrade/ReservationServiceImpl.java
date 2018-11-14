package upgrade;


import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationServiceImpl implements ReservationService {
    public Reservation getReservationByCheckin(DateTime checkin){
        return null;
    }

    @Override
    public List<Campsite> getCampsiteByDateRange(DateTime from, DateTime to) {
        List<Campsite> listOfCampsiteAvailables = new ArrayList<>();
        listOfCampsiteAvailables.add(new Campsite(from));
        listOfCampsiteAvailables.add(new Campsite(to));
        return listOfCampsiteAvailables;
    }

    @Override
    public Long setCampsiteReservation(String name, DateTime from, DateTime to) {
        Reservation reservation = new Reservation(name,from,to);
        return null;
    }

    @Override
    public Long modifyCampsiteReservation(long reservationid, DateTime from, DateTime to) {

        return null;
    }

    @Override
    public Long deleteCampsiteReservation(long reservationId) {
        return null;
    }

}
