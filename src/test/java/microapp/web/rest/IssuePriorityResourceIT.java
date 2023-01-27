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
import microapp.domain.IssuePriority;
import microapp.repository.EntityManager;
import microapp.repository.IssuePriorityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link IssuePriorityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class IssuePriorityResourceIT {

    private static final String DEFAULT_ISSUE_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_PRIORITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISSUE_PRIORITY_LEVEL = 1;
    private static final Integer UPDATED_ISSUE_PRIORITY_LEVEL = 2;

    private static final String ENTITY_API_URL = "/api/issue-priorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssuePriorityRepository issuePriorityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private IssuePriority issuePriority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssuePriority createEntity(EntityManager em) {
        IssuePriority issuePriority = new IssuePriority()
            .issuePriority(DEFAULT_ISSUE_PRIORITY)
            .issuePriorityLevel(DEFAULT_ISSUE_PRIORITY_LEVEL);
        return issuePriority;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssuePriority createUpdatedEntity(EntityManager em) {
        IssuePriority issuePriority = new IssuePriority()
            .issuePriority(UPDATED_ISSUE_PRIORITY)
            .issuePriorityLevel(UPDATED_ISSUE_PRIORITY_LEVEL);
        return issuePriority;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(IssuePriority.class).block();
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
        issuePriority = createEntity(em);
    }

    @Test
    void createIssuePriority() throws Exception {
        int databaseSizeBeforeCreate = issuePriorityRepository.findAll().collectList().block().size();
        // Create the IssuePriority
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeCreate + 1);
        IssuePriority testIssuePriority = issuePriorityList.get(issuePriorityList.size() - 1);
        assertThat(testIssuePriority.getIssuePriority()).isEqualTo(DEFAULT_ISSUE_PRIORITY);
        assertThat(testIssuePriority.getIssuePriorityLevel()).isEqualTo(DEFAULT_ISSUE_PRIORITY_LEVEL);
    }

    @Test
    void createIssuePriorityWithExistingId() throws Exception {
        // Create the IssuePriority with an existing ID
        issuePriority.setId(1L);

        int databaseSizeBeforeCreate = issuePriorityRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkIssuePriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = issuePriorityRepository.findAll().collectList().block().size();
        // set the field null
        issuePriority.setIssuePriority(null);

        // Create the IssuePriority, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssuePriorityLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = issuePriorityRepository.findAll().collectList().block().size();
        // set the field null
        issuePriority.setIssuePriorityLevel(null);

        // Create the IssuePriority, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllIssuePrioritiesAsStream() {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        List<IssuePriority> issuePriorityList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(IssuePriority.class)
            .getResponseBody()
            .filter(issuePriority::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(issuePriorityList).isNotNull();
        assertThat(issuePriorityList).hasSize(1);
        IssuePriority testIssuePriority = issuePriorityList.get(0);
        assertThat(testIssuePriority.getIssuePriority()).isEqualTo(DEFAULT_ISSUE_PRIORITY);
        assertThat(testIssuePriority.getIssuePriorityLevel()).isEqualTo(DEFAULT_ISSUE_PRIORITY_LEVEL);
    }

    @Test
    void getAllIssuePriorities() {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        // Get all the issuePriorityList
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
            .value(hasItem(issuePriority.getId().intValue()))
            .jsonPath("$.[*].issuePriority")
            .value(hasItem(DEFAULT_ISSUE_PRIORITY))
            .jsonPath("$.[*].issuePriorityLevel")
            .value(hasItem(DEFAULT_ISSUE_PRIORITY_LEVEL));
    }

    @Test
    void getIssuePriority() {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        // Get the issuePriority
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, issuePriority.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(issuePriority.getId().intValue()))
            .jsonPath("$.issuePriority")
            .value(is(DEFAULT_ISSUE_PRIORITY))
            .jsonPath("$.issuePriorityLevel")
            .value(is(DEFAULT_ISSUE_PRIORITY_LEVEL));
    }

    @Test
    void getNonExistingIssuePriority() {
        // Get the issuePriority
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingIssuePriority() throws Exception {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();

        // Update the issuePriority
        IssuePriority updatedIssuePriority = issuePriorityRepository.findById(issuePriority.getId()).block();
        updatedIssuePriority.issuePriority(UPDATED_ISSUE_PRIORITY).issuePriorityLevel(UPDATED_ISSUE_PRIORITY_LEVEL);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedIssuePriority.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedIssuePriority))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
        IssuePriority testIssuePriority = issuePriorityList.get(issuePriorityList.size() - 1);
        assertThat(testIssuePriority.getIssuePriority()).isEqualTo(UPDATED_ISSUE_PRIORITY);
        assertThat(testIssuePriority.getIssuePriorityLevel()).isEqualTo(UPDATED_ISSUE_PRIORITY_LEVEL);
    }

    @Test
    void putNonExistingIssuePriority() throws Exception {
        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();
        issuePriority.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issuePriority.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchIssuePriority() throws Exception {
        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();
        issuePriority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamIssuePriority() throws Exception {
        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();
        issuePriority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateIssuePriorityWithPatch() throws Exception {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();

        // Update the issuePriority using partial update
        IssuePriority partialUpdatedIssuePriority = new IssuePriority();
        partialUpdatedIssuePriority.setId(issuePriority.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssuePriority.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssuePriority))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
        IssuePriority testIssuePriority = issuePriorityList.get(issuePriorityList.size() - 1);
        assertThat(testIssuePriority.getIssuePriority()).isEqualTo(DEFAULT_ISSUE_PRIORITY);
        assertThat(testIssuePriority.getIssuePriorityLevel()).isEqualTo(DEFAULT_ISSUE_PRIORITY_LEVEL);
    }

    @Test
    void fullUpdateIssuePriorityWithPatch() throws Exception {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();

        // Update the issuePriority using partial update
        IssuePriority partialUpdatedIssuePriority = new IssuePriority();
        partialUpdatedIssuePriority.setId(issuePriority.getId());

        partialUpdatedIssuePriority.issuePriority(UPDATED_ISSUE_PRIORITY).issuePriorityLevel(UPDATED_ISSUE_PRIORITY_LEVEL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssuePriority.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssuePriority))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
        IssuePriority testIssuePriority = issuePriorityList.get(issuePriorityList.size() - 1);
        assertThat(testIssuePriority.getIssuePriority()).isEqualTo(UPDATED_ISSUE_PRIORITY);
        assertThat(testIssuePriority.getIssuePriorityLevel()).isEqualTo(UPDATED_ISSUE_PRIORITY_LEVEL);
    }

    @Test
    void patchNonExistingIssuePriority() throws Exception {
        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();
        issuePriority.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, issuePriority.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchIssuePriority() throws Exception {
        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();
        issuePriority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamIssuePriority() throws Exception {
        int databaseSizeBeforeUpdate = issuePriorityRepository.findAll().collectList().block().size();
        issuePriority.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issuePriority))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssuePriority in the database
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteIssuePriority() {
        // Initialize the database
        issuePriorityRepository.save(issuePriority).block();

        int databaseSizeBeforeDelete = issuePriorityRepository.findAll().collectList().block().size();

        // Delete the issuePriority
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, issuePriority.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<IssuePriority> issuePriorityList = issuePriorityRepository.findAll().collectList().block();
        assertThat(issuePriorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
