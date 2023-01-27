import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssueAssignment } from '../issue-assignment.model';
import { IssueAssignmentService } from '../service/issue-assignment.service';

@Injectable({ providedIn: 'root' })
export class IssueAssignmentRoutingResolveService implements Resolve<IIssueAssignment | null> {
  constructor(protected service: IssueAssignmentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueAssignment | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issueAssignment: HttpResponse<IIssueAssignment>) => {
          if (issueAssignment.body) {
            return of(issueAssignment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
