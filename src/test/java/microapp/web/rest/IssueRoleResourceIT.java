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
import microapp.domain.IssueRole;
import microapp.repository.EntityManager;
import microapp.repository.IssueRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link IssueRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class IssueRoleResourceIT {

    private static final String DEFAULT_ISSUE_ROLE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_ROLE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_ROLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/issue-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssueRoleRepository issueRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private IssueRole issueRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueRole createEntity(EntityManager em) {
        IssueRole issueRole = new IssueRole().issueRoleKey(DEFAULT_ISSUE_ROLE_KEY).issueRole(DEFAULT_ISSUE_ROLE);
        return issueRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueRole createUpdatedEntity(EntityManager em) {
        IssueRole issueRole = new IssueRole().issueRoleKey(UPDATED_ISSUE_ROLE_KEY).issueRole(UPDATED_ISSUE_ROLE);
        return issueRole;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(IssueRole.class).block();
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
        issueRole = createEntity(em);
    }

    @Test
    void createIssueRole() throws Exception {
        int databaseSizeBeforeCreate = issueRoleRepository.findAll().collectList().block().size();
        // Create the IssueRole
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeCreate + 1);
        IssueRole testIssueRole = issueRoleList.get(issueRoleList.size() - 1);
        assertThat(testIssueRole.getIssueRoleKey()).isEqualTo(DEFAULT_ISSUE_ROLE_KEY);
        assertThat(testIssueRole.getIssueRole()).isEqualTo(DEFAULT_ISSUE_ROLE);
    }

    @Test
    void createIssueRoleWithExistingId() throws Exception {
        // Create the IssueRole with an existing ID
        issueRole.setId(1L);

        int databaseSizeBeforeCreate = issueRoleRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkIssueRoleKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueRoleRepository.findAll().collectList().block().size();
        // set the field null
        issueRole.setIssueRoleKey(null);

        // Create the IssueRole, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueRoleRepository.findAll().collectList().block().size();
        // set the field null
        issueRole.setIssueRole(null);

        // Create the IssueRole, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllIssueRolesAsStream() {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        List<IssueRole> issueRoleList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(IssueRole.class)
            .getResponseBody()
            .filter(issueRole::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(issueRoleList).isNotNull();
        assertThat(issueRoleList).hasSize(1);
        IssueRole testIssueRole = issueRoleList.get(0);
        assertThat(testIssueRole.getIssueRoleKey()).isEqualTo(DEFAULT_ISSUE_ROLE_KEY);
        assertThat(testIssueRole.getIssueRole()).isEqualTo(DEFAULT_ISSUE_ROLE);
    }

    @Test
    void getAllIssueRoles() {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        // Get all the issueRoleList
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
            .value(hasItem(issueRole.getId().intValue()))
            .jsonPath("$.[*].issueRoleKey")
            .value(hasItem(DEFAULT_ISSUE_ROLE_KEY))
            .jsonPath("$.[*].issueRole")
            .value(hasItem(DEFAULT_ISSUE_ROLE));
    }

    @Test
    void getIssueRole() {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        // Get the issueRole
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, issueRole.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(issueRole.getId().intValue()))
            .jsonPath("$.issueRoleKey")
            .value(is(DEFAULT_ISSUE_ROLE_KEY))
            .jsonPath("$.issueRole")
            .value(is(DEFAULT_ISSUE_ROLE));
    }

    @Test
    void getNonExistingIssueRole() {
        // Get the issueRole
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingIssueRole() throws Exception {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();

        // Update the issueRole
        IssueRole updatedIssueRole = issueRoleRepository.findById(issueRole.getId()).block();
        updatedIssueRole.issueRoleKey(UPDATED_ISSUE_ROLE_KEY).issueRole(UPDATED_ISSUE_ROLE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedIssueRole.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedIssueRole))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
        IssueRole testIssueRole = issueRoleList.get(issueRoleList.size() - 1);
        assertThat(testIssueRole.getIssueRoleKey()).isEqualTo(UPDATED_ISSUE_ROLE_KEY);
        assertThat(testIssueRole.getIssueRole()).isEqualTo(UPDATED_ISSUE_ROLE);
    }

    @Test
    void putNonExistingIssueRole() throws Exception {
        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();
        issueRole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueRole.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchIssueRole() throws Exception {
        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();
        issueRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamIssueRole() throws Exception {
        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();
        issueRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateIssueRoleWithPatch() throws Exception {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();

        // Update the issueRole using partial update
        IssueRole partialUpdatedIssueRole = new IssueRole();
        partialUpdatedIssueRole.setId(issueRole.getId());

        partialUpdatedIssueRole.issueRoleKey(UPDATED_ISSUE_ROLE_KEY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueRole.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueRole))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
        IssueRole testIssueRole = issueRoleList.get(issueRoleList.size() - 1);
        assertThat(testIssueRole.getIssueRoleKey()).isEqualTo(UPDATED_ISSUE_ROLE_KEY);
        assertThat(testIssueRole.getIssueRole()).isEqualTo(DEFAULT_ISSUE_ROLE);
    }

    @Test
    void fullUpdateIssueRoleWithPatch() throws Exception {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();

        // Update the issueRole using partial update
        IssueRole partialUpdatedIssueRole = new IssueRole();
        partialUpdatedIssueRole.setId(issueRole.getId());

        partialUpdatedIssueRole.issueRoleKey(UPDATED_ISSUE_ROLE_KEY).issueRole(UPDATED_ISSUE_ROLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueRole.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueRole))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
        IssueRole testIssueRole = issueRoleList.get(issueRoleList.size() - 1);
        assertThat(testIssueRole.getIssueRoleKey()).isEqualTo(UPDATED_ISSUE_ROLE_KEY);
        assertThat(testIssueRole.getIssueRole()).isEqualTo(UPDATED_ISSUE_ROLE);
    }

    @Test
    void patchNonExistingIssueRole() throws Exception {
        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();
        issueRole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, issueRole.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchIssueRole() throws Exception {
        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();
        issueRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamIssueRole() throws Exception {
        int databaseSizeBeforeUpdate = issueRoleRepository.findAll().collectList().block().size();
        issueRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueRole))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueRole in the database
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteIssueRole() {
        // Initialize the database
        issueRoleRepository.save(issueRole).block();

        int databaseSizeBeforeDelete = issueRoleRepository.findAll().collectList().block().size();

        // Delete the issueRole
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, issueRole.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<IssueRole> issueRoleList = issueRoleRepository.findAll().collectList().block();
        assertThat(issueRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
