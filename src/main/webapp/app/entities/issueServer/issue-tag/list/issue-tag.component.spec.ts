import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IssueTagService } from '../service/issue-tag.service';

import { IssueTagComponent } from './issue-tag.component';

describe('IssueTag Management Component', () => {
  let comp: IssueTagComponent;
  let fixture: ComponentFixture<IssueTagComponent>;
  let service: IssueTagService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'issueserver/issue-tag', component: IssueTagComponent }]), HttpClientTestingModule],
      declarations: [IssueTagComponent],
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
      .overrideTemplate(IssueTagComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueTagComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IssueTagService);

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
    expect(comp.issueTags?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to issueTagService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getIssueTagIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getIssueTagIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
