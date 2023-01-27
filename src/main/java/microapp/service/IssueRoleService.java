package microapp.service;

import java.util.List;
import microapp.domain.IssueRole;
import microapp.repository.IssueRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link IssueRole}.
 */
@Service
@Transactional
public class IssueRoleService {

    private final Logger log = LoggerFactory.getLogger(IssueRoleService.class);

    private final IssueRoleRepository issueRoleRepository;

    public IssueRoleService(IssueRoleRepository issueRoleRepository) {
        this.issueRoleRepository = issueRoleRepository;
    }

    /**
     * Save a issueRole.
     *
     * @param issueRole the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueRole> save(IssueRole issueRole) {
        log.debug("Request to save IssueRole : {}", issueRole);
        return issueRoleRepository.save(issueRole);
    }

    /**
     * Update a issueRole.
     *
     * @param issueRole the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueRole> update(IssueRole issueRole) {
        log.debug("Request to update IssueRole : {}", issueRole);
        return issueRoleRepository.save(issueRole);
    }

    /**
     * Partially update a issueRole.
     *
     * @param issueRole the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueRole> partialUpdate(IssueRole issueRole) {
        log.debug("Request to partially update IssueRole : {}", issueRole);

        return issueRoleRepository
            .findById(issueRole.getId())
            .map(existingIssueRole -> {
                if (issueRole.getIssueRoleKey() != null) {
                    existingIssueRole.setIssueRoleKey(issueRole.getIssueRoleKey());
                }
                if (issueRole.getIssueRole() != null) {
                    existingIssueRole.setIssueRole(issueRole.getIssueRole());
                }

                return existingIssueRole;
            })
            .flatMap(issueRoleRepository::save);
    }

    /**
     * Get all the issueRoles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssueRole> findAll() {
        log.debug("Request to get all IssueRoles");
        return issueRoleRepository.findAll();
    }

    /**
     * Returns the number of issueRoles available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issueRoleRepository.count();
    }

    /**
     * Get one issueRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssueRole> findOne(Long id) {
        log.debug("Request to get IssueRole : {}", id);
        return issueRoleRepository.findById(id);
    }

    /**
     * Delete the issueRole by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IssueRole : {}", id);
        return issueRoleRepository.deleteById(id);
    }
}
