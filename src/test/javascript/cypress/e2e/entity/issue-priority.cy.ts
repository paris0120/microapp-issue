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

describe('IssuePriority e2e test', () => {
  const issuePriorityPageUrl = '/issueserver/issue-priority';
  const issuePriorityPageUrlPattern = new RegExp('/issueserver/issue-priority(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issuePrioritySample = { issuePriority: 'static', issuePriorityLevel: 77643 };

  let issuePriority;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issue-priorities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issue-priorities').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issue-priorities/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issuePriority) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issue-priorities/${issuePriority.id}`,
      }).then(() => {
        issuePriority = undefined;
      });
    }
  });

  it('IssuePriorities menu should load IssuePriorities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue-priority');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IssuePriority').should('exist');
    cy.url().should('match', issuePriorityPageUrlPattern);
  });

  describe('IssuePriority page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issuePriorityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IssuePriority page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue-priority/new$'));
        cy.getEntityCreateUpdateHeading('IssuePriority');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePriorityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issue-priorities',
          body: issuePrioritySample,
        }).then(({ body }) => {
          issuePriority = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issue-priorities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [issuePriority],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issuePriorityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IssuePriority page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issuePriority');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePriorityPageUrlPattern);
      });

      it('edit button click should load edit IssuePriority page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssuePriority');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePriorityPageUrlPattern);
      });

      it('edit button click should load edit IssuePriority page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssuePriority');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePriorityPageUrlPattern);
      });

      it('last delete button click should delete instance of IssuePriority', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issuePriority').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issuePriorityPageUrlPattern);

        issuePriority = undefined;
      });
    });
  });

  describe('new IssuePriority page', () => {
    beforeEach(() => {
      cy.visit(`${issuePriorityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IssuePriority');
    });

    it('should create an instance of IssuePriority', () => {
      cy.get(`[data-cy="issuePriority"]`).type('Table Cheese').should('have.value', 'Table Cheese');

      cy.get(`[data-cy="issuePriorityLevel"]`).type('27783').should('have.value', '27783');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issuePriority = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issuePriorityPageUrlPattern);
    });
  });
});
