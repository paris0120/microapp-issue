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

describe('IssueType e2e test', () => {
  const issueTypePageUrl = '/issueserver/issue-type';
  const issueTypePageUrlPattern = new RegExp('/issueserver/issue-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issueTypeSample = { issueTypeKey: 'ADP turquoise', issueType: 'Wooden' };

  let issueType;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issue-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issue-types').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issue-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issueType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issue-types/${issueType.id}`,
      }).then(() => {
        issueType = undefined;
      });
    }
  });

  it('IssueTypes menu should load IssueTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IssueType').should('exist');
    cy.url().should('match', issueTypePageUrlPattern);
  });

  describe('IssueType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issueTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IssueType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue-type/new$'));
        cy.getEntityCreateUpdateHeading('IssueType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issue-types',
          body: issueTypeSample,
        }).then(({ body }) => {
          issueType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issue-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [issueType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issueTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IssueType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issueType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueTypePageUrlPattern);
      });

      it('edit button click should load edit IssueType page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueTypePageUrlPattern);
      });

      it('edit button click should load edit IssueType page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueType');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueTypePageUrlPattern);
      });

      it('last delete button click should delete instance of IssueType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issueType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueTypePageUrlPattern);

        issueType = undefined;
      });
    });
  });

  describe('new IssueType page', () => {
    beforeEach(() => {
      cy.visit(`${issueTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IssueType');
    });

    it('should create an instance of IssueType', () => {
      cy.get(`[data-cy="issueTypeKey"]`).type('Internal Realigned').should('have.value', 'Internal Realigned');

      cy.get(`[data-cy="issueType"]`).type('Home wireless Operations').should('have.value', 'Home wireless Operations');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issueType = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issueTypePageUrlPattern);
    });
  });
});
