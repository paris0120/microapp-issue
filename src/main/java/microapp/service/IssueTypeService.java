package microapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import microapp.domain.IssueType;
import microapp.domain.MenuItem;
import microapp.repository.IssueTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link IssueType}.
 */
@Service
@Transactional
public class IssueTypeService {

    private List<IssueType> issueTypes;

    private final Logger log = LoggerFactory.getLogger(IssueTypeService.class);

    private final IssueTypeRepository issueTypeRepository;

    public IssueTypeService(IssueTypeRepository issueTypeRepository) {
        this.issueTypeRepository = issueTypeRepository;
    }

    public List<IssueType> getIssueTypes() {
        if (issueTypes == null) refreshIssueType();
        return issueTypes;
    }

    public void setIssueTypes(List<IssueType> issueTypes) {
        this.issueTypes = issueTypes;
    }

    public void refreshIssueType() {
        this.issueTypes = new ArrayList<>();
        this.findAll().subscribe(issueTypes::add);
        Collections.sort(issueTypes, (a, b) -> a.getIssueTypeWeight() - b.getIssueTypeWeight());
    }

    /**
     * Save a issueType.
     *
     * @param issueType the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueType> save(IssueType issueType) {
        log.debug("Request to save IssueType : {}", issueType);
        return issueTypeRepository.save(issueType);
    }

    /**
     * Update a issueType.
     *
     * @param issueType the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueType> update(IssueType issueType) {
        log.debug("Request to update IssueType : {}", issueType);
        return issueTypeRepository.save(issueType);
    }

    /**
     * Partially update a issueType.
     *
     * @param issueType the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueType> partialUpdate(IssueType issueType) {
        log.debug("Request to partially update IssueType : {}", issueType);

        return issueTypeRepository
            .findById(issueType.getId())
            .map(existingIssueType -> {
                if (issueType.getIssueTypeKey() != null) {
                    existingIssueType.setIssueTypeKey(issueType.getIssueTypeKey());
                }
                if (issueType.getIssueType() != null) {
                    existingIssueType.setIssueType(issueType.getIssueType());
                }

                return existingIssueType;
            })
            .flatMap(issueTypeRepository::save);
    }

    /**
     * Get all the issueTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssueType> findAll() {
        log.debug("Request to get all IssueTypes");
        return issueTypeRepository.findAll();
    }

    /**
     * Returns the number of issueTypes available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issueTypeRepository.count();
    }

    /**
     * Get one issueType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssueType> findOne(Long id) {
        log.debug("Request to get IssueType : {}", id);
        return issueTypeRepository.findById(id);
    }

    /**
     * Delete the issueType by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IssueType : {}", id);
        return issueTypeRepository.deleteById(id);
    }
}
