package microapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import microapp.domain.IssuePriority;
import microapp.repository.IssuePriorityRepository;
import microapp.service.IssuePriorityService;
import microapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link microapp.domain.IssuePriority}.
 */
@RestController
@RequestMapping("/api")
public class IssuePriorityResource {

    private final Logger log = LoggerFactory.getLogger(IssuePriorityResource.class);

    private static final String ENTITY_NAME = "issueServerIssuePriority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssuePriorityService issuePriorityService;

    private final IssuePriorityRepository issuePriorityRepository;

    public IssuePriorityResource(IssuePriorityService issuePriorityService, IssuePriorityRepository issuePriorityRepository) {
        this.issuePriorityService = issuePriorityService;
        this.issuePriorityRepository = issuePriorityRepository;
    }

    /**
     * {@code POST  /issue-priorities} : Create a new issuePriority.
     *
     * @param issuePriority the issuePriority to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issuePriority, or with status {@code 400 (Bad Request)} if the issuePriority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-priorities")
    public Mono<ResponseEntity<IssuePriority>> createIssuePriority(@Valid @RequestBody IssuePriority issuePriority)
        throws URISyntaxException {
        log.debug("REST request to save IssuePriority : {}", issuePriority);
        if (issuePriority.getId() != null) {
            throw new BadRequestAlertException("A new issuePriority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return issuePriorityService
            .save(issuePriority)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/issue-priorities/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /issue-priorities/:id} : Updates an existing issuePriority.
     *
     * @param id the id of the issuePriority to save.
     * @param issuePriority the issuePriority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issuePriority,
     * or with status {@code 400 (Bad Request)} if the issuePriority is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issuePriority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-priorities/{id}")
    public Mono<ResponseEntity<IssuePriority>> updateIssuePriority(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IssuePriority issuePriority
    ) throws URISyntaxException {
        log.debug("REST request to update IssuePriority : {}, {}", id, issuePriority);
        if (issuePriority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issuePriority.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issuePriorityRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return issuePriorityService
                    .update(issuePriority)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /issue-priorities/:id} : Partial updates given fields of an existing issuePriority, field will ignore if it is null
     *
     * @param id the id of the issuePriority to save.
     * @param issuePriority the issuePriority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issuePriority,
     * or with status {@code 400 (Bad Request)} if the issuePriority is not valid,
     * or with status {@code 404 (Not Found)} if the issuePriority is not found,
     * or with status {@code 500 (Internal Server Error)} if the issuePriority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issue-priorities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<IssuePriority>> partialUpdateIssuePriority(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IssuePriority issuePriority
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssuePriority partially : {}, {}", id, issuePriority);
        if (issuePriority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issuePriority.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issuePriorityRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<IssuePriority> result = issuePriorityService.partialUpdate(issuePriority);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /issue-priorities} : get all the issuePriorities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issuePriorities in body.
     */
    @GetMapping("/issue-priorities")
    public Mono<List<IssuePriority>> getAllIssuePriorities() {
        log.debug("REST request to get all IssuePriorities");
        return issuePriorityService.findAll().collectList();
    }

    /**
     * {@code GET  /issue-priorities} : get all the issuePriorities as a stream.
     * @return the {@link Flux} of issuePriorities.
     */
    @GetMapping(value = "/issue-priorities", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<IssuePriority> getAllIssuePrioritiesAsStream() {
        log.debug("REST request to get all IssuePriorities as a stream");
        return issuePriorityService.findAll();
    }

    /**
     * {@code GET  /issue-priorities/:id} : get the "id" issuePriority.
     *
     * @param id the id of the issuePriority to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issuePriority, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-priorities/{id}")
    public Mono<ResponseEntity<IssuePriority>> getIssuePriority(@PathVariable Long id) {
        log.debug("REST request to get IssuePriority : {}", id);
        Mono<IssuePriority> issuePriority = issuePriorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issuePriority);
    }

    /**
     * {@code DELETE  /issue-priorities/:id} : delete the "id" issuePriority.
     *
     * @param id the id of the issuePriority to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-priorities/{id}")
    public Mono<ResponseEntity<Void>> deleteIssuePriority(@PathVariable Long id) {
        log.debug("REST request to delete IssuePriority : {}", id);
        return issuePriorityService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
