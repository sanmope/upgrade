package upgrade;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    public Reservation getReservationByCheckin(Date checkin){
        return reservationRepository.getReservation(checkin);
    }

    @Override
    public Set<Date> getCampsiteByDateRange(DateTime from, DateTime to) {
        Set<Campsite> setOfCampsiteAvailables = new HashSet<>();
        Set<Date> setOfAvailableDates = new HashSet<Date>();
        setOfCampsiteAvailables = campsiteRepository.findByDateBetween(from.toDate(),to.toDate());
        if (setOfCampsiteAvailables.size() != 0) {
            if (to == null )
                to = from.plusDays(30);
            for (DateTime date = from; date.isBefore(to); date = date.plusDays(1) ){
                setOfAvailableDates.add(date.toDate());
            }
            Set<Date> datesInCampsite = setOfCampsiteAvailables.stream().map(sc -> sc.getDate()).collect(Collectors.toSet());
            setOfAvailableDates.removeAll(datesInCampsite);
            return setOfAvailableDates;
        }else
            throw new CampsiteNotFoundException("No dates available for this period");
    }

    @Override
    public Long CampsiteReservation(String name, DateTime from, DateTime to) throws Exception {
        if (isCampsiteSetAvailable (from,to)) {
            Set<Campsite> campsiteSet = new HashSet<Campsite>();
            for (DateTime date = from; date.isBefore(to.toInstant()); date = date.plusDays(1)) {
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
    public Reservation modifyCampsiteReservation(long reservationid, DateTime from, DateTime to) {
        Reservation reservation = reservationRepository.findById(reservationid).orElse(null);
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public void deleteCampsiteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public long count() {
        return reservationRepository.count();
    }

    private boolean isCampsiteSetAvailable(DateTime from,DateTime to) {
                return (campsiteRepository.findByDateAfterAndDateBefore(from,to).size()==0)?true:false;
    }
}
