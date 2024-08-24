package kr.co.apiy.global.log;

import kr.co.apiy.global.entity.ExceptionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<ExceptionLogEntity, Long> {
}
