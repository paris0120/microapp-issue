package microapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import microapp.IntegrationTest;
import microapp.domain.IssueType;
import microapp.repository.EntityManager;
import microapp.repository.IssueTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link IssueTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class IssueTypeResourceIT {

    private static final String DEFAULT_ISSUE_TYPE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_TYPE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/issue-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssueTypeRepository issueTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private IssueType issueType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueType createEntity(EntityManager em) {
        IssueType issueType = new IssueType().issueTypeKey(DEFAULT_ISSUE_TYPE_KEY).issueType(DEFAULT_ISSUE_TYPE);
        return issueType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueType createUpdatedEntity(EntityManager em) {
        IssueType issueType = new IssueType().issueTypeKey(UPDATED_ISSUE_TYPE_KEY).issueType(UPDATED_ISSUE_TYPE);
        return issueType;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(IssueType.class).block();
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
        issueType = createEntity(em);
    }

    @Test
    void createIssueType() throws Exception {
        int databaseSizeBeforeCreate = issueTypeRepository.findAll().collectList().block().size();
        // Create the IssueType
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IssueType testIssueType = issueTypeList.get(issueTypeList.size() - 1);
        assertThat(testIssueType.getIssueTypeKey()).isEqualTo(DEFAULT_ISSUE_TYPE_KEY);
        assertThat(testIssueType.getIssueType()).isEqualTo(DEFAULT_ISSUE_TYPE);
    }

    @Test
    void createIssueTypeWithExistingId() throws Exception {
        // Create the IssueType with an existing ID
        issueType.setId(1L);

        int databaseSizeBeforeCreate = issueTypeRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkIssueTypeKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueTypeRepository.findAll().collectList().block().size();
        // set the field null
        issueType.setIssueTypeKey(null);

        // Create the IssueType, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueTypeRepository.findAll().collectList().block().size();
        // set the field null
        issueType.setIssueType(null);

        // Create the IssueType, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllIssueTypesAsStream() {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        List<IssueType> issueTypeList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(IssueType.class)
            .getResponseBody()
            .filter(issueType::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(issueTypeList).isNotNull();
        assertThat(issueTypeList).hasSize(1);
        IssueType testIssueType = issueTypeList.get(0);
        assertThat(testIssueType.getIssueTypeKey()).isEqualTo(DEFAULT_ISSUE_TYPE_KEY);
        assertThat(testIssueType.getIssueType()).isEqualTo(DEFAULT_ISSUE_TYPE);
    }

    @Test
    void getAllIssueTypes() {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        // Get all the issueTypeList
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
            .value(hasItem(issueType.getId().intValue()))
            .jsonPath("$.[*].issueTypeKey")
            .value(hasItem(DEFAULT_ISSUE_TYPE_KEY))
            .jsonPath("$.[*].issueType")
            .value(hasItem(DEFAULT_ISSUE_TYPE));
    }

    @Test
    void getIssueType() {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        // Get the issueType
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, issueType.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(issueType.getId().intValue()))
            .jsonPath("$.issueTypeKey")
            .value(is(DEFAULT_ISSUE_TYPE_KEY))
            .jsonPath("$.issueType")
            .value(is(DEFAULT_ISSUE_TYPE));
    }

    @Test
    void getNonExistingIssueType() {
        // Get the issueType
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingIssueType() throws Exception {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();

        // Update the issueType
        IssueType updatedIssueType = issueTypeRepository.findById(issueType.getId()).block();
        updatedIssueType.issueTypeKey(UPDATED_ISSUE_TYPE_KEY).issueType(UPDATED_ISSUE_TYPE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedIssueType.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedIssueType))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
        IssueType testIssueType = issueTypeList.get(issueTypeList.size() - 1);
        assertThat(testIssueType.getIssueTypeKey()).isEqualTo(UPDATED_ISSUE_TYPE_KEY);
        assertThat(testIssueType.getIssueType()).isEqualTo(UPDATED_ISSUE_TYPE);
    }

    @Test
    void putNonExistingIssueType() throws Exception {
        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();
        issueType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueType.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchIssueType() throws Exception {
        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();
        issueType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamIssueType() throws Exception {
        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();
        issueType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateIssueTypeWithPatch() throws Exception {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();

        // Update the issueType using partial update
        IssueType partialUpdatedIssueType = new IssueType();
        partialUpdatedIssueType.setId(issueType.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueType.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueType))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
        IssueType testIssueType = issueTypeList.get(issueTypeList.size() - 1);
        assertThat(testIssueType.getIssueTypeKey()).isEqualTo(DEFAULT_ISSUE_TYPE_KEY);
        assertThat(testIssueType.getIssueType()).isEqualTo(DEFAULT_ISSUE_TYPE);
    }

    @Test
    void fullUpdateIssueTypeWithPatch() throws Exception {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();

        // Update the issueType using partial update
        IssueType partialUpdatedIssueType = new IssueType();
        partialUpdatedIssueType.setId(issueType.getId());

        partialUpdatedIssueType.issueTypeKey(UPDATED_ISSUE_TYPE_KEY).issueType(UPDATED_ISSUE_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueType.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueType))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
        IssueType testIssueType = issueTypeList.get(issueTypeList.size() - 1);
        assertThat(testIssueType.getIssueTypeKey()).isEqualTo(UPDATED_ISSUE_TYPE_KEY);
        assertThat(testIssueType.getIssueType()).isEqualTo(UPDATED_ISSUE_TYPE);
    }

    @Test
    void patchNonExistingIssueType() throws Exception {
        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();
        issueType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, issueType.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchIssueType() throws Exception {
        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();
        issueType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamIssueType() throws Exception {
        int databaseSizeBeforeUpdate = issueTypeRepository.findAll().collectList().block().size();
        issueType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueType))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueType in the database
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteIssueType() {
        // Initialize the database
        issueTypeRepository.save(issueType).block();

        int databaseSizeBeforeDelete = issueTypeRepository.findAll().collectList().block().size();

        // Delete the issueType
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, issueType.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<IssueType> issueTypeList = issueTypeRepository.findAll().collectList().block();
        assertThat(issueTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
