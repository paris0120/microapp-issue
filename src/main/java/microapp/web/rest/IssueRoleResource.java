package microapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import microapp.domain.IssueRole;
import microapp.repository.IssueRoleRepository;
import microapp.service.IssueRoleService;
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
 * REST controller for managing {@link microapp.domain.IssueRole}.
 */
@RestController
@RequestMapping("/api")
public class IssueRoleResource {

    private final Logger log = LoggerFactory.getLogger(IssueRoleResource.class);

    private static final String ENTITY_NAME = "issueServerIssueRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueRoleService issueRoleService;

    private final IssueRoleRepository issueRoleRepository;

    public IssueRoleResource(IssueRoleService issueRoleService, IssueRoleRepository issueRoleRepository) {
        this.issueRoleService = issueRoleService;
        this.issueRoleRepository = issueRoleRepository;
    }

    /**
     * {@code POST  /issue-roles} : Create a new issueRole.
     *
     * @param issueRole the issueRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueRole, or with status {@code 400 (Bad Request)} if the issueRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-roles")
    public Mono<ResponseEntity<IssueRole>> createIssueRole(@Valid @RequestBody IssueRole issueRole) throws URISyntaxException {
        log.debug("REST request to save IssueRole : {}", issueRole);
        if (issueRole.getId() != null) {
            throw new BadRequestAlertException("A new issueRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return issueRoleService
            .save(issueRole)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/issue-roles/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /issue-roles/:id} : Updates an existing issueRole.
     *
     * @param id the id of the issueRole to save.
     * @param issueRole the issueRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueRole,
     * or with status {@code 400 (Bad Request)} if the issueRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-roles/{id}")
    public Mono<ResponseEntity<IssueRole>> updateIssueRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IssueRole issueRole
    ) throws URISyntaxException {
        log.debug("REST request to update IssueRole : {}, {}", id, issueRole);
        if (issueRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueRole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueRoleRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return issueRoleService
                    .update(issueRole)
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
     * {@code PATCH  /issue-roles/:id} : Partial updates given fields of an existing issueRole, field will ignore if it is null
     *
     * @param id the id of the issueRole to save.
     * @param issueRole the issueRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueRole,
     * or with status {@code 400 (Bad Request)} if the issueRole is not valid,
     * or with status {@code 404 (Not Found)} if the issueRole is not found,
     * or with status {@code 500 (Internal Server Error)} if the issueRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issue-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<IssueRole>> partialUpdateIssueRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IssueRole issueRole
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssueRole partially : {}, {}", id, issueRole);
        if (issueRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueRole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueRoleRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<IssueRole> result = issueRoleService.partialUpdate(issueRole);

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
     * {@code GET  /issue-roles} : get all the issueRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueRoles in body.
     */
    @GetMapping("/issue-roles")
    public Mono<List<IssueRole>> getAllIssueRoles() {
        log.debug("REST request to get all IssueRoles");
        return issueRoleService.findAll().collectList();
    }

    /**
     * {@code GET  /issue-roles} : get all the issueRoles as a stream.
     * @return the {@link Flux} of issueRoles.
     */
    @GetMapping(value = "/issue-roles", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<IssueRole> getAllIssueRolesAsStream() {
        log.debug("REST request to get all IssueRoles as a stream");
        return issueRoleService.findAll();
    }

    /**
     * {@code GET  /issue-roles/:id} : get the "id" issueRole.
     *
     * @param id the id of the issueRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-roles/{id}")
    public Mono<ResponseEntity<IssueRole>> getIssueRole(@PathVariable Long id) {
        log.debug("REST request to get IssueRole : {}", id);
        Mono<IssueRole> issueRole = issueRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueRole);
    }

    /**
     * {@code DELETE  /issue-roles/:id} : delete the "id" issueRole.
     *
     * @param id the id of the issueRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-roles/{id}")
    public Mono<ResponseEntity<Void>> deleteIssueRole(@PathVariable Long id) {
        log.debug("REST request to delete IssueRole : {}", id);
        return issueRoleService
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
