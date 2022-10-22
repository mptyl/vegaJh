import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeReplyFormService } from './qe-reply-form.service';
import { QeReplyService } from '../service/qe-reply.service';
import { IQeReply } from '../qe-reply.model';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';

import { QeReplyUpdateComponent } from './qe-reply-update.component';

describe('QeReply Management Update Component', () => {
  let comp: QeReplyUpdateComponent;
  let fixture: ComponentFixture<QeReplyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeReplyFormService: QeReplyFormService;
  let qeReplyService: QeReplyService;
  let qeQuestionService: QeQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeReplyUpdateComponent],
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
      .overrideTemplate(QeReplyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeReplyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeReplyFormService = TestBed.inject(QeReplyFormService);
    qeReplyService = TestBed.inject(QeReplyService);
    qeQuestionService = TestBed.inject(QeQuestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeQuestion query and add missing value', () => {
      const qeReply: IQeReply = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 55868 };
      qeReply.qeQuestion = qeQuestion;

      const qeQuestionCollection: IQeQuestion[] = [{ id: 10761 }];
      jest.spyOn(qeQuestionService, 'query').mockReturnValue(of(new HttpResponse({ body: qeQuestionCollection })));
      const additionalQeQuestions = [qeQuestion];
      const expectedCollection: IQeQuestion[] = [...additionalQeQuestions, ...qeQuestionCollection];
      jest.spyOn(qeQuestionService, 'addQeQuestionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeReply });
      comp.ngOnInit();

      expect(qeQuestionService.query).toHaveBeenCalled();
      expect(qeQuestionService.addQeQuestionToCollectionIfMissing).toHaveBeenCalledWith(
        qeQuestionCollection,
        ...additionalQeQuestions.map(expect.objectContaining)
      );
      expect(comp.qeQuestionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeReply: IQeReply = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 79085 };
      qeReply.qeQuestion = qeQuestion;

      activatedRoute.data = of({ qeReply });
      comp.ngOnInit();

      expect(comp.qeQuestionsSharedCollection).toContain(qeQuestion);
      expect(comp.qeReply).toEqual(qeReply);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeReply>>();
      const qeReply = { id: 123 };
      jest.spyOn(qeReplyFormService, 'getQeReply').mockReturnValue(qeReply);
      jest.spyOn(qeReplyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeReply });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeReply }));
      saveSubject.complete();

      // THEN
      expect(qeReplyFormService.getQeReply).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeReplyService.update).toHaveBeenCalledWith(expect.objectContaining(qeReply));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeReply>>();
      const qeReply = { id: 123 };
      jest.spyOn(qeReplyFormService, 'getQeReply').mockReturnValue({ id: null });
      jest.spyOn(qeReplyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeReply: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeReply }));
      saveSubject.complete();

      // THEN
      expect(qeReplyFormService.getQeReply).toHaveBeenCalled();
      expect(qeReplyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeReply>>();
      const qeReply = { id: 123 };
      jest.spyOn(qeReplyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeReply });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeReplyService.update).toHaveBeenCalled();
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
