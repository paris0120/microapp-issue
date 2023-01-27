import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssueRoleFormService } from './issue-role-form.service';
import { IssueRoleService } from '../service/issue-role.service';
import { IIssueRole } from '../issue-role.model';

import { IssueRoleUpdateComponent } from './issue-role-update.component';

describe('IssueRole Management Update Component', () => {
  let comp: IssueRoleUpdateComponent;
  let fixture: ComponentFixture<IssueRoleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issueRoleFormService: IssueRoleFormService;
  let issueRoleService: IssueRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssueRoleUpdateComponent],
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
      .overrideTemplate(IssueRoleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssueRoleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issueRoleFormService = TestBed.inject(IssueRoleFormService);
    issueRoleService = TestBed.inject(IssueRoleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issueRole: IIssueRole = { id: 456 };

      activatedRoute.data = of({ issueRole });
      comp.ngOnInit();

      expect(comp.issueRole).toEqual(issueRole);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueRole>>();
      const issueRole = { id: 123 };
      jest.spyOn(issueRoleFormService, 'getIssueRole').mockReturnValue(issueRole);
      jest.spyOn(issueRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueRole }));
      saveSubject.complete();

      // THEN
      expect(issueRoleFormService.getIssueRole).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issueRoleService.update).toHaveBeenCalledWith(expect.objectContaining(issueRole));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueRole>>();
      const issueRole = { id: 123 };
      jest.spyOn(issueRoleFormService, 'getIssueRole').mockReturnValue({ id: null });
      jest.spyOn(issueRoleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueRole: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issueRole }));
      saveSubject.complete();

      // THEN
      expect(issueRoleFormService.getIssueRole).toHaveBeenCalled();
      expect(issueRoleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssueRole>>();
      const issueRole = { id: 123 };
      jest.spyOn(issueRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issueRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issueRoleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
