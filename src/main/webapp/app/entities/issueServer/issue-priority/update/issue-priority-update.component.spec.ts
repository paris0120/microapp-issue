import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssuePriorityFormService } from './issue-priority-form.service';
import { IssuePriorityService } from '../service/issue-priority.service';
import { IIssuePriority } from '../issue-priority.model';

import { IssuePriorityUpdateComponent } from './issue-priority-update.component';

describe('IssuePriority Management Update Component', () => {
  let comp: IssuePriorityUpdateComponent;
  let fixture: ComponentFixture<IssuePriorityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issuePriorityFormService: IssuePriorityFormService;
  let issuePriorityService: IssuePriorityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssuePriorityUpdateComponent],
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
      .overrideTemplate(IssuePriorityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssuePriorityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issuePriorityFormService = TestBed.inject(IssuePriorityFormService);
    issuePriorityService = TestBed.inject(IssuePriorityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issuePriority: IIssuePriority = { id: 456 };

      activatedRoute.data = of({ issuePriority });
      comp.ngOnInit();

      expect(comp.issuePriority).toEqual(issuePriority);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssuePriority>>();
      const issuePriority = { id: 123 };
      jest.spyOn(issuePriorityFormService, 'getIssuePriority').mockReturnValue(issuePriority);
      jest.spyOn(issuePriorityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issuePriority });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issuePriority }));
      saveSubject.complete();

      // THEN
      expect(issuePriorityFormService.getIssuePriority).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issuePriorityService.update).toHaveBeenCalledWith(expect.objectContaining(issuePriority));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssuePriority>>();
      const issuePriority = { id: 123 };
      jest.spyOn(issuePriorityFormService, 'getIssuePriority').mockReturnValue({ id: null });
      jest.spyOn(issuePriorityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issuePriority: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issuePriority }));
      saveSubject.complete();

      // THEN
      expect(issuePriorityFormService.getIssuePriority).toHaveBeenCalled();
      expect(issuePriorityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssuePriority>>();
      const issuePriority = { id: 123 };
      jest.spyOn(issuePriorityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issuePriority });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issuePriorityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
