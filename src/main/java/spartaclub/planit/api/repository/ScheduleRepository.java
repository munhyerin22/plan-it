package spartaclub.planit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spartaclub.planit.api.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByUpdatedAtDesc();
    List<Schedule> findAllByNameOrderByUpdatedAtDesc(String name);

}
