import com.upgrade.campsite.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void testSavingReservation() throws Exception{
        Set<Campsite> campsiteSet = new HashSet<>();
        campsiteSet.add(new Campsite(new Date(1 / 10 / 2018)));
        campsiteSet.add(new Campsite(new Date(1 / 11 / 2018)));
        Reservation savedReservation = new Reservation("Santiago","s@s.com", campsiteSet);
        this.entityManager.persist(savedReservation);
        Reservation retrivedReservation = this.reservationRepository.getOne(savedReservation.getId());
        assertThat(savedReservation).isEqualTo(retrivedReservation);

    }

    @Test
    public void testOptimisticLockInsertion () throws Exception{
        Set<Campsite> campsiteSet = new HashSet<>();
        campsiteSet.add(new Campsite(new Date(1 / 10 / 2018)));
        campsiteSet.add(new Campsite(new Date(1 / 11 / 2018)));
        Reservation savedReservation = new Reservation("Santiago","s@s.com", campsiteSet);
        this.entityManager.persist(savedReservation);

        Reservation res1 = this.reservationRepository.getOne(new Long(1));
        Reservation res2 = this.reservationRepository.getOne(new Long(1));

        res1.getCampsiteRange().iterator().next().setDate(new Date(2/5/2018));
        res2.getCampsiteRange().iterator().next().setDate(new Date(3/2/2018));

        assertThat(res1.getCampsiteRange().iterator().next().getVersion()).isEqualTo(0);
        assertThat(res2.getCampsiteRange().iterator().next().getVersion()).isEqualTo(0);

        this.reservationRepository.save(res1);
        this.reservationRepository.save(res2);

    }
}
