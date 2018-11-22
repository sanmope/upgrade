package com.upgrade.campsite;


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
    public Long CampsiteReservation(String name,String email, DateTime from, DateTime to) throws Exception {
        Long reservation_id= new Long(0);
        try{
            if (isCampsiteSetAvailable (from,to) & isDateRangeLessThanThreeDays(from,to) & isCheckinAnticipationValid(from,to)) {
                Set<Campsite> campsiteSet = getSetOfCampsitesRange(from, to);
                Reservation reservation = new Reservation(name,email,campsiteSet);
                reservationRepository.save(reservation);
                return reservation.getId();
            }
        }catch( Exception e) {
            throw new Exception("This range of dates is already reserved or exceeds the maximum of 3 days");
        }
        return reservation_id;
    }


    @Override
    public Reservation modifyCampsiteReservation(long reservationid, DateTime from, DateTime to) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationid).orElse(null);
        try{
            if (isCampsiteSetAvailable (from,to) & isDateRangeLessThanThreeDays(from,to) & isCheckinAnticipationValid(from,to)) {
                Set<Campsite> campsiteSet = getSetOfCampsitesRange(from, to);
                reservationRepository.save(reservation);
                return reservation;
            }
        }catch( Exception e) {
            throw new Exception("This range of dates is already reserved or exceeds the maximum of 3 days");
        }
        return reservation;
    }

    @Override
    public void deleteCampsiteReservation(long reservationId) throws Exception {
        try{
            reservationRepository.deleteById(reservationId);
        }catch(Exception e){
            throw new Exception("Can't delete this Reservation");
        }

    }

    @Override
    public Reservation save(Reservation reservation) throws Exception {
        try {
            return reservationRepository.save(reservation);
        }catch (Exception e){
            throw new Exception("Can't save this reservation");
        }
    }

    @Override
    public long count() {
        return reservationRepository.count();
    }





    private boolean isCampsiteSetAvailable(DateTime from,DateTime to) {
                return (campsiteRepository.findByDateAfterAndDateBefore(from,to).size()==0)?true:false;
    }

    private boolean isDateRangeLessThanThreeDays(DateTime from, DateTime to){
        return (from.compareTo(to)>=3)?true:false;
    }

    private Set<Campsite> getSetOfCampsitesRange(DateTime from, DateTime to) {
        Set<Campsite> campsiteSet = new HashSet<Campsite>();
        for (DateTime date = from; date.isBefore(to.toInstant()); date = date.plusDays(1)) {
            campsiteSet.add(new Campsite(date.toDate()));
        }
        return campsiteSet;
    }

    private boolean isCheckinAnticipationValid(DateTime from, DateTime to){
        return ( (from.compareTo(DateTime.now())>=1) || (from.compareTo(DateTime.now())<=30) )?true:false;
    }
}
