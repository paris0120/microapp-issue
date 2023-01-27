import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssueRole } from '../issue-role.model';
import { IssueRoleService } from '../service/issue-role.service';

@Injectable({ providedIn: 'root' })
export class IssueRoleRoutingResolveService implements Resolve<IIssueRole | null> {
  constructor(protected service: IssueRoleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueRole | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issueRole: HttpResponse<IIssueRole>) => {
          if (issueRole.body) {
            return of(issueRole.body);
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
