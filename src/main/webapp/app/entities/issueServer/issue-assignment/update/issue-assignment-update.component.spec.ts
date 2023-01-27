import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssueAssignmentFormService } from './issue-assignment-form.service';
import { IssueAssignmentService } from '../service/issue-assignment.service';
import { IIssueAssignment } from '../issue-assignment.model';

import { IssueAssignmentUpdateComponent } from './issue-assignment-update.component';

describe('IssueAssignment Management Update Component', () => {
  let comp: IssueAssignmentUpdateComponent;
  let fixture: ComponentFixture<IssueAssignmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issueAssignmentFormService: IssueAssignmentFormService;
  let issueAssignmentService: IssueAssignmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssueAssignmentUpdateComponent],
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
      .overrideTemplate(IssueAssignmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueAssignmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issueAssignmentFormService = TestBed.inject(IssueAssignmentFormService);
    issueAssignmentService = TestBed.inject(IssueAssignmentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issueAssignment: IIssueAssignment = { id: 456 };

      activatedRoute.data = of({ issueAssignment });
      comp.ngOnInit();

      expect(comp.issueAssignment).toEqual(issueAssignment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueAssignment>>();
      const issueAssignment = { id: 123 };
      jest.spyOn(issueAssignmentFormService, 'getIssueAssignment').mockReturnValue(issueAssignment);
      jest.spyOn(issueAssignmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueAssignment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueAssignment }));
      saveSubject.complete();

      // THEN
      expect(issueAssignmentFormService.getIssueAssignment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issueAssignmentService.update).toHaveBeenCalledWith(expect.objectContaining(issueAssignment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueAssignment>>();
      const issueAssignment = { id: 123 };
      jest.spyOn(issueAssignmentFormService, 'getIssueAssignment').mockReturnValue({ id: null });
      jest.spyOn(issueAssignmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueAssignment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueAssignment }));
      saveSubject.complete();

      // THEN
      expect(issueAssignmentFormService.getIssueAssignment).toHaveBeenCalled();
      expect(issueAssignmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueAssignment>>();
      const issueAssignment = { id: 123 };
      jest.spyOn(issueAssignmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueAssignment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issueAssignmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
