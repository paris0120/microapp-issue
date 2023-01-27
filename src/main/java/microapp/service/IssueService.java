package microapp.service;

import java.time.Instant;
import java.util.UUID;
import microapp.domain.Issue;
import microapp.repository.IssueRepository;
import microapp.security.AuthoritiesConstants;
import microapp.security.SecurityUtils;
import microapp.service.dto.IssueDTO;
import microapp.service.mapper.IssueMapper;
import microapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Issue}.
 */
@Service
@Transactional
public class IssueService {

    private final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final IssueMapper issueMapper;

    private final IssuePriorityService issuePriorityService;

    private final IssueWorkflowStatusService issueWorkflowStatusService;

    public IssueService(
        IssueRepository issueRepository,
        IssueMapper issueMapper,
        IssuePriorityService issuePriorityService,
        IssueWorkflowStatusService issueWorkflowStatusService
    ) {
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
        this.issuePriorityService = issuePriorityService;
        this.issueWorkflowStatusService = issueWorkflowStatusService;
    }

    /**
     * Save a issue.
     *
     * @param issueDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueDTO> save(IssueDTO issueDTO) {
        return SecurityUtils
            .getCurrentUserLogin()
            .flatMap(username -> {
                issueDTO.setUsername(username);
                issueDTO.setDisplayedUsername(username);
                issueDTO.setIssuePriorityLevel(0);
                issueDTO.setIssueWorkflowStatusKey("OPEN");
                issueDTO.setIssueWorkflowStatus(issueWorkflowStatusService.getIssueWorkflowStatus(issueDTO.getIssueWorkflowStatusKey()));
                issueDTO.setCreated(Instant.now());
                issueDTO.setModified(Instant.now());
                issueDTO.setIssueUuid(UUID.randomUUID());
                log.debug("Request to save Issue : {}", issueDTO);
                return issueRepository.save(issueMapper.toEntity(issueDTO)).map(issueMapper::toDto);
            });
    }

    //    /**
    //     * Update a issue.
    //     *
    //     * @param issueDTO the entity to save.
    //     * @return the persisted entity.
    //     */
    //    public Mono<IssueDTO> update(IssueDTO issueDTO) {
    //        log.debug("Request to update Issue : {}", issueDTO);
    //        return issueRepository.save(issueMapper.toEntity(issueDTO)).map(issueMapper::toDto);
    //    }
    //
    //    /**
    //     * Partially update a issue.
    //     *
    //     * @param issueDTO the entity to update partially.
    //     * @return the persisted entity.
    //     */
    //    public Mono<IssueDTO> partialUpdate(IssueDTO issueDTO) {
    //        log.debug("Request to partially update Issue : {}", issueDTO);
    //
    //        return issueRepository
    //            .findById(issueDTO.getId())
    //            .map(existingIssue -> {
    //                issueMapper.partialUpdate(existingIssue, issueDTO);
    //
    //                return existingIssue;
    //            })
    //            .flatMap(issueRepository::save)
    //            .map(issueMapper::toDto);
    //    }

    private static final String ENTITY_NAME = "issueServerIssue";

    /**
     * Partially update a issue.
     *
     * @param issueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueDTO> updateIssueContent(IssueDTO issueDTO) {
        return SecurityUtils
            .getCurrentUserLogin()
            .flatMap(username -> {
                return issueRepository
                    .findById(issueDTO.getId())
                    .map(existingIssue -> {
                        if (!existingIssue.getUsername().equals(username)) {
                            throw new BadRequestAlertException("Unauthorized modification", ENTITY_NAME, "unauth");
                        }
                        existingIssue.setIssueContent(issueDTO.getIssueContent());
                        existingIssue.setIssueTitle(issueDTO.getIssueTitle());
                        existingIssue.setModified(Instant.now());
                        log.debug("Request to update Issue : {}", issueDTO);
                        return existingIssue;
                    })
                    .flatMap(issueRepository::save)
                    .map(issueMapper::toDto);
            });
    }

    /**
     * Partially update a issue.
     *
     * @param issueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueDTO> updateIssueAdmin(IssueDTO issueDTO) {
        return SecurityUtils
            .hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN)
            .flatMap(isAdmin -> {
                if (!isAdmin) {
                    throw new BadRequestAlertException("Unauthorized modification", ENTITY_NAME, "unauth");
                }
                return issueRepository
                    .findById(issueDTO.getId())
                    .map(existingIssue -> {
                        existingIssue.setIssueWorkflowStatusKey(issueDTO.getIssueWorkflowStatusKey());
                        existingIssue.setIssueWorkflowStatus(
                            issueWorkflowStatusService.getIssueWorkflowStatus(existingIssue.getIssueWorkflowStatusKey())
                        );
                        existingIssue.setIssuePriorityLevel(issueDTO.getIssuePriorityLevel());
                        existingIssue.setIssueType(issueDTO.getIssueType());
                        existingIssue.setIssueContent(issueDTO.getIssueContent());
                        existingIssue.setIssueTitle(issueDTO.getIssueTitle());
                        existingIssue.setModified(Instant.now());
                        log.debug("Request to update Issue : {}", issueDTO);
                        return existingIssue;
                    })
                    .flatMap(issueRepository::save)
                    .map(issueMapper::toDto);
            });
    }

    /**
     * Get all the issues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Issues");
        return issueRepository.findAllBy(pageable).map(issueMapper::toDto);
    }

    /**
     * Returns the number of issues available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issueRepository.count();
    }

    /**
     * Get one issue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssueDTO> findOne(Long id) {
        log.debug("Request to get Issue : {}", id);
        return issueRepository.findById(id).map(issueMapper::toDto);
    }

    /**
     * Delete the issue by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Issue : {}", id);
        return issueRepository.deleteById(id);
    }
}
