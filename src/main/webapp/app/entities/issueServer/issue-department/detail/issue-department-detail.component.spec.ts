import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssueDepartmentDetailComponent } from './issue-department-detail.component';

describe('IssueDepartment Management Detail Component', () => {
  let comp: IssueDepartmentDetailComponent;
  let fixture: ComponentFixture<IssueDepartmentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssueDepartmentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issueDepartment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssueDepartmentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssueDepartmentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issueDepartment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issueDepartment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
