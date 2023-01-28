package microapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import microapp.domain.IssueType;
import microapp.repository.IssueTypeRepository;
import microapp.service.IssueTypeService;
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
 * REST controller for managing {@link microapp.domain.IssueType}.
 */
@RestController
@RequestMapping("/api")
public class IssueTypeResource {

    private final Logger log = LoggerFactory.getLogger(IssueTypeResource.class);

    private static final String ENTITY_NAME = "issueServerIssueType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueTypeService issueTypeService;

    private final IssueTypeRepository issueTypeRepository;

    public IssueTypeResource(IssueTypeService issueTypeService, IssueTypeRepository issueTypeRepository) {
        this.issueTypeService = issueTypeService;
        this.issueTypeRepository = issueTypeRepository;
    }

    /**
     * {@code POST  /issue-types} : Create a new issueType.
     *
     * @param issueType the issueType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueType, or with status {@code 400 (Bad Request)} if the issueType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-types")
    public Mono<ResponseEntity<IssueType>> createIssueType(@Valid @RequestBody IssueType issueType) throws URISyntaxException {
        log.debug("REST request to save IssueType : {}", issueType);
        if (issueType.getId() != null) {
            throw new BadRequestAlertException("A new issueType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return issueTypeService
            .save(issueType)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/issue-types/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /issue-types/:id} : Updates an existing issueType.
     *
     * @param id the id of the issueType to save.
     * @param issueType the issueType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueType,
     * or with status {@code 400 (Bad Request)} if the issueType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-types/{id}")
    public Mono<ResponseEntity<IssueType>> updateIssueType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IssueType issueType
    ) throws URISyntaxException {
        log.debug("REST request to update IssueType : {}, {}", id, issueType);
        if (issueType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueTypeRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return issueTypeService
                    .update(issueType)
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
     * {@code PATCH  /issue-types/:id} : Partial updates given fields of an existing issueType, field will ignore if it is null
     *
     * @param id the id of the issueType to save.
     * @param issueType the issueType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueType,
     * or with status {@code 400 (Bad Request)} if the issueType is not valid,
     * or with status {@code 404 (Not Found)} if the issueType is not found,
     * or with status {@code 500 (Internal Server Error)} if the issueType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issue-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<IssueType>> partialUpdateIssueType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IssueType issueType
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssueType partially : {}, {}", id, issueType);
        if (issueType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueTypeRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<IssueType> result = issueTypeService.partialUpdate(issueType);

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
     * {@code GET  /issue-types} : get all the issueTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueTypes in body.
     */
    @GetMapping("/issue-types")
    public Mono<List<IssueType>> getAllIssueTypes() {
        log.debug("REST request to get all IssueTypes");
        return issueTypeService.getIssueTypes().collectList();
    }

    /**
     * {@code GET  /issue-types} : get all the issueTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueTypes in body.
     */
    @GetMapping("/issue-types/refresh")
    public Flux<IssueType> refreshIssueTypes() {
        log.debug("REST request to get all IssueTypes");
        return issueTypeService.getIssueTypes();
    }

    /**
     * {@code GET  /issue-types} : get all the issueTypes as a stream.
     * @return the {@link Flux} of issueTypes.
     */
    @GetMapping(value = "/issue-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<IssueType> getAllIssueTypesAsStream() {
        log.debug("REST request to get all IssueTypes as a stream");
        return issueTypeService.getIssueTypes();
    }

    /**
     * {@code GET  /issue-types/:id} : get the "id" issueType.
     *
     * @param id the id of the issueType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-types/{id}")
    public Mono<ResponseEntity<IssueType>> getIssueType(@PathVariable Long id) {
        log.debug("REST request to get IssueType : {}", id);
        Mono<IssueType> issueType = issueTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueType);
    }

    /**
     * {@code DELETE  /issue-types/:id} : delete the "id" issueType.
     *
     * @param id the id of the issueType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-types/{id}")
    public Mono<ResponseEntity<Void>> deleteIssueType(@PathVariable Long id) {
        log.debug("REST request to delete IssueType : {}", id);
        return issueTypeService
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
