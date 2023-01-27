import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssueRoleDetailComponent } from './issue-role-detail.component';

describe('IssueRole Management Detail Component', () => {
  let comp: IssueRoleDetailComponent;
  let fixture: ComponentFixture<IssueRoleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssueRoleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issueRole: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssueRoleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssueRoleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issueRole on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issueRole).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
