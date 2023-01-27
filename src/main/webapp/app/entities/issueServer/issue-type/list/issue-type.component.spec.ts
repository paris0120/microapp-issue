import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IssueTypeService } from '../service/issue-type.service';

import { IssueTypeComponent } from './issue-type.component';

describe('IssueType Management Component', () => {
  let comp: IssueTypeComponent;
  let fixture: ComponentFixture<IssueTypeComponent>;
  let service: IssueTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'issueserver/issue-type', component: IssueTypeComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [IssueTypeComponent],
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
      .overrideTemplate(IssueTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IssueTypeService);

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
    expect(comp.issueTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to issueTypeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getIssueTypeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getIssueTypeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
