package vananh.work.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vananh.work.entity.Work;
import java.util.Optional;

/**
 * WorkRepository
 */
@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    Page<Work> findAll(Pageable pageable);

    Optional<Work> findByWorkName(String name);

    boolean existsByWorkName(String name);

    void deleteWorkByWorkName(String name);
}
