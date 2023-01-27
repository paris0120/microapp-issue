import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssueEmployee, NewIssueEmployee } from '../issue-employee.model';

export type PartialUpdateIssueEmployee = Partial<IIssueEmployee> & Pick<IIssueEmployee, 'id'>;

type RestOf<T extends IIssueEmployee | NewIssueEmployee> = Omit<
  T,
  'inOfficeFrom' | 'officeHourFrom' | 'officeHourTo' | 'created' | 'modified' | 'deleted'
> & {
  inOfficeFrom?: string | null;
  officeHourFrom?: string | null;
  officeHourTo?: string | null;
  created?: string | null;
  modified?: string | null;
  deleted?: string | null;
};

export type RestIssueEmployee = RestOf<IIssueEmployee>;

export type NewRestIssueEmployee = RestOf<NewIssueEmployee>;

export type PartialUpdateRestIssueEmployee = RestOf<PartialUpdateIssueEmployee>;

export type EntityResponseType = HttpResponse<IIssueEmployee>;
export type EntityArrayResponseType = HttpResponse<IIssueEmployee[]>;

@Injectable({ providedIn: 'root' })
export class IssueEmployeeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issue-employees', 'issueserver');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issueEmployee: NewIssueEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issueEmployee);
    return this.http
      .post<RestIssueEmployee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(issueEmployee: IIssueEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issueEmployee);
    return this.http
      .put<RestIssueEmployee>(`${this.resourceUrl}/${this.getIssueEmployeeIdentifier(issueEmployee)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(issueEmployee: PartialUpdateIssueEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issueEmployee);
    return this.http
      .patch<RestIssueEmployee>(`${this.resourceUrl}/${this.getIssueEmployeeIdentifier(issueEmployee)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestIssueEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestIssueEmployee[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssueEmployeeIdentifier(issueEmployee: Pick<IIssueEmployee, 'id'>): number {
    return issueEmployee.id;
  }

  compareIssueEmployee(o1: Pick<IIssueEmployee, 'id'> | null, o2: Pick<IIssueEmployee, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssueEmployeeIdentifier(o1) === this.getIssueEmployeeIdentifier(o2) : o1 === o2;
  }

  addIssueEmployeeToCollectionIfMissing<Type extends Pick<IIssueEmployee, 'id'>>(
    issueEmployeeCollection: Type[],
    ...issueEmployeesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issueEmployees: Type[] = issueEmployeesToCheck.filter(isPresent);
    if (issueEmployees.length > 0) {
      const issueEmployeeCollectionIdentifiers = issueEmployeeCollection.map(
        issueEmployeeItem => this.getIssueEmployeeIdentifier(issueEmployeeItem)!
      );
      const issueEmployeesToAdd = issueEmployees.filter(issueEmployeeItem => {
        const issueEmployeeIdentifier = this.getIssueEmployeeIdentifier(issueEmployeeItem);
        if (issueEmployeeCollectionIdentifiers.includes(issueEmployeeIdentifier)) {
          return false;
        }
        issueEmployeeCollectionIdentifiers.push(issueEmployeeIdentifier);
        return true;
      });
      return [...issueEmployeesToAdd, ...issueEmployeeCollection];
    }
    return issueEmployeeCollection;
  }

  protected convertDateFromClient<T extends IIssueEmployee | NewIssueEmployee | PartialUpdateIssueEmployee>(issueEmployee: T): RestOf<T> {
    return {
      ...issueEmployee,
      inOfficeFrom: issueEmployee.inOfficeFrom?.toJSON() ?? null,
      officeHourFrom: issueEmployee.officeHourFrom?.toJSON() ?? null,
      officeHourTo: issueEmployee.officeHourTo?.toJSON() ?? null,
      created: issueEmployee.created?.toJSON() ?? null,
      modified: issueEmployee.modified?.toJSON() ?? null,
      deleted: issueEmployee.deleted?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restIssueEmployee: RestIssueEmployee): IIssueEmployee {
    return {
      ...restIssueEmployee,
      inOfficeFrom: restIssueEmployee.inOfficeFrom ? dayjs(restIssueEmployee.inOfficeFrom) : undefined,
      officeHourFrom: restIssueEmployee.officeHourFrom ? dayjs(restIssueEmployee.officeHourFrom) : undefined,
      officeHourTo: restIssueEmployee.officeHourTo ? dayjs(restIssueEmployee.officeHourTo) : undefined,
      created: restIssueEmployee.created ? dayjs(restIssueEmployee.created) : undefined,
      modified: restIssueEmployee.modified ? dayjs(restIssueEmployee.modified) : undefined,
      deleted: restIssueEmployee.deleted ? dayjs(restIssueEmployee.deleted) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestIssueEmployee>): HttpResponse<IIssueEmployee> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestIssueEmployee[]>): HttpResponse<IIssueEmployee[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
