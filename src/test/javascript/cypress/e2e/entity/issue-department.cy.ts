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

describe('IssueDepartment e2e test', () => {
  const issueDepartmentPageUrl = '/issueserver/issue-department';
  const issueDepartmentPageUrlPattern = new RegExp('/issueserver/issue-department(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issueDepartmentSample = { issueDepartmentKey: 'Arkansas revolutionize', issueDepartment: 'calculating' };

  let issueDepartment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/issueserver/api/issue-departments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/issueserver/api/issue-departments').as('postEntityRequest');
    cy.intercept('DELETE', '/services/issueserver/api/issue-departments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issueDepartment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/issueserver/api/issue-departments/${issueDepartment.id}`,
      }).then(() => {
        issueDepartment = undefined;
      });
    }
  });

  it('IssueDepartments menu should load IssueDepartments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issueserver/issue-department');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IssueDepartment').should('exist');
    cy.url().should('match', issueDepartmentPageUrlPattern);
  });

  describe('IssueDepartment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issueDepartmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IssueDepartment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issueserver/issue-department/new$'));
        cy.getEntityCreateUpdateHeading('IssueDepartment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueDepartmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/issueserver/api/issue-departments',
          body: issueDepartmentSample,
        }).then(({ body }) => {
          issueDepartment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/issueserver/api/issue-departments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [issueDepartment],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issueDepartmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IssueDepartment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issueDepartment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueDepartmentPageUrlPattern);
      });

      it('edit button click should load edit IssueDepartment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueDepartment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueDepartmentPageUrlPattern);
      });

      it('edit button click should load edit IssueDepartment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IssueDepartment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueDepartmentPageUrlPattern);
      });

      it('last delete button click should delete instance of IssueDepartment', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issueDepartment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', issueDepartmentPageUrlPattern);

        issueDepartment = undefined;
      });
    });
  });

  describe('new IssueDepartment page', () => {
    beforeEach(() => {
      cy.visit(`${issueDepartmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IssueDepartment');
    });

    it('should create an instance of IssueDepartment', () => {
      cy.get(`[data-cy="issueDepartmentKey"]`).type('Small Intelligent Computers').should('have.value', 'Small Intelligent Computers');

      cy.get(`[data-cy="issueDepartment"]`).type('value-added Personal').should('have.value', 'value-added Personal');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        issueDepartment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', issueDepartmentPageUrlPattern);
    });
  });
});
