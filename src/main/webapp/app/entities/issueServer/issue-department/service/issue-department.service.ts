import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssueDepartment, NewIssueDepartment } from '../issue-department.model';

export type PartialUpdateIssueDepartment = Partial<IIssueDepartment> & Pick<IIssueDepartment, 'id'>;

export type EntityResponseType = HttpResponse<IIssueDepartment>;
export type EntityArrayResponseType = HttpResponse<IIssueDepartment[]>;

@Injectable({ providedIn: 'root' })
export class IssueDepartmentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issue-departments', 'issueserver');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issueDepartment: NewIssueDepartment): Observable<EntityResponseType> {
    return this.http.post<IIssueDepartment>(this.resourceUrl, issueDepartment, { observe: 'response' });
  }

  update(issueDepartment: IIssueDepartment): Observable<EntityResponseType> {
    return this.http.put<IIssueDepartment>(`${this.resourceUrl}/${this.getIssueDepartmentIdentifier(issueDepartment)}`, issueDepartment, {
      observe: 'response',
    });
  }

  partialUpdate(issueDepartment: PartialUpdateIssueDepartment): Observable<EntityResponseType> {
    return this.http.patch<IIssueDepartment>(`${this.resourceUrl}/${this.getIssueDepartmentIdentifier(issueDepartment)}`, issueDepartment, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueDepartment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueDepartment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssueDepartmentIdentifier(issueDepartment: Pick<IIssueDepartment, 'id'>): number {
    return issueDepartment.id;
  }

  compareIssueDepartment(o1: Pick<IIssueDepartment, 'id'> | null, o2: Pick<IIssueDepartment, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssueDepartmentIdentifier(o1) === this.getIssueDepartmentIdentifier(o2) : o1 === o2;
  }

  addIssueDepartmentToCollectionIfMissing<Type extends Pick<IIssueDepartment, 'id'>>(
    issueDepartmentCollection: Type[],
    ...issueDepartmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issueDepartments: Type[] = issueDepartmentsToCheck.filter(isPresent);
    if (issueDepartments.length > 0) {
      const issueDepartmentCollectionIdentifiers = issueDepartmentCollection.map(
        issueDepartmentItem => this.getIssueDepartmentIdentifier(issueDepartmentItem)!
      );
      const issueDepartmentsToAdd = issueDepartments.filter(issueDepartmentItem => {
        const issueDepartmentIdentifier = this.getIssueDepartmentIdentifier(issueDepartmentItem);
        if (issueDepartmentCollectionIdentifiers.includes(issueDepartmentIdentifier)) {
          return false;
        }
        issueDepartmentCollectionIdentifiers.push(issueDepartmentIdentifier);
        return true;
      });
      return [...issueDepartmentsToAdd, ...issueDepartmentCollection];
    }
    return issueDepartmentCollection;
  }
}
