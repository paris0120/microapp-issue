import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssueAssignmentDetailComponent } from './issue-assignment-detail.component';

describe('IssueAssignment Management Detail Component', () => {
  let comp: IssueAssignmentDetailComponent;
  let fixture: ComponentFixture<IssueAssignmentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssueAssignmentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issueAssignment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssueAssignmentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssueAssignmentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issueAssignment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issueAssignment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
