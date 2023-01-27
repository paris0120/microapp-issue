import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssuePriority, NewIssuePriority } from '../issue-priority.model';

export type PartialUpdateIssuePriority = Partial<IIssuePriority> & Pick<IIssuePriority, 'id'>;

export type EntityResponseType = HttpResponse<IIssuePriority>;
export type EntityArrayResponseType = HttpResponse<IIssuePriority[]>;

@Injectable({ providedIn: 'root' })
export class IssuePriorityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issue-priorities', 'issueserver');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issuePriority: NewIssuePriority): Observable<EntityResponseType> {
    return this.http.post<IIssuePriority>(this.resourceUrl, issuePriority, { observe: 'response' });
  }

  update(issuePriority: IIssuePriority): Observable<EntityResponseType> {
    return this.http.put<IIssuePriority>(`${this.resourceUrl}/${this.getIssuePriorityIdentifier(issuePriority)}`, issuePriority, {
      observe: 'response',
    });
  }

  partialUpdate(issuePriority: PartialUpdateIssuePriority): Observable<EntityResponseType> {
    return this.http.patch<IIssuePriority>(`${this.resourceUrl}/${this.getIssuePriorityIdentifier(issuePriority)}`, issuePriority, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssuePriority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssuePriority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssuePriorityIdentifier(issuePriority: Pick<IIssuePriority, 'id'>): number {
    return issuePriority.id;
  }

  compareIssuePriority(o1: Pick<IIssuePriority, 'id'> | null, o2: Pick<IIssuePriority, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssuePriorityIdentifier(o1) === this.getIssuePriorityIdentifier(o2) : o1 === o2;
  }

  addIssuePriorityToCollectionIfMissing<Type extends Pick<IIssuePriority, 'id'>>(
    issuePriorityCollection: Type[],
    ...issuePrioritiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issuePriorities: Type[] = issuePrioritiesToCheck.filter(isPresent);
    if (issuePriorities.length > 0) {
      const issuePriorityCollectionIdentifiers = issuePriorityCollection.map(
        issuePriorityItem => this.getIssuePriorityIdentifier(issuePriorityItem)!
      );
      const issuePrioritiesToAdd = issuePriorities.filter(issuePriorityItem => {
        const issuePriorityIdentifier = this.getIssuePriorityIdentifier(issuePriorityItem);
        if (issuePriorityCollectionIdentifiers.includes(issuePriorityIdentifier)) {
          return false;
        }
        issuePriorityCollectionIdentifiers.push(issuePriorityIdentifier);
        return true;
      });
      return [...issuePrioritiesToAdd, ...issuePriorityCollection];
    }
    return issuePriorityCollection;
  }
}
