
import com.upgrade.campsite.*;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class ReservationControllerTests {

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void shouldReturnWelcomeMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to the Volcano Campsite Reservation Page")));
    }


    @Test
    public void shouldReturnCampsiteAvailableDates() throws Exception {
        DateTime from = new DateTime(1 / 10 / 2018);
        DateTime to = new DateTime(1 / 11 / 2018);
        Set<Campsite> campsiteSet = new HashSet<>();
        campsiteSet.add(new Campsite(new Date(1 / 10 / 2018)));
        campsiteSet.add(new Campsite(new Date(1 / 11 / 2018)));
        Reservation savedReservation = new Reservation("Santiago","s@s.com", campsiteSet);


        when(reservationService.CampsiteReservation("santiago","s@s",from,to)).thenReturn(savedReservation.getId());
        this.mockMvc.perform(get("/reservecampsite?name=santiago&email=s@s&from=12/1/2018&to=12/3/2018")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));
    }

}