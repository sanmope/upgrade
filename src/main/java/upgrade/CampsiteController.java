package upgrade;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampsiteController{
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/campsite")
    public Campsite campsite(@RequestParam(value="email", defaultValue="jorge@lopez.com") String email) {
       return new Campsite(counter.incrementAndGet(),
       String.format(template, email));
    }
}