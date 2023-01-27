import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssueType, NewIssueType } from '../issue-type.model';

export type PartialUpdateIssueType = Partial<IIssueType> & Pick<IIssueType, 'id'>;

export type EntityResponseType = HttpResponse<IIssueType>;
export type EntityArrayResponseType = HttpResponse<IIssueType[]>;

@Injectable({ providedIn: 'root' })
export class IssueTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issue-types', 'issueserver');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issueType: NewIssueType): Observable<EntityResponseType> {
    return this.http.post<IIssueType>(this.resourceUrl, issueType, { observe: 'response' });
  }

  update(issueType: IIssueType): Observable<EntityResponseType> {
    return this.http.put<IIssueType>(`${this.resourceUrl}/${this.getIssueTypeIdentifier(issueType)}`, issueType, { observe: 'response' });
  }

  partialUpdate(issueType: PartialUpdateIssueType): Observable<EntityResponseType> {
    return this.http.patch<IIssueType>(`${this.resourceUrl}/${this.getIssueTypeIdentifier(issueType)}`, issueType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssueTypeIdentifier(issueType: Pick<IIssueType, 'id'>): number {
    return issueType.id;
  }

  compareIssueType(o1: Pick<IIssueType, 'id'> | null, o2: Pick<IIssueType, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssueTypeIdentifier(o1) === this.getIssueTypeIdentifier(o2) : o1 === o2;
  }

  addIssueTypeToCollectionIfMissing<Type extends Pick<IIssueType, 'id'>>(
    issueTypeCollection: Type[],
    ...issueTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issueTypes: Type[] = issueTypesToCheck.filter(isPresent);
    if (issueTypes.length > 0) {
      const issueTypeCollectionIdentifiers = issueTypeCollection.map(issueTypeItem => this.getIssueTypeIdentifier(issueTypeItem)!);
      const issueTypesToAdd = issueTypes.filter(issueTypeItem => {
        const issueTypeIdentifier = this.getIssueTypeIdentifier(issueTypeItem);
        if (issueTypeCollectionIdentifiers.includes(issueTypeIdentifier)) {
          return false;
        }
        issueTypeCollectionIdentifiers.push(issueTypeIdentifier);
        return true;
      });
      return [...issueTypesToAdd, ...issueTypeCollection];
    }
    return issueTypeCollection;
  }
}
