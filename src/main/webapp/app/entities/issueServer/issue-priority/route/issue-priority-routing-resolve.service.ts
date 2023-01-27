import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssuePriority } from '../issue-priority.model';
import { IssuePriorityService } from '../service/issue-priority.service';

@Injectable({ providedIn: 'root' })
export class IssuePriorityRoutingResolveService implements Resolve<IIssuePriority | null> {
  constructor(protected service: IssuePriorityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssuePriority | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issuePriority: HttpResponse<IIssuePriority>) => {
          if (issuePriority.body) {
            return of(issuePriority.body);
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
