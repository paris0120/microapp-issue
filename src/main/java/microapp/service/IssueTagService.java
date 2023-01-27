package microapp.service;

import java.util.List;
import microapp.domain.IssueTag;
import microapp.repository.IssueTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link IssueTag}.
 */
@Service
@Transactional
public class IssueTagService {

    private final Logger log = LoggerFactory.getLogger(IssueTagService.class);

    private final IssueTagRepository issueTagRepository;

    public IssueTagService(IssueTagRepository issueTagRepository) {
        this.issueTagRepository = issueTagRepository;
    }

    /**
     * Save a issueTag.
     *
     * @param issueTag the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueTag> save(IssueTag issueTag) {
        log.debug("Request to save IssueTag : {}", issueTag);
        return issueTagRepository.save(issueTag);
    }

    /**
     * Update a issueTag.
     *
     * @param issueTag the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueTag> update(IssueTag issueTag) {
        log.debug("Request to update IssueTag : {}", issueTag);
        return issueTagRepository.save(issueTag);
    }

    /**
     * Partially update a issueTag.
     *
     * @param issueTag the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueTag> partialUpdate(IssueTag issueTag) {
        log.debug("Request to partially update IssueTag : {}", issueTag);

        return issueTagRepository
            .findById(issueTag.getId())
            .map(existingIssueTag -> {
                if (issueTag.getIssueId() != null) {
                    existingIssueTag.setIssueId(issueTag.getIssueId());
                }
                if (issueTag.getIssueUuid() != null) {
                    existingIssueTag.setIssueUuid(issueTag.getIssueUuid());
                }
                if (issueTag.getTag() != null) {
                    existingIssueTag.setTag(issueTag.getTag());
                }

                return existingIssueTag;
            })
            .flatMap(issueTagRepository::save);
    }

    /**
     * Get all the issueTags.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssueTag> findAll() {
        log.debug("Request to get all IssueTags");
        return issueTagRepository.findAll();
    }

    /**
     * Returns the number of issueTags available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issueTagRepository.count();
    }

    /**
     * Get one issueTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssueTag> findOne(Long id) {
        log.debug("Request to get IssueTag : {}", id);
        return issueTagRepository.findById(id);
    }

    /**
     * Delete the issueTag by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IssueTag : {}", id);
        return issueTagRepository.deleteById(id);
    }
}
