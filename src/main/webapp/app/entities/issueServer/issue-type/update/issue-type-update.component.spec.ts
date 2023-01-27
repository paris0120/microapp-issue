import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssueTypeFormService } from './issue-type-form.service';
import { IssueTypeService } from '../service/issue-type.service';
import { IIssueType } from '../issue-type.model';

import { IssueTypeUpdateComponent } from './issue-type-update.component';

describe('IssueType Management Update Component', () => {
  let comp: IssueTypeUpdateComponent;
  let fixture: ComponentFixture<IssueTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issueTypeFormService: IssueTypeFormService;
  let issueTypeService: IssueTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssueTypeUpdateComponent],
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
      .overrideTemplate(IssueTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issueTypeFormService = TestBed.inject(IssueTypeFormService);
    issueTypeService = TestBed.inject(IssueTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issueType: IIssueType = { id: 456 };

      activatedRoute.data = of({ issueType });
      comp.ngOnInit();

      expect(comp.issueType).toEqual(issueType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueType>>();
      const issueType = { id: 123 };
      jest.spyOn(issueTypeFormService, 'getIssueType').mockReturnValue(issueType);
      jest.spyOn(issueTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueType }));
      saveSubject.complete();

      // THEN
      expect(issueTypeFormService.getIssueType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issueTypeService.update).toHaveBeenCalledWith(expect.objectContaining(issueType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueType>>();
      const issueType = { id: 123 };
      jest.spyOn(issueTypeFormService, 'getIssueType').mockReturnValue({ id: null });
      jest.spyOn(issueTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueType }));
      saveSubject.complete();

      // THEN
      expect(issueTypeFormService.getIssueType).toHaveBeenCalled();
      expect(issueTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueType>>();
      const issueType = { id: 123 };
      jest.spyOn(issueTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issueTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
