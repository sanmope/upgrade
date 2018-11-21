import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import upgrade.Campsite;
import upgrade.Reservation;
import upgrade.ReservationController;
import upgrade.ReservationService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(ReservationController.class)
@SpringBootTest(classes=ReservationController.class)
@AutoConfigureMockMvc
public class ReservationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Before
    public void setUp() throws Exception{
        Set<Campsite> campsiteSet = new HashSet<>();
        campsiteSet.add(new Campsite(new Date(1/10/2018)));
        campsiteSet.add(new Campsite(new Date(1/11/2018)));
        Reservation res1 = new Reservation("Santiago",campsiteSet);

        Set<Campsite> campsiteSet2 = new HashSet<>();
        campsiteSet2.add(new Campsite(new Date(1/12/2018)));
        campsiteSet2.add(new Campsite(new Date(1/13/2018)));
        Reservation res2 = new Reservation("Santiago",campsiteSet2);

        reservationService.save(res1);
        reservationService.save(res2);
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void shouldNotSaveReservation2AtSameTime(){
        //Assertions.assertThat("Number of reservation in database",reservationService)
        Reservation reservation1 = reservationService.getReservationByCheckin(new DateTime(1/10/2018));
        Reservation reservation2 = reservationService.getReservationByCheckin(new DateTime(1/10/2018));

        Set<Campsite> campsiteSet = new HashSet<Campsite>();
        campsiteSet.add(new Campsite(new Date(1/12/2018)));
        campsiteSet.add(new Campsite(new Date(1/13/2018)));
        reservation1.setCampsiteRange(campsiteSet);

        reservationService.CampsiteReservation("Santiago",new DateTime(1/12/2018),new DateTime(1/13/2018));

        Set<Campsite> campsiteSet2 = new HashSet<Campsite>();
        campsiteSet2.add(new Campsite(new Date(1/14/2018)));
        campsiteSet2.add(new Campsite(new Date(1/15/2018)));
        reservation2.setCampsiteRange(campsiteSet2);

        Assertions.assertThat(reservation1.getCampsiteRange().iterator().next().getVersion()).isEqualTo(0);
        Assertions.assertThat(reservation1.getCampsiteRange().iterator().next().getVersion()).isEqualTo(0);

        reservationService.save(reservation1);
        //Exception should occur now
        reservationService.save(reservation2);



    }

    @Test
    public void shouldReturnWelcomeMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to the Volcano Campsite Reservation Page")));
    }
}