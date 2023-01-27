import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssueType } from '../issue-type.model';
import { IssueTypeService } from '../service/issue-type.service';

@Injectable({ providedIn: 'root' })
export class IssueTypeRoutingResolveService implements Resolve<IIssueType | null> {
  constructor(protected service: IssueTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issueType: HttpResponse<IIssueType>) => {
          if (issueType.body) {
            return of(issueType.body);
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
