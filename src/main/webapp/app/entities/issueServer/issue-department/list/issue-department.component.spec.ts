import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IssueDepartmentService } from '../service/issue-department.service';

import { IssueDepartmentComponent } from './issue-department.component';

describe('IssueDepartment Management Component', () => {
  let comp: IssueDepartmentComponent;
  let fixture: ComponentFixture<IssueDepartmentComponent>;
  let service: IssueDepartmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'issueserver/issue-department', component: IssueDepartmentComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [IssueDepartmentComponent],
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
      .overrideTemplate(IssueDepartmentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueDepartmentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IssueDepartmentService);

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
    expect(comp.issueDepartments?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to issueDepartmentService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getIssueDepartmentIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getIssueDepartmentIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
