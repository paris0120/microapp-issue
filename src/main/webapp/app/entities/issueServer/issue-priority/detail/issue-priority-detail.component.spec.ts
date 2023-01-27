import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssuePriorityDetailComponent } from './issue-priority-detail.component';

describe('IssuePriority Management Detail Component', () => {
  let comp: IssuePriorityDetailComponent;
  let fixture: ComponentFixture<IssuePriorityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssuePriorityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issuePriority: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssuePriorityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssuePriorityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issuePriority on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issuePriority).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
