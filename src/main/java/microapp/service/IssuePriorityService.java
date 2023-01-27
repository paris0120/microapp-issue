package microapp.service;

import java.util.List;
import microapp.domain.IssuePriority;
import microapp.repository.IssuePriorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link IssuePriority}.
 */
@Service
@Transactional
public class IssuePriorityService {

    private final Logger log = LoggerFactory.getLogger(IssuePriorityService.class);

    private final IssuePriorityRepository issuePriorityRepository;

    public IssuePriorityService(IssuePriorityRepository issuePriorityRepository) {
        this.issuePriorityRepository = issuePriorityRepository;
    }

    /**
     * Save a issuePriority.
     *
     * @param issuePriority the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssuePriority> save(IssuePriority issuePriority) {
        log.debug("Request to save IssuePriority : {}", issuePriority);
        return issuePriorityRepository.save(issuePriority);
    }

    /**
     * Update a issuePriority.
     *
     * @param issuePriority the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssuePriority> update(IssuePriority issuePriority) {
        log.debug("Request to update IssuePriority : {}", issuePriority);
        return issuePriorityRepository.save(issuePriority);
    }

    /**
     * Partially update a issuePriority.
     *
     * @param issuePriority the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssuePriority> partialUpdate(IssuePriority issuePriority) {
        log.debug("Request to partially update IssuePriority : {}", issuePriority);

        return issuePriorityRepository
            .findById(issuePriority.getId())
            .map(existingIssuePriority -> {
                if (issuePriority.getIssuePriority() != null) {
                    existingIssuePriority.setIssuePriority(issuePriority.getIssuePriority());
                }
                if (issuePriority.getIssuePriorityLevel() != null) {
                    existingIssuePriority.setIssuePriorityLevel(issuePriority.getIssuePriorityLevel());
                }

                return existingIssuePriority;
            })
            .flatMap(issuePriorityRepository::save);
    }

    /**
     * Get all the issuePriorities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssuePriority> findAll() {
        log.debug("Request to get all IssuePriorities");
        return issuePriorityRepository.findAll();
    }

    /**
     * Returns the number of issuePriorities available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issuePriorityRepository.count();
    }

    /**
     * Get one issuePriority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssuePriority> findOne(Long id) {
        log.debug("Request to get IssuePriority : {}", id);
        return issuePriorityRepository.findById(id);
    }

    /**
     * Delete the issuePriority by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IssuePriority : {}", id);
        return issuePriorityRepository.deleteById(id);
    }
}
