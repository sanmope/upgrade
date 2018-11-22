package com.upgrade.campsite;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public interface ReservationService {
    Reservation getReservationByCheckin(Date checkin);

    Set<Date> getCampsiteByDateRange(DateTime from, DateTime to);

    Long CampsiteReservation(String name, String email, DateTime from, DateTime to) throws Exception;

    Reservation modifyCampsiteReservation(long reservationid, DateTime from, DateTime to) throws Exception;

    void deleteCampsiteReservation(long reservationId) throws Exception;

    Reservation save(Reservation reservation) throws Exception;

    long count();
}
