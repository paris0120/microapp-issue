import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IssuePriorityService } from '../service/issue-priority.service';

import { IssuePriorityComponent } from './issue-priority.component';

describe('IssuePriority Management Component', () => {
  let comp: IssuePriorityComponent;
  let fixture: ComponentFixture<IssuePriorityComponent>;
  let service: IssuePriorityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'issueserver/issue-priority', component: IssuePriorityComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [IssuePriorityComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(IssuePriorityComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssuePriorityComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IssuePriorityService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.issuePriorities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to issuePriorityService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getIssuePriorityIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getIssuePriorityIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
