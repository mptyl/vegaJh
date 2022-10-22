import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeJumpExpressionFormService } from './qe-jump-expression-form.service';
import { QeJumpExpressionService } from '../service/qe-jump-expression.service';
import { IQeJumpExpression } from '../qe-jump-expression.model';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';

import { QeJumpExpressionUpdateComponent } from './qe-jump-expression-update.component';

describe('QeJumpExpression Management Update Component', () => {
  let comp: QeJumpExpressionUpdateComponent;
  let fixture: ComponentFixture<QeJumpExpressionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeJumpExpressionFormService: QeJumpExpressionFormService;
  let qeJumpExpressionService: QeJumpExpressionService;
  let qeQuestionService: QeQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeJumpExpressionUpdateComponent],
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
      .overrideTemplate(QeJumpExpressionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeJumpExpressionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeJumpExpressionFormService = TestBed.inject(QeJumpExpressionFormService);
    qeJumpExpressionService = TestBed.inject(QeJumpExpressionService);
    qeQuestionService = TestBed.inject(QeQuestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeQuestion query and add missing value', () => {
      const qeJumpExpression: IQeJumpExpression = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 97253 };
      qeJumpExpression.qeQuestion = qeQuestion;

      const qeQuestionCollection: IQeQuestion[] = [{ id: 94627 }];
      jest.spyOn(qeQuestionService, 'query').mockReturnValue(of(new HttpResponse({ body: qeQuestionCollection })));
      const additionalQeQuestions = [qeQuestion];
      const expectedCollection: IQeQuestion[] = [...additionalQeQuestions, ...qeQuestionCollection];
      jest.spyOn(qeQuestionService, 'addQeQuestionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeJumpExpression });
      comp.ngOnInit();

      expect(qeQuestionService.query).toHaveBeenCalled();
      expect(qeQuestionService.addQeQuestionToCollectionIfMissing).toHaveBeenCalledWith(
        qeQuestionCollection,
        ...additionalQeQuestions.map(expect.objectContaining)
      );
      expect(comp.qeQuestionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeJumpExpression: IQeJumpExpression = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 22027 };
      qeJumpExpression.qeQuestion = qeQuestion;

      activatedRoute.data = of({ qeJumpExpression });
      comp.ngOnInit();

      expect(comp.qeQuestionsSharedCollection).toContain(qeQuestion);
      expect(comp.qeJumpExpression).toEqual(qeJumpExpression);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeJumpExpression>>();
      const qeJumpExpression = { id: 123 };
      jest.spyOn(qeJumpExpressionFormService, 'getQeJumpExpression').mockReturnValue(qeJumpExpression);
      jest.spyOn(qeJumpExpressionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeJumpExpression });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeJumpExpression }));
      saveSubject.complete();

      // THEN
      expect(qeJumpExpressionFormService.getQeJumpExpression).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeJumpExpressionService.update).toHaveBeenCalledWith(expect.objectContaining(qeJumpExpression));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeJumpExpression>>();
      const qeJumpExpression = { id: 123 };
      jest.spyOn(qeJumpExpressionFormService, 'getQeJumpExpression').mockReturnValue({ id: null });
      jest.spyOn(qeJumpExpressionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeJumpExpression: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeJumpExpression }));
      saveSubject.complete();

      // THEN
      expect(qeJumpExpressionFormService.getQeJumpExpression).toHaveBeenCalled();
      expect(qeJumpExpressionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeJumpExpression>>();
      const qeJumpExpression = { id: 123 };
      jest.spyOn(qeJumpExpressionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeJumpExpression });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeJumpExpressionService.update).toHaveBeenCalled();
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
