import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssueAssignment, NewIssueAssignment } from '../issue-assignment.model';

export type PartialUpdateIssueAssignment = Partial<IIssueAssignment> & Pick<IIssueAssignment, 'id'>;

type RestOf<T extends IIssueAssignment | NewIssueAssignment> = Omit<T, 'created' | 'modified' | 'accepted' | 'deleted'> & {
  created?: string | null;
  modified?: string | null;
  accepted?: string | null;
  deleted?: string | null;
};

export type RestIssueAssignment = RestOf<IIssueAssignment>;

export type NewRestIssueAssignment = RestOf<NewIssueAssignment>;

export type PartialUpdateRestIssueAssignment = RestOf<PartialUpdateIssueAssignment>;

export type EntityResponseType = HttpResponse<IIssueAssignment>;
export type EntityArrayResponseType = HttpResponse<IIssueAssignment[]>;

@Injectable({ providedIn: 'root' })
export class IssueAssignmentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/issue-assignments', 'issueserver');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issueAssignment: NewIssueAssignment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issueAssignment);
    return this.http
      .post<RestIssueAssignment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(issueAssignment: IIssueAssignment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issueAssignment);
    return this.http
      .put<RestIssueAssignment>(`${this.resourceUrl}/${this.getIssueAssignmentIdentifier(issueAssignment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(issueAssignment: PartialUpdateIssueAssignment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(issueAssignment);
    return this.http
      .patch<RestIssueAssignment>(`${this.resourceUrl}/${this.getIssueAssignmentIdentifier(issueAssignment)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestIssueAssignment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestIssueAssignment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssueAssignmentIdentifier(issueAssignment: Pick<IIssueAssignment, 'id'>): number {
    return issueAssignment.id;
  }

  compareIssueAssignment(o1: Pick<IIssueAssignment, 'id'> | null, o2: Pick<IIssueAssignment, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssueAssignmentIdentifier(o1) === this.getIssueAssignmentIdentifier(o2) : o1 === o2;
  }

  addIssueAssignmentToCollectionIfMissing<Type extends Pick<IIssueAssignment, 'id'>>(
    issueAssignmentCollection: Type[],
    ...issueAssignmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issueAssignments: Type[] = issueAssignmentsToCheck.filter(isPresent);
    if (issueAssignments.length > 0) {
      const issueAssignmentCollectionIdentifiers = issueAssignmentCollection.map(
        issueAssignmentItem => this.getIssueAssignmentIdentifier(issueAssignmentItem)!
      );
      const issueAssignmentsToAdd = issueAssignments.filter(issueAssignmentItem => {
        const issueAssignmentIdentifier = this.getIssueAssignmentIdentifier(issueAssignmentItem);
        if (issueAssignmentCollectionIdentifiers.includes(issueAssignmentIdentifier)) {
          return false;
        }
        issueAssignmentCollectionIdentifiers.push(issueAssignmentIdentifier);
        return true;
      });
      return [...issueAssignmentsToAdd, ...issueAssignmentCollection];
    }
    return issueAssignmentCollection;
  }

  protected convertDateFromClient<T extends IIssueAssignment | NewIssueAssignment | PartialUpdateIssueAssignment>(
    issueAssignment: T
  ): RestOf<T> {
    return {
      ...issueAssignment,
      created: issueAssignment.created?.toJSON() ?? null,
      modified: issueAssignment.modified?.toJSON() ?? null,
      accepted: issueAssignment.accepted?.toJSON() ?? null,
      deleted: issueAssignment.deleted?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restIssueAssignment: RestIssueAssignment): IIssueAssignment {
    return {
      ...restIssueAssignment,
      created: restIssueAssignment.created ? dayjs(restIssueAssignment.created) : undefined,
      modified: restIssueAssignment.modified ? dayjs(restIssueAssignment.modified) : undefined,
      accepted: restIssueAssignment.accepted ? dayjs(restIssueAssignment.accepted) : undefined,
      deleted: restIssueAssignment.deleted ? dayjs(restIssueAssignment.deleted) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestIssueAssignment>): HttpResponse<IIssueAssignment> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestIssueAssignment[]>): HttpResponse<IIssueAssignment[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
