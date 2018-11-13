package upgrade;

import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/campsite")
    public Campsite campsite(@RequestParam(value="email", defaultValue="jorge@lopez.com") String email) {
        reservationService.getReservationByDate(new DateTime());

        return new Campsite(counter.incrementAndGet(),String.format(template, email));
    }
}
