package vananh.work.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vananh.work.entity.Work;
import java.util.Optional;

/**
 * WorkService
 */
public interface WorkService {

    /**
     * Find all Work by pageable
     *
     * @param pageable
     * @return Page<Work>
     */
    Page<Work> findAll(Pageable pageable);

    /**
     * Get Work by workName
     *
     * @param name
     * @return Optional<Work>
     */
    Optional<Work> getByName(String name);

    /**
     * Save Work
     *
     * @param work
     * @return Work
     */
    Work save(Work work);

    /**
     * Update Work
     *
     * @param work
     * @return Work
     */
    Work update(Work work);

    /**
     * Delete Work by name
     *
     * @param name
     * @return Boolean
     */
    Boolean deleteByName(String name);

    /**
     * Check exist by name
     *
     * @param name
     * @return boolean
     */
    boolean existByName(String name);
}
