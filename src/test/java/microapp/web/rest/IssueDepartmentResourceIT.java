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
import microapp.domain.IssueDepartment;
import microapp.repository.EntityManager;
import microapp.repository.IssueDepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link IssueDepartmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class IssueDepartmentResourceIT {

    private static final String DEFAULT_ISSUE_DEPARTMENT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_DEPARTMENT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_DEPARTMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/issue-departments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssueDepartmentRepository issueDepartmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private IssueDepartment issueDepartment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueDepartment createEntity(EntityManager em) {
        IssueDepartment issueDepartment = new IssueDepartment()
            .issueDepartmentKey(DEFAULT_ISSUE_DEPARTMENT_KEY)
            .issueDepartment(DEFAULT_ISSUE_DEPARTMENT);
        return issueDepartment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueDepartment createUpdatedEntity(EntityManager em) {
        IssueDepartment issueDepartment = new IssueDepartment()
            .issueDepartmentKey(UPDATED_ISSUE_DEPARTMENT_KEY)
            .issueDepartment(UPDATED_ISSUE_DEPARTMENT);
        return issueDepartment;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(IssueDepartment.class).block();
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
        issueDepartment = createEntity(em);
    }

    @Test
    void createIssueDepartment() throws Exception {
        int databaseSizeBeforeCreate = issueDepartmentRepository.findAll().collectList().block().size();
        // Create the IssueDepartment
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        IssueDepartment testIssueDepartment = issueDepartmentList.get(issueDepartmentList.size() - 1);
        assertThat(testIssueDepartment.getIssueDepartmentKey()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT_KEY);
        assertThat(testIssueDepartment.getIssueDepartment()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT);
    }

    @Test
    void createIssueDepartmentWithExistingId() throws Exception {
        // Create the IssueDepartment with an existing ID
        issueDepartment.setId(1L);

        int databaseSizeBeforeCreate = issueDepartmentRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkIssueDepartmentKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueDepartmentRepository.findAll().collectList().block().size();
        // set the field null
        issueDepartment.setIssueDepartmentKey(null);

        // Create the IssueDepartment, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueDepartmentRepository.findAll().collectList().block().size();
        // set the field null
        issueDepartment.setIssueDepartment(null);

        // Create the IssueDepartment, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllIssueDepartmentsAsStream() {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        List<IssueDepartment> issueDepartmentList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(IssueDepartment.class)
            .getResponseBody()
            .filter(issueDepartment::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(issueDepartmentList).isNotNull();
        assertThat(issueDepartmentList).hasSize(1);
        IssueDepartment testIssueDepartment = issueDepartmentList.get(0);
        assertThat(testIssueDepartment.getIssueDepartmentKey()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT_KEY);
        assertThat(testIssueDepartment.getIssueDepartment()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT);
    }

    @Test
    void getAllIssueDepartments() {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        // Get all the issueDepartmentList
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
            .value(hasItem(issueDepartment.getId().intValue()))
            .jsonPath("$.[*].issueDepartmentKey")
            .value(hasItem(DEFAULT_ISSUE_DEPARTMENT_KEY))
            .jsonPath("$.[*].issueDepartment")
            .value(hasItem(DEFAULT_ISSUE_DEPARTMENT));
    }

    @Test
    void getIssueDepartment() {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        // Get the issueDepartment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, issueDepartment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(issueDepartment.getId().intValue()))
            .jsonPath("$.issueDepartmentKey")
            .value(is(DEFAULT_ISSUE_DEPARTMENT_KEY))
            .jsonPath("$.issueDepartment")
            .value(is(DEFAULT_ISSUE_DEPARTMENT));
    }

    @Test
    void getNonExistingIssueDepartment() {
        // Get the issueDepartment
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingIssueDepartment() throws Exception {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();

        // Update the issueDepartment
        IssueDepartment updatedIssueDepartment = issueDepartmentRepository.findById(issueDepartment.getId()).block();
        updatedIssueDepartment.issueDepartmentKey(UPDATED_ISSUE_DEPARTMENT_KEY).issueDepartment(UPDATED_ISSUE_DEPARTMENT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedIssueDepartment.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedIssueDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
        IssueDepartment testIssueDepartment = issueDepartmentList.get(issueDepartmentList.size() - 1);
        assertThat(testIssueDepartment.getIssueDepartmentKey()).isEqualTo(UPDATED_ISSUE_DEPARTMENT_KEY);
        assertThat(testIssueDepartment.getIssueDepartment()).isEqualTo(UPDATED_ISSUE_DEPARTMENT);
    }

    @Test
    void putNonExistingIssueDepartment() throws Exception {
        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();
        issueDepartment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueDepartment.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchIssueDepartment() throws Exception {
        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();
        issueDepartment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamIssueDepartment() throws Exception {
        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();
        issueDepartment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateIssueDepartmentWithPatch() throws Exception {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();

        // Update the issueDepartment using partial update
        IssueDepartment partialUpdatedIssueDepartment = new IssueDepartment();
        partialUpdatedIssueDepartment.setId(issueDepartment.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
        IssueDepartment testIssueDepartment = issueDepartmentList.get(issueDepartmentList.size() - 1);
        assertThat(testIssueDepartment.getIssueDepartmentKey()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT_KEY);
        assertThat(testIssueDepartment.getIssueDepartment()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT);
    }

    @Test
    void fullUpdateIssueDepartmentWithPatch() throws Exception {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();

        // Update the issueDepartment using partial update
        IssueDepartment partialUpdatedIssueDepartment = new IssueDepartment();
        partialUpdatedIssueDepartment.setId(issueDepartment.getId());

        partialUpdatedIssueDepartment.issueDepartmentKey(UPDATED_ISSUE_DEPARTMENT_KEY).issueDepartment(UPDATED_ISSUE_DEPARTMENT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
        IssueDepartment testIssueDepartment = issueDepartmentList.get(issueDepartmentList.size() - 1);
        assertThat(testIssueDepartment.getIssueDepartmentKey()).isEqualTo(UPDATED_ISSUE_DEPARTMENT_KEY);
        assertThat(testIssueDepartment.getIssueDepartment()).isEqualTo(UPDATED_ISSUE_DEPARTMENT);
    }

    @Test
    void patchNonExistingIssueDepartment() throws Exception {
        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();
        issueDepartment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, issueDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchIssueDepartment() throws Exception {
        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();
        issueDepartment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamIssueDepartment() throws Exception {
        int databaseSizeBeforeUpdate = issueDepartmentRepository.findAll().collectList().block().size();
        issueDepartment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueDepartment))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueDepartment in the database
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteIssueDepartment() {
        // Initialize the database
        issueDepartmentRepository.save(issueDepartment).block();

        int databaseSizeBeforeDelete = issueDepartmentRepository.findAll().collectList().block().size();

        // Delete the issueDepartment
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, issueDepartment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<IssueDepartment> issueDepartmentList = issueDepartmentRepository.findAll().collectList().block();
        assertThat(issueDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
