package microapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import microapp.domain.IssueDepartment;
import microapp.repository.IssueDepartmentRepository;
import microapp.service.IssueDepartmentService;
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
 * REST controller for managing {@link microapp.domain.IssueDepartment}.
 */
@RestController
@RequestMapping("/api")
public class IssueDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(IssueDepartmentResource.class);

    private static final String ENTITY_NAME = "issueServerIssueDepartment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueDepartmentService issueDepartmentService;

    private final IssueDepartmentRepository issueDepartmentRepository;

    public IssueDepartmentResource(IssueDepartmentService issueDepartmentService, IssueDepartmentRepository issueDepartmentRepository) {
        this.issueDepartmentService = issueDepartmentService;
        this.issueDepartmentRepository = issueDepartmentRepository;
    }

    /**
     * {@code POST  /issue-departments} : Create a new issueDepartment.
     *
     * @param issueDepartment the issueDepartment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueDepartment, or with status {@code 400 (Bad Request)} if the issueDepartment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-departments")
    public Mono<ResponseEntity<IssueDepartment>> createIssueDepartment(@Valid @RequestBody IssueDepartment issueDepartment)
        throws URISyntaxException {
        log.debug("REST request to save IssueDepartment : {}", issueDepartment);
        if (issueDepartment.getId() != null) {
            throw new BadRequestAlertException("A new issueDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return issueDepartmentService
            .save(issueDepartment)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/issue-departments/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /issue-departments/:id} : Updates an existing issueDepartment.
     *
     * @param id the id of the issueDepartment to save.
     * @param issueDepartment the issueDepartment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueDepartment,
     * or with status {@code 400 (Bad Request)} if the issueDepartment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueDepartment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-departments/{id}")
    public Mono<ResponseEntity<IssueDepartment>> updateIssueDepartment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IssueDepartment issueDepartment
    ) throws URISyntaxException {
        log.debug("REST request to update IssueDepartment : {}, {}", id, issueDepartment);
        if (issueDepartment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueDepartment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueDepartmentRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return issueDepartmentService
                    .update(issueDepartment)
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
     * {@code PATCH  /issue-departments/:id} : Partial updates given fields of an existing issueDepartment, field will ignore if it is null
     *
     * @param id the id of the issueDepartment to save.
     * @param issueDepartment the issueDepartment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueDepartment,
     * or with status {@code 400 (Bad Request)} if the issueDepartment is not valid,
     * or with status {@code 404 (Not Found)} if the issueDepartment is not found,
     * or with status {@code 500 (Internal Server Error)} if the issueDepartment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issue-departments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<IssueDepartment>> partialUpdateIssueDepartment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IssueDepartment issueDepartment
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssueDepartment partially : {}, {}", id, issueDepartment);
        if (issueDepartment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueDepartment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueDepartmentRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<IssueDepartment> result = issueDepartmentService.partialUpdate(issueDepartment);

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
     * {@code GET  /issue-departments} : get all the issueDepartments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueDepartments in body.
     */
    @GetMapping("/issue-departments")
    public Mono<List<IssueDepartment>> getAllIssueDepartments() {
        log.debug("REST request to get all IssueDepartments");
        return issueDepartmentService.findAll().collectList();
    }

    /**
     * {@code GET  /issue-departments} : get all the issueDepartments as a stream.
     * @return the {@link Flux} of issueDepartments.
     */
    @GetMapping(value = "/issue-departments", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<IssueDepartment> getAllIssueDepartmentsAsStream() {
        log.debug("REST request to get all IssueDepartments as a stream");
        return issueDepartmentService.findAll();
    }

    /**
     * {@code GET  /issue-departments/:id} : get the "id" issueDepartment.
     *
     * @param id the id of the issueDepartment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueDepartment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-departments/{id}")
    public Mono<ResponseEntity<IssueDepartment>> getIssueDepartment(@PathVariable Long id) {
        log.debug("REST request to get IssueDepartment : {}", id);
        Mono<IssueDepartment> issueDepartment = issueDepartmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueDepartment);
    }

    /**
     * {@code DELETE  /issue-departments/:id} : delete the "id" issueDepartment.
     *
     * @param id the id of the issueDepartment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-departments/{id}")
    public Mono<ResponseEntity<Void>> deleteIssueDepartment(@PathVariable Long id) {
        log.debug("REST request to delete IssueDepartment : {}", id);
        return issueDepartmentService
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
