package upgrade;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public Reservation getReservationByCheckin(DateTime checkin){
        return reservationRepository.findByCheckin(checkin.toDate());
    }

    @Override
    public List<Campsite> getCampsiteByDateRange(DateTime from, DateTime to) {
        List<Campsite> listOfCampsiteAvailables = new ArrayList<>();
        return listOfCampsiteAvailables;
    }

    @Override
    public Long setCampsiteReservation(String name, DateTime from, DateTime to) {
        Reservation reservation = new Reservation(name,from,to);
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Override
    public void modifyCampsiteReservation(long reservationid, DateTime from, DateTime to) {
        Reservation reservation = reservationRepository.getOne(reservationid);
        reservation.setCheckin(from.toDate());
        reservation.setCheckout(to.toDate());
        reservationRepository.save(reservation);
    }

    @Override
    public void deleteCampsiteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

}
