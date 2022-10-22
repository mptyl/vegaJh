import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeRadioGroupFormService } from './qe-radio-group-form.service';
import { QeRadioGroupService } from '../service/qe-radio-group.service';
import { IQeRadioGroup } from '../qe-radio-group.model';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';

import { QeRadioGroupUpdateComponent } from './qe-radio-group-update.component';

describe('QeRadioGroup Management Update Component', () => {
  let comp: QeRadioGroupUpdateComponent;
  let fixture: ComponentFixture<QeRadioGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeRadioGroupFormService: QeRadioGroupFormService;
  let qeRadioGroupService: QeRadioGroupService;
  let qeQuestionService: QeQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeRadioGroupUpdateComponent],
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
      .overrideTemplate(QeRadioGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeRadioGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeRadioGroupFormService = TestBed.inject(QeRadioGroupFormService);
    qeRadioGroupService = TestBed.inject(QeRadioGroupService);
    qeQuestionService = TestBed.inject(QeQuestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeQuestion query and add missing value', () => {
      const qeRadioGroup: IQeRadioGroup = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 84566 };
      qeRadioGroup.qeQuestion = qeQuestion;

      const qeQuestionCollection: IQeQuestion[] = [{ id: 22454 }];
      jest.spyOn(qeQuestionService, 'query').mockReturnValue(of(new HttpResponse({ body: qeQuestionCollection })));
      const additionalQeQuestions = [qeQuestion];
      const expectedCollection: IQeQuestion[] = [...additionalQeQuestions, ...qeQuestionCollection];
      jest.spyOn(qeQuestionService, 'addQeQuestionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeRadioGroup });
      comp.ngOnInit();

      expect(qeQuestionService.query).toHaveBeenCalled();
      expect(qeQuestionService.addQeQuestionToCollectionIfMissing).toHaveBeenCalledWith(
        qeQuestionCollection,
        ...additionalQeQuestions.map(expect.objectContaining)
      );
      expect(comp.qeQuestionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeRadioGroup: IQeRadioGroup = { id: 456 };
      const qeQuestion: IQeQuestion = { id: 6336 };
      qeRadioGroup.qeQuestion = qeQuestion;

      activatedRoute.data = of({ qeRadioGroup });
      comp.ngOnInit();

      expect(comp.qeQuestionsSharedCollection).toContain(qeQuestion);
      expect(comp.qeRadioGroup).toEqual(qeRadioGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeRadioGroup>>();
      const qeRadioGroup = { id: 123 };
      jest.spyOn(qeRadioGroupFormService, 'getQeRadioGroup').mockReturnValue(qeRadioGroup);
      jest.spyOn(qeRadioGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeRadioGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeRadioGroup }));
      saveSubject.complete();

      // THEN
      expect(qeRadioGroupFormService.getQeRadioGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeRadioGroupService.update).toHaveBeenCalledWith(expect.objectContaining(qeRadioGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeRadioGroup>>();
      const qeRadioGroup = { id: 123 };
      jest.spyOn(qeRadioGroupFormService, 'getQeRadioGroup').mockReturnValue({ id: null });
      jest.spyOn(qeRadioGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeRadioGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeRadioGroup }));
      saveSubject.complete();

      // THEN
      expect(qeRadioGroupFormService.getQeRadioGroup).toHaveBeenCalled();
      expect(qeRadioGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeRadioGroup>>();
      const qeRadioGroup = { id: 123 };
      jest.spyOn(qeRadioGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeRadioGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeRadioGroupService.update).toHaveBeenCalled();
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
