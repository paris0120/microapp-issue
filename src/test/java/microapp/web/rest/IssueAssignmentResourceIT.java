package microapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import microapp.IntegrationTest;
import microapp.domain.IssueAssignment;
import microapp.repository.EntityManager;
import microapp.repository.IssueAssignmentRepository;
import microapp.service.dto.IssueAssignmentDTO;
import microapp.service.mapper.IssueAssignmentMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link IssueAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class IssueAssignmentResourceIT {

    private static final Long DEFAULT_ISSUE_ID = 1L;
    private static final Long UPDATED_ISSUE_ID = 2L;

    private static final UUID DEFAULT_ISSUE_UUID = UUID.randomUUID();
    private static final UUID UPDATED_ISSUE_UUID = UUID.randomUUID();

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAYED_ISSUE_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAYED_ISSUE_ROLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACCEPTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACCEPTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/issue-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssueAssignmentRepository issueAssignmentRepository;

    @Autowired
    private IssueAssignmentMapper issueAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private IssueAssignment issueAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueAssignment createEntity(EntityManager em) {
        IssueAssignment issueAssignment = new IssueAssignment()
            .issueId(DEFAULT_ISSUE_ID)
            .issueUuid(DEFAULT_ISSUE_UUID)
            .username(DEFAULT_USERNAME)
            .issueAssignmentDisplayedUsername(DEFAULT_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME)
            .issueRole(DEFAULT_ISSUE_ROLE)
            .displayedIssueRole(DEFAULT_DISPLAYED_ISSUE_ROLE)
            .created(DEFAULT_CREATED)
            .modified(DEFAULT_MODIFIED)
            .accepted(DEFAULT_ACCEPTED)
            .deleted(DEFAULT_DELETED);
        return issueAssignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueAssignment createUpdatedEntity(EntityManager em) {
        IssueAssignment issueAssignment = new IssueAssignment()
            .issueId(UPDATED_ISSUE_ID)
            .issueUuid(UPDATED_ISSUE_UUID)
            .username(UPDATED_USERNAME)
            .issueAssignmentDisplayedUsername(UPDATED_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME)
            .issueRole(UPDATED_ISSUE_ROLE)
            .displayedIssueRole(UPDATED_DISPLAYED_ISSUE_ROLE)
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .accepted(UPDATED_ACCEPTED)
            .deleted(UPDATED_DELETED);
        return issueAssignment;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(IssueAssignment.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        issueAssignment = createEntity(em);
    }

    @Test
    void createIssueAssignment() throws Exception {
        int databaseSizeBeforeCreate = issueAssignmentRepository.findAll().collectList().block().size();
        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeCreate + 1);
        IssueAssignment testIssueAssignment = issueAssignmentList.get(issueAssignmentList.size() - 1);
        assertThat(testIssueAssignment.getIssueId()).isEqualTo(DEFAULT_ISSUE_ID);
        assertThat(testIssueAssignment.getIssueUuid()).isEqualTo(DEFAULT_ISSUE_UUID);
        assertThat(testIssueAssignment.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testIssueAssignment.getIssueAssignmentDisplayedUsername()).isEqualTo(DEFAULT_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME);
        assertThat(testIssueAssignment.getIssueRole()).isEqualTo(DEFAULT_ISSUE_ROLE);
        assertThat(testIssueAssignment.getDisplayedIssueRole()).isEqualTo(DEFAULT_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueAssignment.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIssueAssignment.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testIssueAssignment.getAccepted()).isEqualTo(DEFAULT_ACCEPTED);
        assertThat(testIssueAssignment.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    void createIssueAssignmentWithExistingId() throws Exception {
        // Create the IssueAssignment with an existing ID
        issueAssignment.setId(1L);
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        int databaseSizeBeforeCreate = issueAssignmentRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkIssueIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setIssueId(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setIssueUuid(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setUsername(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueAssignmentDisplayedUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setIssueAssignmentDisplayedUsername(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setIssueRole(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDisplayedIssueRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setDisplayedIssueRole(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setCreated(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueAssignmentRepository.findAll().collectList().block().size();
        // set the field null
        issueAssignment.setModified(null);

        // Create the IssueAssignment, which fails.
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllIssueAssignments() {
        // Initialize the database
        issueAssignmentRepository.save(issueAssignment).block();

        // Get all the issueAssignmentList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(issueAssignment.getId().intValue()))
            .jsonPath("$.[*].issueId")
            .value(hasItem(DEFAULT_ISSUE_ID.intValue()))
            .jsonPath("$.[*].issueUuid")
            .value(hasItem(DEFAULT_ISSUE_UUID.toString()))
            .jsonPath("$.[*].username")
            .value(hasItem(DEFAULT_USERNAME))
            .jsonPath("$.[*].issueAssignmentDisplayedUsername")
            .value(hasItem(DEFAULT_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME))
            .jsonPath("$.[*].issueRole")
            .value(hasItem(DEFAULT_ISSUE_ROLE))
            .jsonPath("$.[*].displayedIssueRole")
            .value(hasItem(DEFAULT_DISPLAYED_ISSUE_ROLE))
            .jsonPath("$.[*].created")
            .value(hasItem(DEFAULT_CREATED.toString()))
            .jsonPath("$.[*].modified")
            .value(hasItem(DEFAULT_MODIFIED.toString()))
            .jsonPath("$.[*].accepted")
            .value(hasItem(DEFAULT_ACCEPTED.toString()))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED.toString()));
    }

    @Test
    void getIssueAssignment() {
        // Initialize the database
        issueAssignmentRepository.save(issueAssignment).block();

        // Get the issueAssignment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, issueAssignment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(issueAssignment.getId().intValue()))
            .jsonPath("$.issueId")
            .value(is(DEFAULT_ISSUE_ID.intValue()))
            .jsonPath("$.issueUuid")
            .value(is(DEFAULT_ISSUE_UUID.toString()))
            .jsonPath("$.username")
            .value(is(DEFAULT_USERNAME))
            .jsonPath("$.issueAssignmentDisplayedUsername")
            .value(is(DEFAULT_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME))
            .jsonPath("$.issueRole")
            .value(is(DEFAULT_ISSUE_ROLE))
            .jsonPath("$.displayedIssueRole")
            .value(is(DEFAULT_DISPLAYED_ISSUE_ROLE))
            .jsonPath("$.created")
            .value(is(DEFAULT_CREATED.toString()))
            .jsonPath("$.modified")
            .value(is(DEFAULT_MODIFIED.toString()))
            .jsonPath("$.accepted")
            .value(is(DEFAULT_ACCEPTED.toString()))
            .jsonPath("$.deleted")
            .value(is(DEFAULT_DELETED.toString()));
    }

    @Test
    void getNonExistingIssueAssignment() {
        // Get the issueAssignment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingIssueAssignment() throws Exception {
        // Initialize the database
        issueAssignmentRepository.save(issueAssignment).block();

        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();

        // Update the issueAssignment
        IssueAssignment updatedIssueAssignment = issueAssignmentRepository.findById(issueAssignment.getId()).block();
        updatedIssueAssignment
            .issueId(UPDATED_ISSUE_ID)
            .issueUuid(UPDATED_ISSUE_UUID)
            .username(UPDATED_USERNAME)
            .issueAssignmentDisplayedUsername(UPDATED_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME)
            .issueRole(UPDATED_ISSUE_ROLE)
            .displayedIssueRole(UPDATED_DISPLAYED_ISSUE_ROLE)
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .accepted(UPDATED_ACCEPTED)
            .deleted(UPDATED_DELETED);
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(updatedIssueAssignment);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueAssignmentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
        IssueAssignment testIssueAssignment = issueAssignmentList.get(issueAssignmentList.size() - 1);
        assertThat(testIssueAssignment.getIssueId()).isEqualTo(UPDATED_ISSUE_ID);
        assertThat(testIssueAssignment.getIssueUuid()).isEqualTo(UPDATED_ISSUE_UUID);
        assertThat(testIssueAssignment.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testIssueAssignment.getIssueAssignmentDisplayedUsername()).isEqualTo(UPDATED_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME);
        assertThat(testIssueAssignment.getIssueRole()).isEqualTo(UPDATED_ISSUE_ROLE);
        assertThat(testIssueAssignment.getDisplayedIssueRole()).isEqualTo(UPDATED_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueAssignment.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIssueAssignment.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testIssueAssignment.getAccepted()).isEqualTo(UPDATED_ACCEPTED);
        assertThat(testIssueAssignment.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void putNonExistingIssueAssignment() throws Exception {
        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();
        issueAssignment.setId(count.incrementAndGet());

        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueAssignmentDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchIssueAssignment() throws Exception {
        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();
        issueAssignment.setId(count.incrementAndGet());

        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamIssueAssignment() throws Exception {
        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();
        issueAssignment.setId(count.incrementAndGet());

        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateIssueAssignmentWithPatch() throws Exception {
        // Initialize the database
        issueAssignmentRepository.save(issueAssignment).block();

        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();

        // Update the issueAssignment using partial update
        IssueAssignment partialUpdatedIssueAssignment = new IssueAssignment();
        partialUpdatedIssueAssignment.setId(issueAssignment.getId());

        partialUpdatedIssueAssignment
            .issueUuid(UPDATED_ISSUE_UUID)
            .displayedIssueRole(UPDATED_DISPLAYED_ISSUE_ROLE)
            .modified(UPDATED_MODIFIED)
            .deleted(UPDATED_DELETED);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueAssignment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueAssignment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
        IssueAssignment testIssueAssignment = issueAssignmentList.get(issueAssignmentList.size() - 1);
        assertThat(testIssueAssignment.getIssueId()).isEqualTo(DEFAULT_ISSUE_ID);
        assertThat(testIssueAssignment.getIssueUuid()).isEqualTo(UPDATED_ISSUE_UUID);
        assertThat(testIssueAssignment.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testIssueAssignment.getIssueAssignmentDisplayedUsername()).isEqualTo(DEFAULT_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME);
        assertThat(testIssueAssignment.getIssueRole()).isEqualTo(DEFAULT_ISSUE_ROLE);
        assertThat(testIssueAssignment.getDisplayedIssueRole()).isEqualTo(UPDATED_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueAssignment.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIssueAssignment.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testIssueAssignment.getAccepted()).isEqualTo(DEFAULT_ACCEPTED);
        assertThat(testIssueAssignment.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void fullUpdateIssueAssignmentWithPatch() throws Exception {
        // Initialize the database
        issueAssignmentRepository.save(issueAssignment).block();

        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();

        // Update the issueAssignment using partial update
        IssueAssignment partialUpdatedIssueAssignment = new IssueAssignment();
        partialUpdatedIssueAssignment.setId(issueAssignment.getId());

        partialUpdatedIssueAssignment
            .issueId(UPDATED_ISSUE_ID)
            .issueUuid(UPDATED_ISSUE_UUID)
            .username(UPDATED_USERNAME)
            .issueAssignmentDisplayedUsername(UPDATED_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME)
            .issueRole(UPDATED_ISSUE_ROLE)
            .displayedIssueRole(UPDATED_DISPLAYED_ISSUE_ROLE)
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .accepted(UPDATED_ACCEPTED)
            .deleted(UPDATED_DELETED);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueAssignment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueAssignment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
        IssueAssignment testIssueAssignment = issueAssignmentList.get(issueAssignmentList.size() - 1);
        assertThat(testIssueAssignment.getIssueId()).isEqualTo(UPDATED_ISSUE_ID);
        assertThat(testIssueAssignment.getIssueUuid()).isEqualTo(UPDATED_ISSUE_UUID);
        assertThat(testIssueAssignment.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testIssueAssignment.getIssueAssignmentDisplayedUsername()).isEqualTo(UPDATED_ISSUE_ASSIGNMENT_DISPLAYED_USERNAME);
        assertThat(testIssueAssignment.getIssueRole()).isEqualTo(UPDATED_ISSUE_ROLE);
        assertThat(testIssueAssignment.getDisplayedIssueRole()).isEqualTo(UPDATED_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueAssignment.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIssueAssignment.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testIssueAssignment.getAccepted()).isEqualTo(UPDATED_ACCEPTED);
        assertThat(testIssueAssignment.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void patchNonExistingIssueAssignment() throws Exception {
        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();
        issueAssignment.setId(count.incrementAndGet());

        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, issueAssignmentDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchIssueAssignment() throws Exception {
        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();
        issueAssignment.setId(count.incrementAndGet());

        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamIssueAssignment() throws Exception {
        int databaseSizeBeforeUpdate = issueAssignmentRepository.findAll().collectList().block().size();
        issueAssignment.setId(count.incrementAndGet());

        // Create the IssueAssignment
        IssueAssignmentDTO issueAssignmentDTO = issueAssignmentMapper.toDto(issueAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueAssignmentDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueAssignment in the database
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteIssueAssignment() {
        // Initialize the database
        issueAssignmentRepository.save(issueAssignment).block();

        int databaseSizeBeforeDelete = issueAssignmentRepository.findAll().collectList().block().size();

        // Delete the issueAssignment
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, issueAssignment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<IssueAssignment> issueAssignmentList = issueAssignmentRepository.findAll().collectList().block();
        assertThat(issueAssignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
