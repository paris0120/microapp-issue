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
import java.util.concurrent.atomic.AtomicLong;
import microapp.IntegrationTest;
import microapp.domain.IssueEmployee;
import microapp.repository.EntityManager;
import microapp.repository.IssueEmployeeRepository;
import microapp.service.dto.IssueEmployeeDTO;
import microapp.service.mapper.IssueEmployeeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link IssueEmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class IssueEmployeeResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_ISSUE_ROLE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_ISSUE_ROLE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_DISPLAYED_ISSUE_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_DISPLAYED_ISSUE_ROLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AVAILABLE = false;
    private static final Boolean UPDATED_IS_AVAILABLE = true;

    private static final Instant DEFAULT_IN_OFFICE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IN_OFFICE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_OFFICE_HOUR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OFFICE_HOUR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_OFFICE_HOUR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OFFICE_HOUR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TIMEZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIMEZONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/issue-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssueEmployeeRepository issueEmployeeRepository;

    @Autowired
    private IssueEmployeeMapper issueEmployeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private IssueEmployee issueEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueEmployee createEntity(EntityManager em) {
        IssueEmployee issueEmployee = new IssueEmployee()
            .username(DEFAULT_USERNAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .issueDepartment(DEFAULT_ISSUE_DEPARTMENT)
            .defaultIssueRoleKey(DEFAULT_DEFAULT_ISSUE_ROLE_KEY)
            .defaultDisplayedIssueRole(DEFAULT_DEFAULT_DISPLAYED_ISSUE_ROLE)
            .isAvailable(DEFAULT_IS_AVAILABLE)
            .inOfficeFrom(DEFAULT_IN_OFFICE_FROM)
            .officeHourFrom(DEFAULT_OFFICE_HOUR_FROM)
            .officeHourTo(DEFAULT_OFFICE_HOUR_TO)
            .timezone(DEFAULT_TIMEZONE)
            .created(DEFAULT_CREATED)
            .modified(DEFAULT_MODIFIED)
            .deleted(DEFAULT_DELETED);
        return issueEmployee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueEmployee createUpdatedEntity(EntityManager em) {
        IssueEmployee issueEmployee = new IssueEmployee()
            .username(UPDATED_USERNAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .issueDepartment(UPDATED_ISSUE_DEPARTMENT)
            .defaultIssueRoleKey(UPDATED_DEFAULT_ISSUE_ROLE_KEY)
            .defaultDisplayedIssueRole(UPDATED_DEFAULT_DISPLAYED_ISSUE_ROLE)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .inOfficeFrom(UPDATED_IN_OFFICE_FROM)
            .officeHourFrom(UPDATED_OFFICE_HOUR_FROM)
            .officeHourTo(UPDATED_OFFICE_HOUR_TO)
            .timezone(UPDATED_TIMEZONE)
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .deleted(UPDATED_DELETED);
        return issueEmployee;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(IssueEmployee.class).block();
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
        issueEmployee = createEntity(em);
    }

    @Test
    void createIssueEmployee() throws Exception {
        int databaseSizeBeforeCreate = issueEmployeeRepository.findAll().collectList().block().size();
        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        IssueEmployee testIssueEmployee = issueEmployeeList.get(issueEmployeeList.size() - 1);
        assertThat(testIssueEmployee.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testIssueEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIssueEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIssueEmployee.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testIssueEmployee.getIssueDepartment()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT);
        assertThat(testIssueEmployee.getDefaultIssueRoleKey()).isEqualTo(DEFAULT_DEFAULT_ISSUE_ROLE_KEY);
        assertThat(testIssueEmployee.getDefaultDisplayedIssueRole()).isEqualTo(DEFAULT_DEFAULT_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueEmployee.getIsAvailable()).isEqualTo(DEFAULT_IS_AVAILABLE);
        assertThat(testIssueEmployee.getInOfficeFrom()).isEqualTo(DEFAULT_IN_OFFICE_FROM);
        assertThat(testIssueEmployee.getOfficeHourFrom()).isEqualTo(DEFAULT_OFFICE_HOUR_FROM);
        assertThat(testIssueEmployee.getOfficeHourTo()).isEqualTo(DEFAULT_OFFICE_HOUR_TO);
        assertThat(testIssueEmployee.getTimezone()).isEqualTo(DEFAULT_TIMEZONE);
        assertThat(testIssueEmployee.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIssueEmployee.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testIssueEmployee.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    void createIssueEmployeeWithExistingId() throws Exception {
        // Create the IssueEmployee with an existing ID
        issueEmployee.setId(1L);
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        int databaseSizeBeforeCreate = issueEmployeeRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setUsername(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setFirstName(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setLastName(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setDisplayName(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIssueDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setIssueDepartment(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDefaultIssueRoleKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setDefaultIssueRoleKey(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDefaultDisplayedIssueRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setDefaultDisplayedIssueRole(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIsAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setIsAvailable(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkInOfficeFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setInOfficeFrom(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkOfficeHourFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setOfficeHourFrom(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkOfficeHourToIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setOfficeHourTo(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTimezoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setTimezone(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setCreated(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueEmployeeRepository.findAll().collectList().block().size();
        // set the field null
        issueEmployee.setModified(null);

        // Create the IssueEmployee, which fails.
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllIssueEmployees() {
        // Initialize the database
        issueEmployeeRepository.save(issueEmployee).block();

        // Get all the issueEmployeeList
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
            .value(hasItem(issueEmployee.getId().intValue()))
            .jsonPath("$.[*].username")
            .value(hasItem(DEFAULT_USERNAME))
            .jsonPath("$.[*].firstName")
            .value(hasItem(DEFAULT_FIRST_NAME))
            .jsonPath("$.[*].lastName")
            .value(hasItem(DEFAULT_LAST_NAME))
            .jsonPath("$.[*].displayName")
            .value(hasItem(DEFAULT_DISPLAY_NAME))
            .jsonPath("$.[*].issueDepartment")
            .value(hasItem(DEFAULT_ISSUE_DEPARTMENT))
            .jsonPath("$.[*].defaultIssueRoleKey")
            .value(hasItem(DEFAULT_DEFAULT_ISSUE_ROLE_KEY))
            .jsonPath("$.[*].defaultDisplayedIssueRole")
            .value(hasItem(DEFAULT_DEFAULT_DISPLAYED_ISSUE_ROLE))
            .jsonPath("$.[*].isAvailable")
            .value(hasItem(DEFAULT_IS_AVAILABLE.booleanValue()))
            .jsonPath("$.[*].inOfficeFrom")
            .value(hasItem(DEFAULT_IN_OFFICE_FROM.toString()))
            .jsonPath("$.[*].officeHourFrom")
            .value(hasItem(DEFAULT_OFFICE_HOUR_FROM.toString()))
            .jsonPath("$.[*].officeHourTo")
            .value(hasItem(DEFAULT_OFFICE_HOUR_TO.toString()))
            .jsonPath("$.[*].timezone")
            .value(hasItem(DEFAULT_TIMEZONE))
            .jsonPath("$.[*].created")
            .value(hasItem(DEFAULT_CREATED.toString()))
            .jsonPath("$.[*].modified")
            .value(hasItem(DEFAULT_MODIFIED.toString()))
            .jsonPath("$.[*].deleted")
            .value(hasItem(DEFAULT_DELETED.toString()));
    }

    @Test
    void getIssueEmployee() {
        // Initialize the database
        issueEmployeeRepository.save(issueEmployee).block();

        // Get the issueEmployee
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, issueEmployee.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(issueEmployee.getId().intValue()))
            .jsonPath("$.username")
            .value(is(DEFAULT_USERNAME))
            .jsonPath("$.firstName")
            .value(is(DEFAULT_FIRST_NAME))
            .jsonPath("$.lastName")
            .value(is(DEFAULT_LAST_NAME))
            .jsonPath("$.displayName")
            .value(is(DEFAULT_DISPLAY_NAME))
            .jsonPath("$.issueDepartment")
            .value(is(DEFAULT_ISSUE_DEPARTMENT))
            .jsonPath("$.defaultIssueRoleKey")
            .value(is(DEFAULT_DEFAULT_ISSUE_ROLE_KEY))
            .jsonPath("$.defaultDisplayedIssueRole")
            .value(is(DEFAULT_DEFAULT_DISPLAYED_ISSUE_ROLE))
            .jsonPath("$.isAvailable")
            .value(is(DEFAULT_IS_AVAILABLE.booleanValue()))
            .jsonPath("$.inOfficeFrom")
            .value(is(DEFAULT_IN_OFFICE_FROM.toString()))
            .jsonPath("$.officeHourFrom")
            .value(is(DEFAULT_OFFICE_HOUR_FROM.toString()))
            .jsonPath("$.officeHourTo")
            .value(is(DEFAULT_OFFICE_HOUR_TO.toString()))
            .jsonPath("$.timezone")
            .value(is(DEFAULT_TIMEZONE))
            .jsonPath("$.created")
            .value(is(DEFAULT_CREATED.toString()))
            .jsonPath("$.modified")
            .value(is(DEFAULT_MODIFIED.toString()))
            .jsonPath("$.deleted")
            .value(is(DEFAULT_DELETED.toString()));
    }

    @Test
    void getNonExistingIssueEmployee() {
        // Get the issueEmployee
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingIssueEmployee() throws Exception {
        // Initialize the database
        issueEmployeeRepository.save(issueEmployee).block();

        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();

        // Update the issueEmployee
        IssueEmployee updatedIssueEmployee = issueEmployeeRepository.findById(issueEmployee.getId()).block();
        updatedIssueEmployee
            .username(UPDATED_USERNAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .issueDepartment(UPDATED_ISSUE_DEPARTMENT)
            .defaultIssueRoleKey(UPDATED_DEFAULT_ISSUE_ROLE_KEY)
            .defaultDisplayedIssueRole(UPDATED_DEFAULT_DISPLAYED_ISSUE_ROLE)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .inOfficeFrom(UPDATED_IN_OFFICE_FROM)
            .officeHourFrom(UPDATED_OFFICE_HOUR_FROM)
            .officeHourTo(UPDATED_OFFICE_HOUR_TO)
            .timezone(UPDATED_TIMEZONE)
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .deleted(UPDATED_DELETED);
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(updatedIssueEmployee);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueEmployeeDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
        IssueEmployee testIssueEmployee = issueEmployeeList.get(issueEmployeeList.size() - 1);
        assertThat(testIssueEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testIssueEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIssueEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIssueEmployee.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testIssueEmployee.getIssueDepartment()).isEqualTo(UPDATED_ISSUE_DEPARTMENT);
        assertThat(testIssueEmployee.getDefaultIssueRoleKey()).isEqualTo(UPDATED_DEFAULT_ISSUE_ROLE_KEY);
        assertThat(testIssueEmployee.getDefaultDisplayedIssueRole()).isEqualTo(UPDATED_DEFAULT_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueEmployee.getIsAvailable()).isEqualTo(UPDATED_IS_AVAILABLE);
        assertThat(testIssueEmployee.getInOfficeFrom()).isEqualTo(UPDATED_IN_OFFICE_FROM);
        assertThat(testIssueEmployee.getOfficeHourFrom()).isEqualTo(UPDATED_OFFICE_HOUR_FROM);
        assertThat(testIssueEmployee.getOfficeHourTo()).isEqualTo(UPDATED_OFFICE_HOUR_TO);
        assertThat(testIssueEmployee.getTimezone()).isEqualTo(UPDATED_TIMEZONE);
        assertThat(testIssueEmployee.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIssueEmployee.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testIssueEmployee.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void putNonExistingIssueEmployee() throws Exception {
        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();
        issueEmployee.setId(count.incrementAndGet());

        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, issueEmployeeDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchIssueEmployee() throws Exception {
        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();
        issueEmployee.setId(count.incrementAndGet());

        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamIssueEmployee() throws Exception {
        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();
        issueEmployee.setId(count.incrementAndGet());

        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateIssueEmployeeWithPatch() throws Exception {
        // Initialize the database
        issueEmployeeRepository.save(issueEmployee).block();

        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();

        // Update the issueEmployee using partial update
        IssueEmployee partialUpdatedIssueEmployee = new IssueEmployee();
        partialUpdatedIssueEmployee.setId(issueEmployee.getId());

        partialUpdatedIssueEmployee
            .firstName(UPDATED_FIRST_NAME)
            .defaultIssueRoleKey(UPDATED_DEFAULT_ISSUE_ROLE_KEY)
            .inOfficeFrom(UPDATED_IN_OFFICE_FROM)
            .officeHourFrom(UPDATED_OFFICE_HOUR_FROM)
            .timezone(UPDATED_TIMEZONE)
            .modified(UPDATED_MODIFIED);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueEmployee.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueEmployee))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
        IssueEmployee testIssueEmployee = issueEmployeeList.get(issueEmployeeList.size() - 1);
        assertThat(testIssueEmployee.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testIssueEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIssueEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIssueEmployee.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testIssueEmployee.getIssueDepartment()).isEqualTo(DEFAULT_ISSUE_DEPARTMENT);
        assertThat(testIssueEmployee.getDefaultIssueRoleKey()).isEqualTo(UPDATED_DEFAULT_ISSUE_ROLE_KEY);
        assertThat(testIssueEmployee.getDefaultDisplayedIssueRole()).isEqualTo(DEFAULT_DEFAULT_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueEmployee.getIsAvailable()).isEqualTo(DEFAULT_IS_AVAILABLE);
        assertThat(testIssueEmployee.getInOfficeFrom()).isEqualTo(UPDATED_IN_OFFICE_FROM);
        assertThat(testIssueEmployee.getOfficeHourFrom()).isEqualTo(UPDATED_OFFICE_HOUR_FROM);
        assertThat(testIssueEmployee.getOfficeHourTo()).isEqualTo(DEFAULT_OFFICE_HOUR_TO);
        assertThat(testIssueEmployee.getTimezone()).isEqualTo(UPDATED_TIMEZONE);
        assertThat(testIssueEmployee.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIssueEmployee.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testIssueEmployee.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    void fullUpdateIssueEmployeeWithPatch() throws Exception {
        // Initialize the database
        issueEmployeeRepository.save(issueEmployee).block();

        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();

        // Update the issueEmployee using partial update
        IssueEmployee partialUpdatedIssueEmployee = new IssueEmployee();
        partialUpdatedIssueEmployee.setId(issueEmployee.getId());

        partialUpdatedIssueEmployee
            .username(UPDATED_USERNAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .issueDepartment(UPDATED_ISSUE_DEPARTMENT)
            .defaultIssueRoleKey(UPDATED_DEFAULT_ISSUE_ROLE_KEY)
            .defaultDisplayedIssueRole(UPDATED_DEFAULT_DISPLAYED_ISSUE_ROLE)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .inOfficeFrom(UPDATED_IN_OFFICE_FROM)
            .officeHourFrom(UPDATED_OFFICE_HOUR_FROM)
            .officeHourTo(UPDATED_OFFICE_HOUR_TO)
            .timezone(UPDATED_TIMEZONE)
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .deleted(UPDATED_DELETED);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedIssueEmployee.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueEmployee))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
        IssueEmployee testIssueEmployee = issueEmployeeList.get(issueEmployeeList.size() - 1);
        assertThat(testIssueEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testIssueEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIssueEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIssueEmployee.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testIssueEmployee.getIssueDepartment()).isEqualTo(UPDATED_ISSUE_DEPARTMENT);
        assertThat(testIssueEmployee.getDefaultIssueRoleKey()).isEqualTo(UPDATED_DEFAULT_ISSUE_ROLE_KEY);
        assertThat(testIssueEmployee.getDefaultDisplayedIssueRole()).isEqualTo(UPDATED_DEFAULT_DISPLAYED_ISSUE_ROLE);
        assertThat(testIssueEmployee.getIsAvailable()).isEqualTo(UPDATED_IS_AVAILABLE);
        assertThat(testIssueEmployee.getInOfficeFrom()).isEqualTo(UPDATED_IN_OFFICE_FROM);
        assertThat(testIssueEmployee.getOfficeHourFrom()).isEqualTo(UPDATED_OFFICE_HOUR_FROM);
        assertThat(testIssueEmployee.getOfficeHourTo()).isEqualTo(UPDATED_OFFICE_HOUR_TO);
        assertThat(testIssueEmployee.getTimezone()).isEqualTo(UPDATED_TIMEZONE);
        assertThat(testIssueEmployee.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIssueEmployee.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testIssueEmployee.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void patchNonExistingIssueEmployee() throws Exception {
        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();
        issueEmployee.setId(count.incrementAndGet());

        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, issueEmployeeDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchIssueEmployee() throws Exception {
        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();
        issueEmployee.setId(count.incrementAndGet());

        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamIssueEmployee() throws Exception {
        int databaseSizeBeforeUpdate = issueEmployeeRepository.findAll().collectList().block().size();
        issueEmployee.setId(count.incrementAndGet());

        // Create the IssueEmployee
        IssueEmployeeDTO issueEmployeeDTO = issueEmployeeMapper.toDto(issueEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(issueEmployeeDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the IssueEmployee in the database
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteIssueEmployee() {
        // Initialize the database
        issueEmployeeRepository.save(issueEmployee).block();

        int databaseSizeBeforeDelete = issueEmployeeRepository.findAll().collectList().block().size();

        // Delete the issueEmployee
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, issueEmployee.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<IssueEmployee> issueEmployeeList = issueEmployeeRepository.findAll().collectList().block();
        assertThat(issueEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
