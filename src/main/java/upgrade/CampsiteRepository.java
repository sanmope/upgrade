package upgrade;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface CampsiteRepository extends JpaRepository<Campsite, Long> {

    Campsite findByDateIn(List<Date> dates);

    @Query(value="Select c from campsite c Where c.date between ?1 and ?2",nativeQuery = true)
    Set<Campsite> findByDateAfterAndDateBefore(DateTime from, DateTime to);
}
