package vananh.work.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vananh.work.common.exception.ResourceNotFoundException;
import vananh.work.entity.Work;
import vananh.work.repository.WorkRepository;
import vananh.work.service.WorkService;
import java.util.Optional;

/**
 * WorkServiceImpl
 */
@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    @Autowired
    WorkRepository repository;

    /**
     * Find all Work by pageable
     *
     * @param pageable
     * @return Page<Work>
     */
    @Override
    public Page<Work> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Save Work
     *
     * @param work
     * @return Work
     */
    @Override
    public Work save(Work work) {
        return repository.save(work);
    }

    /**
     * Update Work
     *
     * @param work
     * @return Work
     */
    @Override
    public Work update(Work work) {
        return repository.save(work);
    }

    /**
     * Get Work by workName
     *
     * @param name
     * @return Optional<Work>
     */
    @Override
    public Optional<Work> getByName(String name) {
        return repository.findByWorkName(name);
    }

    /**
     * Delete Work by name
     *
     * @param name
     * @return Boolean
     */
    @Override
    public Boolean deleteByName(String name) {
        Boolean result = true;
        try {
            repository.deleteWorkByWorkName(name);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    /**
     * Check exist
     *
     * @param name
     * @return boolean
     */
    @Override
    public boolean existByName(String name) {
        return repository.existsByWorkName(name);
    }
}
