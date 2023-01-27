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

describe('IssueAssignment e2e test', () => {
  const issueAssignmentPageUrl = '/issueserver/issue-assignment';
  const issueAssignmentPageUrlPattern = new RegExp('/issueserver/issue-assignment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issueAssignmentSample = {
    issueId: 98519,
    issueUuid: '53656cb8-59ef-4602-8725-ecb5a7678b21',
    username: 'tertiary',
    issueAssignmentDisplayedUsername: 'Implementation Customer',
    issueRole: 'intranet Account',
    displayedIssueRole: 'Lempira Cotton Sleek',
    created: '2023-01-26T09:36:48.246Z',
    modified: '2023-01-26T07:59:38.976Z',
  };

  let issueAssignment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issue-assignments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issue-assignments').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issue-assignments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issueAssignment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issue-assignments/${issueAssignment.id}`,
      }).then(() => {
        issueAssignment = undefined;
      });
    }
  });

  it('IssueAssignments menu should load IssueAssignments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue-assignment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IssueAssignment').should('exist');
    cy.url().should('match', issueAssignmentPageUrlPattern);
  });

  describe('IssueAssignment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issueAssignmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IssueAssignment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue-assignment/new$'));
        cy.getEntityCreateUpdateHeading('IssueAssignment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueAssignmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issue-assignments',
          body: issueAssignmentSample,
        }).then(({ body }) => {
          issueAssignment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issue-assignments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/issueserver/api/issue-assignments?page=0&size=20>; rel="last",<http://localhost/services/issueserver/api/issue-assignments?page=0&size=20>; rel="first"',
              },
              body: [issueAssignment],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issueAssignmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IssueAssignment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issueAssignment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueAssignmentPageUrlPattern);
      });

      it('edit button click should load edit IssueAssignment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueAssignment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueAssignmentPageUrlPattern);
      });

      it('edit button click should load edit IssueAssignment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueAssignment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueAssignmentPageUrlPattern);
      });

      it('last delete button click should delete instance of IssueAssignment', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issueAssignment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueAssignmentPageUrlPattern);

        issueAssignment = undefined;
      });
    });
  });

  describe('new IssueAssignment page', () => {
    beforeEach(() => {
      cy.visit(`${issueAssignmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IssueAssignment');
    });

    it('should create an instance of IssueAssignment', () => {
      cy.get(`[data-cy="issueId"]`).type('55309').should('have.value', '55309');

      cy.get(`[data-cy="issueUuid"]`)
        .type('80652534-37b4-4dd5-b05c-c91c0d36f486')
        .invoke('val')
        .should('match', new RegExp('80652534-37b4-4dd5-b05c-c91c0d36f486'));

      cy.get(`[data-cy="username"]`).type('Chair').should('have.value', 'Chair');

      cy.get(`[data-cy="issueAssignmentDisplayedUsername"]`).type('Enhanced Bacon olive').should('have.value', 'Enhanced Bacon olive');

      cy.get(`[data-cy="issueRole"]`).type('Licensed').should('have.value', 'Licensed');

      cy.get(`[data-cy="displayedIssueRole"]`).type('next').should('have.value', 'next');

      cy.get(`[data-cy="created"]`).type('2023-01-26T10:58').blur().should('have.value', '2023-01-26T10:58');

      cy.get(`[data-cy="modified"]`).type('2023-01-26T06:56').blur().should('have.value', '2023-01-26T06:56');

      cy.get(`[data-cy="accepted"]`).type('2023-01-26T21:33').blur().should('have.value', '2023-01-26T21:33');

      cy.get(`[data-cy="deleted"]`).type('2023-01-26T20:43').blur().should('have.value', '2023-01-26T20:43');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issueAssignment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issueAssignmentPageUrlPattern);
    });
  });
});
