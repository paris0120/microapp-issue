import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssueDepartmentFormService } from './issue-department-form.service';
import { IssueDepartmentService } from '../service/issue-department.service';
import { IIssueDepartment } from '../issue-department.model';

import { IssueDepartmentUpdateComponent } from './issue-department-update.component';

describe('IssueDepartment Management Update Component', () => {
  let comp: IssueDepartmentUpdateComponent;
  let fixture: ComponentFixture<IssueDepartmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issueDepartmentFormService: IssueDepartmentFormService;
  let issueDepartmentService: IssueDepartmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssueDepartmentUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(IssueDepartmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueDepartmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issueDepartmentFormService = TestBed.inject(IssueDepartmentFormService);
    issueDepartmentService = TestBed.inject(IssueDepartmentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issueDepartment: IIssueDepartment = { id: 456 };

      activatedRoute.data = of({ issueDepartment });
      comp.ngOnInit();

      expect(comp.issueDepartment).toEqual(issueDepartment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueDepartment>>();
      const issueDepartment = { id: 123 };
      jest.spyOn(issueDepartmentFormService, 'getIssueDepartment').mockReturnValue(issueDepartment);
      jest.spyOn(issueDepartmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueDepartment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueDepartment }));
      saveSubject.complete();

      // THEN
      expect(issueDepartmentFormService.getIssueDepartment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issueDepartmentService.update).toHaveBeenCalledWith(expect.objectContaining(issueDepartment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueDepartment>>();
      const issueDepartment = { id: 123 };
      jest.spyOn(issueDepartmentFormService, 'getIssueDepartment').mockReturnValue({ id: null });
      jest.spyOn(issueDepartmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueDepartment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueDepartment }));
      saveSubject.complete();

      // THEN
      expect(issueDepartmentFormService.getIssueDepartment).toHaveBeenCalled();
      expect(issueDepartmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueDepartment>>();
      const issueDepartment = { id: 123 };
      jest.spyOn(issueDepartmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueDepartment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issueDepartmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
