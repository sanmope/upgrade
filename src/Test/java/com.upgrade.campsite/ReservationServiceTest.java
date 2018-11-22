import com.upgrade.campsite.Application;
import com.upgrade.campsite.Campsite;
import com.upgrade.campsite.Reservation;
import com.upgrade.campsite.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import upgrade.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Before
    public void setUp() throws Exception {
        Set<Campsite> campsiteSet = new HashSet<>();
        campsiteSet.add(new Campsite(new Date(1 / 10 / 2018)));
        campsiteSet.add(new Campsite(new Date(1 / 11 / 2018)));
        Reservation res1 = new Reservation("Santiago", campsiteSet);

        Set<Campsite> campsiteSet2 = new HashSet<>();
        campsiteSet2.add(new Campsite(new Date(1 / 12 / 2018)));
        campsiteSet2.add(new Campsite(new Date(1 / 13 / 2018)));
        Reservation res2 = new Reservation("Santiago", campsiteSet2);

        reservationService.save(res1);
        reservationService.save(res2);
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)

    public void shouldNotSaveReservation2AtSameTime() {
        Assertions.assertThat(reservationService.count()).isEqualTo(2);
        Reservation reservation1 = reservationService.getReservationByCheckin(new Date(1/10/2018));
        Reservation reservation2 = reservationService.getReservationByCheckin(new Date(1/10/2018));

        Set<Campsite> campsiteSetToModify = reservation1.getCampsiteRange();
        campsiteSetToModify.iterator().next().setDate(new Date(1/11/2018));
        reservation1.setCampsiteRange(campsiteSetToModify);

        Set<Campsite> campsiteSetToModify2 = reservation2.getCampsiteRange();
        campsiteSetToModify2.iterator().next().setDate(new Date(1/13/2018));
        reservation2.setCampsiteRange(campsiteSetToModify);


        Assertions.assertThat(reservation1.getCampsiteRange().iterator().next().getVersion()).isEqualTo(0);
        Assertions.assertThat(reservation2.getCampsiteRange().iterator().next().getVersion()).isEqualTo(0);

        reservationService.save(reservation1);
        //Exception should occur now
        reservationService.save(reservation2);

    }
}
