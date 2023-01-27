package microapp.service;

import microapp.domain.IssueAssignment;
import microapp.repository.IssueAssignmentRepository;
import microapp.service.dto.IssueAssignmentDTO;
import microapp.service.mapper.IssueAssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link IssueAssignment}.
 */
@Service
@Transactional
public class IssueAssignmentService {

    private final Logger log = LoggerFactory.getLogger(IssueAssignmentService.class);

    private final IssueAssignmentRepository issueAssignmentRepository;

    private final IssueAssignmentMapper issueAssignmentMapper;

    public IssueAssignmentService(IssueAssignmentRepository issueAssignmentRepository, IssueAssignmentMapper issueAssignmentMapper) {
        this.issueAssignmentRepository = issueAssignmentRepository;
        this.issueAssignmentMapper = issueAssignmentMapper;
    }

    /**
     * Save a issueAssignment.
     *
     * @param issueAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueAssignmentDTO> save(IssueAssignmentDTO issueAssignmentDTO) {
        log.debug("Request to save IssueAssignment : {}", issueAssignmentDTO);
        return issueAssignmentRepository.save(issueAssignmentMapper.toEntity(issueAssignmentDTO)).map(issueAssignmentMapper::toDto);
    }

    /**
     * Update a issueAssignment.
     *
     * @param issueAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<IssueAssignmentDTO> update(IssueAssignmentDTO issueAssignmentDTO) {
        log.debug("Request to update IssueAssignment : {}", issueAssignmentDTO);
        return issueAssignmentRepository.save(issueAssignmentMapper.toEntity(issueAssignmentDTO)).map(issueAssignmentMapper::toDto);
    }

    /**
     * Partially update a issueAssignment.
     *
     * @param issueAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<IssueAssignmentDTO> partialUpdate(IssueAssignmentDTO issueAssignmentDTO) {
        log.debug("Request to partially update IssueAssignment : {}", issueAssignmentDTO);

        return issueAssignmentRepository
            .findById(issueAssignmentDTO.getId())
            .map(existingIssueAssignment -> {
                issueAssignmentMapper.partialUpdate(existingIssueAssignment, issueAssignmentDTO);

                return existingIssueAssignment;
            })
            .flatMap(issueAssignmentRepository::save)
            .map(issueAssignmentMapper::toDto);
    }

    /**
     * Get all the issueAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<IssueAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IssueAssignments");
        return issueAssignmentRepository.findAllBy(pageable).map(issueAssignmentMapper::toDto);
    }

    /**
     * Returns the number of issueAssignments available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return issueAssignmentRepository.count();
    }

    /**
     * Get one issueAssignment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IssueAssignmentDTO> findOne(Long id) {
        log.debug("Request to get IssueAssignment : {}", id);
        return issueAssignmentRepository.findById(id).map(issueAssignmentMapper::toDto);
    }

    /**
     * Delete the issueAssignment by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete IssueAssignment : {}", id);
        return issueAssignmentRepository.deleteById(id);
    }
}
