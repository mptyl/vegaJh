import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeQuestionFormService } from './qe-question-form.service';
import { QeQuestionService } from '../service/qe-question.service';
import { IQeQuestion } from '../qe-question.model';
import { IQeGroup } from 'app/entities/qe-group/qe-group.model';
import { QeGroupService } from 'app/entities/qe-group/service/qe-group.service';

import { QeQuestionUpdateComponent } from './qe-question-update.component';

describe('QeQuestion Management Update Component', () => {
  let comp: QeQuestionUpdateComponent;
  let fixture: ComponentFixture<QeQuestionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeQuestionFormService: QeQuestionFormService;
  let qeQuestionService: QeQuestionService;
  let qeGroupService: QeGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeQuestionUpdateComponent],
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
      .overrideTemplate(QeQuestionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeQuestionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeQuestionFormService = TestBed.inject(QeQuestionFormService);
    qeQuestionService = TestBed.inject(QeQuestionService);
    qeGroupService = TestBed.inject(QeGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeGroup query and add missing value', () => {
      const qeQuestion: IQeQuestion = { id: 456 };
      const qeGroup: IQeGroup = { id: 26411 };
      qeQuestion.qeGroup = qeGroup;

      const qeGroupCollection: IQeGroup[] = [{ id: 99495 }];
      jest.spyOn(qeGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: qeGroupCollection })));
      const additionalQeGroups = [qeGroup];
      const expectedCollection: IQeGroup[] = [...additionalQeGroups, ...qeGroupCollection];
      jest.spyOn(qeGroupService, 'addQeGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeQuestion });
      comp.ngOnInit();

      expect(qeGroupService.query).toHaveBeenCalled();
      expect(qeGroupService.addQeGroupToCollectionIfMissing).toHaveBeenCalledWith(
        qeGroupCollection,
        ...additionalQeGroups.map(expect.objectContaining)
      );
      expect(comp.qeGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeQuestion: IQeQuestion = { id: 456 };
      const qeGroup: IQeGroup = { id: 12743 };
      qeQuestion.qeGroup = qeGroup;

      activatedRoute.data = of({ qeQuestion });
      comp.ngOnInit();

      expect(comp.qeGroupsSharedCollection).toContain(qeGroup);
      expect(comp.qeQuestion).toEqual(qeQuestion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeQuestion>>();
      const qeQuestion = { id: 123 };
      jest.spyOn(qeQuestionFormService, 'getQeQuestion').mockReturnValue(qeQuestion);
      jest.spyOn(qeQuestionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeQuestion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeQuestion }));
      saveSubject.complete();

      // THEN
      expect(qeQuestionFormService.getQeQuestion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeQuestionService.update).toHaveBeenCalledWith(expect.objectContaining(qeQuestion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeQuestion>>();
      const qeQuestion = { id: 123 };
      jest.spyOn(qeQuestionFormService, 'getQeQuestion').mockReturnValue({ id: null });
      jest.spyOn(qeQuestionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeQuestion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeQuestion }));
      saveSubject.complete();

      // THEN
      expect(qeQuestionFormService.getQeQuestion).toHaveBeenCalled();
      expect(qeQuestionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeQuestion>>();
      const qeQuestion = { id: 123 };
      jest.spyOn(qeQuestionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeQuestion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeQuestionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareQeGroup', () => {
      it('Should forward to qeGroupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(qeGroupService, 'compareQeGroup');
        comp.compareQeGroup(entity, entity2);
        expect(qeGroupService.compareQeGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
