import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.upgrade.campsite.Application;
import com.upgrade.campsite.ReservationController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SmokeTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    public void contextLoads() throws Exception{
        Assertions.assertThat(reservationController).isNotNull();
    }
}
