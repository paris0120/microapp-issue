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

describe('Issue e2e test', () => {
  const issuePageUrl = '/issueserver/issue';
  const issuePageUrlPattern = new RegExp('/issueserver/issue(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issueSample = {
    displayedUsername: 'virtual',
    issueTitle: 'Botswana Sleek',
    issueContent: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    issueType: 'technologies',
    issueWorkflowStatus: 'Granite Kwacha',
    issueWorkflowStatusKey: 'calculating',
    issuePriorityLevel: 71071,
    created: '2023-01-26T16:36:36.382Z',
    modified: '2023-01-27T03:10:13.548Z',
  };

  let issue;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issues+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issues').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issues/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issue) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issues/${issue.id}`,
      }).then(() => {
        issue = undefined;
      });
    }
  });

  it('Issues menu should load Issues page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Issue').should('exist');
    cy.url().should('match', issuePageUrlPattern);
  });

  describe('Issue page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issuePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Issue page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue/new$'));
        cy.getEntityCreateUpdateHeading('Issue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issues',
          body: issueSample,
        }).then(({ body }) => {
          issue = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issues+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/issueserver/api/issues?page=0&size=20>; rel="last",<http://localhost/services/issueserver/api/issues?page=0&size=20>; rel="first"',
              },
              body: [issue],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issuePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Issue page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issue');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePageUrlPattern);
      });

      it('edit button click should load edit Issue page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Issue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePageUrlPattern);
      });

      it('edit button click should load edit Issue page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Issue');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePageUrlPattern);
      });

      it('last delete button click should delete instance of Issue', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issue').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePageUrlPattern);

        issue = undefined;
      });
    });
  });

  describe('new Issue page', () => {
    beforeEach(() => {
      cy.visit(`${issuePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Issue');
    });

    it('should create an instance of Issue', () => {
      cy.get(`[data-cy="username"]`).type('attitude').should('have.value', 'attitude');

      cy.get(`[data-cy="firstName"]`).type('Valentine').should('have.value', 'Valentine');

      cy.get(`[data-cy="lastName"]`).type('Bogan').should('have.value', 'Bogan');

      cy.get(`[data-cy="displayedUsername"]`).type('hierarchy Loti pixel').should('have.value', 'hierarchy Loti pixel');

      cy.get(`[data-cy="issueTitle"]`).type('Exclusive synergies program').should('have.value', 'Exclusive synergies program');

      cy.get(`[data-cy="issueContent"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="issueType"]`).type('Games').should('have.value', 'Games');

      cy.get(`[data-cy="issueWorkflowStatus"]`).type('back-end bandwidth Architect').should('have.value', 'back-end bandwidth Architect');

      cy.get(`[data-cy="issueWorkflowStatusKey"]`).type('multi-byte vortals Chips').should('have.value', 'multi-byte vortals Chips');

      cy.get(`[data-cy="issuePriorityLevel"]`).type('5712').should('have.value', '5712');

      cy.get(`[data-cy="issueUuid"]`)
        .type('84da1d73-f5fa-45d1-8ddc-9a24319994d3')
        .invoke('val')
        .should('match', new RegExp('84da1d73-f5fa-45d1-8ddc-9a24319994d3'));

      cy.get(`[data-cy="created"]`).type('2023-01-26T15:26').blur().should('have.value', '2023-01-26T15:26');

      cy.get(`[data-cy="modified"]`).type('2023-01-26T23:45').blur().should('have.value', '2023-01-26T23:45');

      cy.get(`[data-cy="deleted"]`).type('2023-01-26T11:53').blur().should('have.value', '2023-01-26T11:53');

      cy.get(`[data-cy="closed"]`).type('2023-01-26T08:35').blur().should('have.value', '2023-01-26T08:35');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issue = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issuePageUrlPattern);
    });
  });
});
