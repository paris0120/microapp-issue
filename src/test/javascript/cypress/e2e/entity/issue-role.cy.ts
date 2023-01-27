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

describe('IssueRole e2e test', () => {
  const issueRolePageUrl = '/issueserver/issue-role';
  const issueRolePageUrlPattern = new RegExp('/issueserver/issue-role(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issueRoleSample = { issueRoleKey: 'withdrawal', issueRole: 'compress' };

  let issueRole;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issue-roles+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issue-roles').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issue-roles/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issueRole) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issue-roles/${issueRole.id}`,
      }).then(() => {
        issueRole = undefined;
      });
    }
  });

  it('IssueRoles menu should load IssueRoles page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue-role');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IssueRole').should('exist');
    cy.url().should('match', issueRolePageUrlPattern);
  });

  describe('IssueRole page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issueRolePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IssueRole page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue-role/new$'));
        cy.getEntityCreateUpdateHeading('IssueRole');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueRolePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issue-roles',
          body: issueRoleSample,
        }).then(({ body }) => {
          issueRole = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issue-roles+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [issueRole],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issueRolePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IssueRole page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issueRole');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueRolePageUrlPattern);
      });

      it('edit button click should load edit IssueRole page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueRole');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueRolePageUrlPattern);
      });

      it('edit button click should load edit IssueRole page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueRole');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueRolePageUrlPattern);
      });

      it('last delete button click should delete instance of IssueRole', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issueRole').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueRolePageUrlPattern);

        issueRole = undefined;
      });
    });
  });

  describe('new IssueRole page', () => {
    beforeEach(() => {
      cy.visit(`${issueRolePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IssueRole');
    });

    it('should create an instance of IssueRole', () => {
      cy.get(`[data-cy="issueRoleKey"]`).type('Reunion Small').should('have.value', 'Reunion Small');

      cy.get(`[data-cy="issueRole"]`).type('Manager Virgin').should('have.value', 'Manager Virgin');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issueRole = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issueRolePageUrlPattern);
    });
  });
});
