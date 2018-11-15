package upgrade;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ControllerAdvice
@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @RequestMapping("/reservationbycheckin")
    public Reservation reservationByCheckin(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime checkin) throws Exception {
        Reservation reservation = reservationService.getReservationByCheckin(checkin);
        if (reservation == null)
            throw new ReservationNotFoundException("404 - Reservation not found. Checkin date- " + checkin);
        return reservation;
    }

    @RequestMapping("/campsiteavailable")
    public List<Campsite> campsiteAvailable(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) {
        return reservationService.getCampsiteByDateRange(from,to);
    }

    @RequestMapping("/reservecampsite")
    public Long reserveCampsite(@RequestParam(value="name", required=true) String name, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) {
        return reservationService.setCampsiteReservation(name,from,to);
    }

    @RequestMapping("/modifyreservation")
    public void modifyReservation(@RequestParam(value="reservationid", required=true) long reservationid, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) {
        reservationService.modifyCampsiteReservation(reservationid,from,to);
    }

    @RequestMapping("/deletereservation")
    public void deleteReservation(@RequestParam(value="reservationid", required=true) long reservationid) {
        reservationService.deleteCampsiteReservation(reservationid);
    }

}
