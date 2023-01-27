import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssueTypeDetailComponent } from './issue-type-detail.component';

describe('IssueType Management Detail Component', () => {
  let comp: IssueTypeDetailComponent;
  let fixture: ComponentFixture<IssueTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssueTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issueType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssueTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssueTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issueType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issueType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
