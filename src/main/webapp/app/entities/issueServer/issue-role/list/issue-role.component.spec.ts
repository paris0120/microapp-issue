import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IssueRoleService } from '../service/issue-role.service';

import { IssueRoleComponent } from './issue-role.component';

describe('IssueRole Management Component', () => {
  let comp: IssueRoleComponent;
  let fixture: ComponentFixture<IssueRoleComponent>;
  let service: IssueRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'issueserver/issue-role', component: IssueRoleComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [IssueRoleComponent],
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
      .overrideTemplate(IssueRoleComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueRoleComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IssueRoleService);

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
    expect(comp.issueRoles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to issueRoleService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getIssueRoleIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getIssueRoleIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
