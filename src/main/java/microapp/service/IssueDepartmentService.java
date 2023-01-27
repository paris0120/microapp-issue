package microapp.service;

import java.util.List;
import microapp.domain.IssueDepartment;
import microapp.repository.IssueDepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link IssueDepartment}.
 */
@Service
@Transactional
public class IssueDepartmentService {

    private final Logger log = LoggerFactory.getLogger(IssueDepartmentService.class);

    private final IssueDepartmentRepository issueDepartmentRepository;

    public IssueDepartmentService(IssueDepartmentRepository issueDepartmentRepository) {
        this.issueDepartmentRepository = issueDepartmentRepository;
    }

    /**
     * Save a issueDepartment.
     *
     * @param issueDepartment the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueDepartment> save(IssueDepartment issueDepartment) {
        log.debug("Request to save IssueDepartment : {}", issueDepartment);
        return issueDepartmentRepository.save(issueDepartment);
    }

    /**
     * Update a issueDepartment.
     *
     * @param issueDepartment the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueDepartment> update(IssueDepartment issueDepartment) {
        log.debug("Request to update IssueDepartment : {}", issueDepartment);
        return issueDepartmentRepository.save(issueDepartment);
    }

    /**
     * Partially update a issueDepartment.
     *
     * @param issueDepartment the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueDepartment> partialUpdate(IssueDepartment issueDepartment) {
        log.debug("Request to partially update IssueDepartment : {}", issueDepartment);

        return issueDepartmentRepository
            .findById(issueDepartment.getId())
            .map(existingIssueDepartment -> {
                if (issueDepartment.getIssueDepartmentKey() != null) {
                    existingIssueDepartment.setIssueDepartmentKey(issueDepartment.getIssueDepartmentKey());
                }
                if (issueDepartment.getIssueDepartment() != null) {
                    existingIssueDepartment.setIssueDepartment(issueDepartment.getIssueDepartment());
                }

                return existingIssueDepartment;
            })
            .flatMap(issueDepartmentRepository::save);
    }

    /**
     * Get all the issueDepartments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssueDepartment> findAll() {
        log.debug("Request to get all IssueDepartments");
        return issueDepartmentRepository.findAll();
    }

    /**
     * Returns the number of issueDepartments available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issueDepartmentRepository.count();
    }

    /**
     * Get one issueDepartment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssueDepartment> findOne(Long id) {
        log.debug("Request to get IssueDepartment : {}", id);
        return issueDepartmentRepository.findById(id);
    }

    /**
     * Delete the issueDepartment by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IssueDepartment : {}", id);
        return issueDepartmentRepository.deleteById(id);
    }
}
