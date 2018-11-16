package upgrade;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    public Reservation getReservationByCheckin(DateTime checkin){
        return null;
    }

    @Override
    public List<Campsite> getCampsiteByDateRange(DateTime from, DateTime to) {
        List<Campsite> listOfCampsiteAvailables = new ArrayList<>();
        return listOfCampsiteAvailables;
    }

    @Override
    public Long setCampsiteReservation(String name, DateTime from, DateTime to) throws Exception {
        if (isCampsiteSetAvailable (from,to)) {
            Set<Campsite> campsiteSet = new HashSet<Campsite>();
            for (DateTime date = from; date.isBefore(to); date = date.plusDays(1)) {
                campsiteSet.add(new Campsite(date.toDate()));
            }

            Reservation reservation = new Reservation(name, campsiteSet);
            reservationRepository.save(reservation);
            return reservation.getId();
        }else {
            throw new Exception("This range of dates is already reserved");
        }
    }


    @Override
    public void modifyCampsiteReservation(long reservationid, DateTime from, DateTime to) {
        Reservation reservation = reservationRepository.findById(reservationid).orElse(null);
        reservationRepository.save(reservation);
    }

    @Override
    public void deleteCampsiteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private boolean isCampsiteSetAvailable(DateTime from,DateTime to) {
                return (campsiteRepository.findByDateAfterAndDateBefore(from,to)==null)?true:false;
    }
}
