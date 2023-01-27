package microapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import microapp.domain.IssueTag;
import microapp.repository.IssueTagRepository;
import microapp.service.IssueTagService;
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
 * REST controller for managing {@link microapp.domain.IssueTag}.
 */
@RestController
@RequestMapping("/api")
public class IssueTagResource {

    private final Logger log = LoggerFactory.getLogger(IssueTagResource.class);

    private static final String ENTITY_NAME = "issueServerIssueTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueTagService issueTagService;

    private final IssueTagRepository issueTagRepository;

    public IssueTagResource(IssueTagService issueTagService, IssueTagRepository issueTagRepository) {
        this.issueTagService = issueTagService;
        this.issueTagRepository = issueTagRepository;
    }

    /**
     * {@code POST  /issue-tags} : Create a new issueTag.
     *
     * @param issueTag the issueTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueTag, or with status {@code 400 (Bad Request)} if the issueTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-tags")
    public Mono<ResponseEntity<IssueTag>> createIssueTag(@Valid @RequestBody IssueTag issueTag) throws URISyntaxException {
        log.debug("REST request to save IssueTag : {}", issueTag);
        if (issueTag.getId() != null) {
            throw new BadRequestAlertException("A new issueTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return issueTagService
            .save(issueTag)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/issue-tags/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /issue-tags/:id} : Updates an existing issueTag.
     *
     * @param id the id of the issueTag to save.
     * @param issueTag the issueTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueTag,
     * or with status {@code 400 (Bad Request)} if the issueTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-tags/{id}")
    public Mono<ResponseEntity<IssueTag>> updateIssueTag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IssueTag issueTag
    ) throws URISyntaxException {
        log.debug("REST request to update IssueTag : {}, {}", id, issueTag);
        if (issueTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueTag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueTagRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return issueTagService
                    .update(issueTag)
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
     * {@code PATCH  /issue-tags/:id} : Partial updates given fields of an existing issueTag, field will ignore if it is null
     *
     * @param id the id of the issueTag to save.
     * @param issueTag the issueTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueTag,
     * or with status {@code 400 (Bad Request)} if the issueTag is not valid,
     * or with status {@code 404 (Not Found)} if the issueTag is not found,
     * or with status {@code 500 (Internal Server Error)} if the issueTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issue-tags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<IssueTag>> partialUpdateIssueTag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IssueTag issueTag
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssueTag partially : {}, {}", id, issueTag);
        if (issueTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueTag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return issueTagRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<IssueTag> result = issueTagService.partialUpdate(issueTag);

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
     * {@code GET  /issue-tags} : get all the issueTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueTags in body.
     */
    @GetMapping("/issue-tags")
    public Mono<List<IssueTag>> getAllIssueTags() {
        log.debug("REST request to get all IssueTags");
        return issueTagService.findAll().collectList();
    }

    /**
     * {@code GET  /issue-tags} : get all the issueTags as a stream.
     * @return the {@link Flux} of issueTags.
     */
    @GetMapping(value = "/issue-tags", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<IssueTag> getAllIssueTagsAsStream() {
        log.debug("REST request to get all IssueTags as a stream");
        return issueTagService.findAll();
    }

    /**
     * {@code GET  /issue-tags/:id} : get the "id" issueTag.
     *
     * @param id the id of the issueTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-tags/{id}")
    public Mono<ResponseEntity<IssueTag>> getIssueTag(@PathVariable Long id) {
        log.debug("REST request to get IssueTag : {}", id);
        Mono<IssueTag> issueTag = issueTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueTag);
    }

    /**
     * {@code DELETE  /issue-tags/:id} : delete the "id" issueTag.
     *
     * @param id the id of the issueTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-tags/{id}")
    public Mono<ResponseEntity<Void>> deleteIssueTag(@PathVariable Long id) {
        log.debug("REST request to delete IssueTag : {}", id);
        return issueTagService
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
