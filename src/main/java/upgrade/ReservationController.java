package upgrade;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @RequestMapping(value="/campsiteavailability",method=RequestMethod.GET)
    public Set<Date> campsiteAvailable(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from,@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) throws Exception {
        Set<Date> campsiteSet = new HashSet<>();
        try{
           campsiteSet = reservationService.getCampsiteByDateRange(from,to);

        }catch(CampsiteNotFoundException e){
                throw new Exception(e);

        }
        return campsiteSet;
    }

    @RequestMapping(value="/reservecampsite",method=RequestMethod.POST)
    public Long reserveCampsite(@RequestParam(value="name", required=true) String name, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from,@RequestParam  @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) throws Exception {
        Long reservationId;
        try {
            reservationId = reservationService.CampsiteReservation(name,from,to);
        }catch (Exception e){
            throw new Exception("An error occured during the reservation");
        }
        return reservationId;
    }

    @RequestMapping(value="/modifyreservation")
    public Reservation reservationByCheckin(@RequestParam(value="reservation_id", required=true) long reservationId, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from,@RequestParam  @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) throws Exception {
        Reservation reservation = new Reservation();
        try {
            reservation = reservationService.modifyCampsiteReservation(reservationId,from,to);
        }catch (Exception e) {
            if (e.getClass() == ReservationNotFoundException.class)
                throw new ReservationNotFoundException("404 - Reservation not found. Reservation ID - " + reservationId);
        }
        return reservation;
    }

    @RequestMapping(value="/deletereservation",method=RequestMethod.DELETE)
    public void deleteReservation(@RequestParam(value="reservationid", required=true) long reservationid) {
        reservationService.deleteCampsiteReservation(reservationid);
    }

    @RequestMapping(value="/")
    public String landingPage() {
        return "Welcome to the Volcano Campsite Reservation Page";
    }

}
