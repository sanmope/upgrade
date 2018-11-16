package upgrade;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Executable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @RequestMapping(value="/reservationbycheckin",method= RequestMethod.GET)
    public Reservation reservationByCheckin(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime checkin) throws Exception {
        Reservation reservation = reservationService.getReservationByCheckin(checkin);
        if (reservation == null)
            throw new ReservationNotFoundException("404 - Reservation not found. Checkin date- " + checkin);
        return reservation;
    }

    @RequestMapping(value="/campsiteavailable",method=RequestMethod.GET)
    public Set<Campsite> campsiteAvailable(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) throws Exception {
        Set<Campsite>  campsiteSet = new HashSet<>();
        try{
           campsiteSet = reservationService.getCampsiteByDateRange(from,to);

        }catch(CampsiteNotFoundException e){
                throw new Exception(e);

        }
        return campsiteSet;
    }

    @RequestMapping(value="/reservecampsite",method=RequestMethod.POST)
    public Long reserveCampsite(@RequestParam(value="name", required=true) String name, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from,@RequestParam  @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) throws Exception {

        return reservationService.setCampsiteReservation(name,from,to);
    }

    @RequestMapping(value="/modifyreservation",method=RequestMethod.PUT)
    public void modifyReservation(@RequestParam(value="reservationid", required=true) long reservationid, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime from, @DateTimeFormat(pattern="MM/dd/yyyy") DateTime to) {
        reservationService.modifyCampsiteReservation(reservationid,from,to);
    }

    @RequestMapping(value="/deletereservation",method=RequestMethod.DELETE)
    public void deleteReservation(@RequestParam(value="reservationid", required=true) long reservationid) {
        reservationService.deleteCampsiteReservation(reservationid);
    }

}
