import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeCheckGroupFormService } from './qe-check-group-form.service';
import { QeCheckGroupService } from '../service/qe-check-group.service';
import { IQeCheckGroup } from '../qe-check-group.model';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';

import { QeCheckGroupUpdateComponent } from './qe-check-group-update.component';

describe('QeCheckGroup Management Update Component', () => {
  let comp: QeCheckGroupUpdateComponent;
  let fixture: ComponentFixture<QeCheckGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeCheckGroupFormService: QeCheckGroupFormService;
  let qeCheckGroupService: QeCheckGroupService;
  let qeQuestionService: QeQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeCheckGroupUpdateComponent],
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
      .overrideTemplate(QeCheckGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeCheckGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeCheckGroupFormService = TestBed.inject(QeCheckGroupFormService);
    qeCheckGroupService = TestBed.inject(QeCheckGroupService);
    qeQuestionService = TestBed.inject(QeQuestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeQuestion query and add missing value', () => {
      const qeCheckGroup: IQeCheckGroup = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 92976 };
      qeCheckGroup.qeQuestion = qeQuestion;

      const qeQuestionCollection: IQeQuestion[] = [{ id: 98015 }];
      jest.spyOn(qeQuestionService, 'query').mockReturnValue(of(new HttpResponse({ body: qeQuestionCollection })));
      const additionalQeQuestions = [qeQuestion];
      const expectedCollection: IQeQuestion[] = [...additionalQeQuestions, ...qeQuestionCollection];
      jest.spyOn(qeQuestionService, 'addQeQuestionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeCheckGroup });
      comp.ngOnInit();

      expect(qeQuestionService.query).toHaveBeenCalled();
      expect(qeQuestionService.addQeQuestionToCollectionIfMissing).toHaveBeenCalledWith(
        qeQuestionCollection,
        ...additionalQeQuestions.map(expect.objectContaining)
      );
      expect(comp.qeQuestionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeCheckGroup: IQeCheckGroup = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 45250 };
      qeCheckGroup.qeQuestion = qeQuestion;

      activatedRoute.data = of({ qeCheckGroup });
      comp.ngOnInit();

      expect(comp.qeQuestionsSharedCollection).toContain(qeQuestion);
      expect(comp.qeCheckGroup).toEqual(qeCheckGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeCheckGroup>>();
      const qeCheckGroup = { id: 123 };
      jest.spyOn(qeCheckGroupFormService, 'getQeCheckGroup').mockReturnValue(qeCheckGroup);
      jest.spyOn(qeCheckGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeCheckGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeCheckGroup }));
      saveSubject.complete();

      // THEN
      expect(qeCheckGroupFormService.getQeCheckGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeCheckGroupService.update).toHaveBeenCalledWith(expect.objectContaining(qeCheckGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeCheckGroup>>();
      const qeCheckGroup = { id: 123 };
      jest.spyOn(qeCheckGroupFormService, 'getQeCheckGroup').mockReturnValue({ id: null });
      jest.spyOn(qeCheckGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeCheckGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeCheckGroup }));
      saveSubject.complete();

      // THEN
      expect(qeCheckGroupFormService.getQeCheckGroup).toHaveBeenCalled();
      expect(qeCheckGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeCheckGroup>>();
      const qeCheckGroup = { id: 123 };
      jest.spyOn(qeCheckGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeCheckGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeCheckGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareQeQuestion', () => {
      it('Should forward to qeQuestionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(qeQuestionService, 'compareQeQuestion');
        comp.compareQeQuestion(entity, entity2);
        expect(qeQuestionService.compareQeQuestion).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
