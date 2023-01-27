import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssueRole, NewIssueRole } from '../issue-role.model';

export type PartialUpdateIssueRole = Partial<IIssueRole> & Pick<IIssueRole, 'id'>;

export type EntityResponseType = HttpResponse<IIssueRole>;
export type EntityArrayResponseType = HttpResponse<IIssueRole[]>;

@Injectable({ providedIn: 'root' })
export class IssueRoleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issue-roles', 'issueserver');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issueRole: NewIssueRole): Observable<EntityResponseType> {
    return this.http.post<IIssueRole>(this.resourceUrl, issueRole, { observe: 'response' });
  }

  update(issueRole: IIssueRole): Observable<EntityResponseType> {
    return this.http.put<IIssueRole>(`${this.resourceUrl}/${this.getIssueRoleIdentifier(issueRole)}`, issueRole, { observe: 'response' });
  }

  partialUpdate(issueRole: PartialUpdateIssueRole): Observable<EntityResponseType> {
    return this.http.patch<IIssueRole>(`${this.resourceUrl}/${this.getIssueRoleIdentifier(issueRole)}`, issueRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssueRoleIdentifier(issueRole: Pick<IIssueRole, 'id'>): number {
    return issueRole.id;
  }

  compareIssueRole(o1: Pick<IIssueRole, 'id'> | null, o2: Pick<IIssueRole, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssueRoleIdentifier(o1) === this.getIssueRoleIdentifier(o2) : o1 === o2;
  }

  addIssueRoleToCollectionIfMissing<Type extends Pick<IIssueRole, 'id'>>(
    issueRoleCollection: Type[],
    ...issueRolesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issueRoles: Type[] = issueRolesToCheck.filter(isPresent);
    if (issueRoles.length > 0) {
      const issueRoleCollectionIdentifiers = issueRoleCollection.map(issueRoleItem => this.getIssueRoleIdentifier(issueRoleItem)!);
      const issueRolesToAdd = issueRoles.filter(issueRoleItem => {
        const issueRoleIdentifier = this.getIssueRoleIdentifier(issueRoleItem);
        if (issueRoleCollectionIdentifiers.includes(issueRoleIdentifier)) {
          return false;
        }
        issueRoleCollectionIdentifiers.push(issueRoleIdentifier);
        return true;
      });
      return [...issueRolesToAdd, ...issueRoleCollection];
    }
    return issueRoleCollection;
  }
}
