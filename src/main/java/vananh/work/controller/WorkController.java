package vananh.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;;
import vananh.work.exception.BadRequestException;
import vananh.work.entity.Work;
import vananh.work.exception.ResourceNotFoundException;
import vananh.work.service.WorkService;
import vananh.work.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * WorkController
 */
@RestController
@RequestMapping("/work")
public class WorkController {

    @Autowired
    WorkService service;

    @Autowired(required = false)
    MessageUtil messageUtil;

    /**
     * Get all Work
     *
     * @param pageable
     * @return ResponseEntity<Page<Work>>
     */
    @GetMapping(value = "/")
    public ResponseEntity<Page<Work>> getAll(Pageable pageable) {
        Page<Work> workList = service.findAll(pageable);
        return ResponseEntity.ok(workList);
    }

    /**
     * Get Work by workName
     *
     * @param name
     * @return ResponseEntity<Work>
     */
    @GetMapping(value = "/{name}")
    public ResponseEntity<Work> getByName(@PathVariable(value = "name") String name) {
        Optional<Work> workOptional = service.getByName(name);
        if (!workOptional.isPresent()) {
            throw new ResourceNotFoundException("Work", "WorkName", name);
        }
        return ResponseEntity.ok(workOptional.get());
    }

    /**
     * Add new Work
     *
     * @param work
     * @param request
     * @return ResponseEntity<Work>
     */
    @PostMapping(value = "/")
    public ResponseEntity<Work> add(@Valid @RequestBody Work work, HttpServletRequest request) {
        if (service.existByName(work.getWorkName())) {
            throw new BadRequestException(messageUtil.getMessage("work.existed", "", request));
        }
        work = service.save(work);
        return new ResponseEntity<>(work, HttpStatus.CREATED);
    }

    /**
     * Update Work
     *
     * @param work
     * @return ResponseEntity<Work>
     */
    @PutMapping(value = "/")
    public ResponseEntity<Work> update(@Valid @RequestBody Work work) {
        if (!service.existByName(work.getWorkName())) {
            throw new ResourceNotFoundException("Work", "WorkName", work.getWorkName());
        }
        return ResponseEntity.ok(service.update(work));
    }

    /**
     * Delete Work by name
     *
     * @param name
     * @param request
     * @return ResponseEntity<?>
     */
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<?> delete(@PathVariable(value = "name") String name, HttpServletRequest request) {
        Boolean result = service.deleteByName(name);
        if (!result) {
            return new ResponseEntity<>("Delete work fail.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Delete work success.", HttpStatus.ACCEPTED);
    }
}
