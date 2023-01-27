import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('IssueEmployee e2e test', () => {
  const issueEmployeePageUrl = '/issueserver/issue-employee';
  const issueEmployeePageUrlPattern = new RegExp('/issueserver/issue-employee(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issueEmployeeSample = {
    username: 'Music payment Pennsylvania',
    firstName: 'Stewart',
    lastName: 'Ankunding',
    displayName: 'Buckinghamshire turquoise',
    issueDepartment: 'Awesome Awesome',
    defaultIssueRoleKey: 'Incredible',
    defaultDisplayedIssueRole: 'Unbranded',
    isAvailable: true,
    inOfficeFrom: '2023-01-26T13:14:40.117Z',
    officeHourFrom: '2023-01-26T13:40:27.695Z',
    officeHourTo: '2023-01-26T19:51:09.159Z',
    timezone: 'Cotton',
    created: '2023-01-27T04:31:54.358Z',
    modified: '2023-01-27T00:37:13.137Z',
  };

  let issueEmployee;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issue-employees+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issue-employees').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issue-employees/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issueEmployee) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issue-employees/${issueEmployee.id}`,
      }).then(() => {
        issueEmployee = undefined;
      });
    }
  });

  it('IssueEmployees menu should load IssueEmployees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue-employee');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IssueEmployee').should('exist');
    cy.url().should('match', issueEmployeePageUrlPattern);
  });

  describe('IssueEmployee page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issueEmployeePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IssueEmployee page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue-employee/new$'));
        cy.getEntityCreateUpdateHeading('IssueEmployee');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueEmployeePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issue-employees',
          body: issueEmployeeSample,
        }).then(({ body }) => {
          issueEmployee = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issue-employees+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/issueserver/api/issue-employees?page=0&size=20>; rel="last",<http://localhost/services/issueserver/api/issue-employees?page=0&size=20>; rel="first"',
              },
              body: [issueEmployee],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issueEmployeePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IssueEmployee page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issueEmployee');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueEmployeePageUrlPattern);
      });

      it('edit button click should load edit IssueEmployee page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueEmployee');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueEmployeePageUrlPattern);
      });

      it('edit button click should load edit IssueEmployee page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueEmployee');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueEmployeePageUrlPattern);
      });

      it('last delete button click should delete instance of IssueEmployee', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issueEmployee').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueEmployeePageUrlPattern);

        issueEmployee = undefined;
      });
    });
  });

  describe('new IssueEmployee page', () => {
    beforeEach(() => {
      cy.visit(`${issueEmployeePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IssueEmployee');
    });

    it('should create an instance of IssueEmployee', () => {
      cy.get(`[data-cy="username"]`).type('Account real-time').should('have.value', 'Account real-time');

      cy.get(`[data-cy="firstName"]`).type('Jettie').should('have.value', 'Jettie');

      cy.get(`[data-cy="lastName"]`).type('Hessel').should('have.value', 'Hessel');

      cy.get(`[data-cy="displayName"]`).type('bleeding-edge').should('have.value', 'bleeding-edge');

      cy.get(`[data-cy="issueDepartment"]`).type('Market').should('have.value', 'Market');

      cy.get(`[data-cy="defaultIssueRoleKey"]`).type('Granite').should('have.value', 'Granite');

      cy.get(`[data-cy="defaultDisplayedIssueRole"]`).type('User-friendly Investment').should('have.value', 'User-friendly Investment');

      cy.get(`[data-cy="isAvailable"]`).should('not.be.checked');
      cy.get(`[data-cy="isAvailable"]`).click().should('be.checked');

      cy.get(`[data-cy="inOfficeFrom"]`).type('2023-01-27T02:40').blur().should('have.value', '2023-01-27T02:40');

      cy.get(`[data-cy="officeHourFrom"]`).type('2023-01-26T22:39').blur().should('have.value', '2023-01-26T22:39');

      cy.get(`[data-cy="officeHourTo"]`).type('2023-01-27T03:00').blur().should('have.value', '2023-01-27T03:00');

      cy.get(`[data-cy="timezone"]`).type('mobile').should('have.value', 'mobile');

      cy.get(`[data-cy="created"]`).type('2023-01-26T05:20').blur().should('have.value', '2023-01-26T05:20');

      cy.get(`[data-cy="modified"]`).type('2023-01-27T02:01').blur().should('have.value', '2023-01-27T02:01');

      cy.get(`[data-cy="deleted"]`).type('2023-01-26T13:36').blur().should('have.value', '2023-01-26T13:36');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issueEmployee = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issueEmployeePageUrlPattern);
    });
  });
});
