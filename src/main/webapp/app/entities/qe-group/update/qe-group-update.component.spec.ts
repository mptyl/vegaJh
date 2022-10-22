import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeGroupFormService } from './qe-group-form.service';
import { QeGroupService } from '../service/qe-group.service';
import { IQeGroup } from '../qe-group.model';
import { IQuestionnaire } from 'app/entities/questionnaire/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire/service/questionnaire.service';

import { QeGroupUpdateComponent } from './qe-group-update.component';

describe('QeGroup Management Update Component', () => {
  let comp: QeGroupUpdateComponent;
  let fixture: ComponentFixture<QeGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeGroupFormService: QeGroupFormService;
  let qeGroupService: QeGroupService;
  let questionnaireService: QuestionnaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeGroupUpdateComponent],
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
      .overrideTemplate(QeGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeGroupFormService = TestBed.inject(QeGroupFormService);
    qeGroupService = TestBed.inject(QeGroupService);
    questionnaireService = TestBed.inject(QuestionnaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Questionnaire query and add missing value', () => {
      const qeGroup: IQeGroup = { id: 456 };
      const questionnaire: IQuestionnaire = { id: 89476 };
      qeGroup.questionnaire = questionnaire;

      const questionnaireCollection: IQuestionnaire[] = [{ id: 21842 }];
      jest.spyOn(questionnaireService, 'query').mockReturnValue(of(new HttpResponse({ body: questionnaireCollection })));
      const additionalQuestionnaires = [questionnaire];
      const expectedCollection: IQuestionnaire[] = [...additionalQuestionnaires, ...questionnaireCollection];
      jest.spyOn(questionnaireService, 'addQuestionnaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeGroup });
      comp.ngOnInit();

      expect(questionnaireService.query).toHaveBeenCalled();
      expect(questionnaireService.addQuestionnaireToCollectionIfMissing).toHaveBeenCalledWith(
        questionnaireCollection,
        ...additionalQuestionnaires.map(expect.objectContaining)
      );
      expect(comp.questionnairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeGroup: IQeGroup = { id: 456 };
      const questionnaire: IQuestionnaire = { id: 19816 };
      qeGroup.questionnaire = questionnaire;

      activatedRoute.data = of({ qeGroup });
      comp.ngOnInit();

      expect(comp.questionnairesSharedCollection).toContain(questionnaire);
      expect(comp.qeGroup).toEqual(qeGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeGroup>>();
      const qeGroup = { id: 123 };
      jest.spyOn(qeGroupFormService, 'getQeGroup').mockReturnValue(qeGroup);
      jest.spyOn(qeGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeGroup }));
      saveSubject.complete();

      // THEN
      expect(qeGroupFormService.getQeGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeGroupService.update).toHaveBeenCalledWith(expect.objectContaining(qeGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeGroup>>();
      const qeGroup = { id: 123 };
      jest.spyOn(qeGroupFormService, 'getQeGroup').mockReturnValue({ id: null });
      jest.spyOn(qeGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeGroup }));
      saveSubject.complete();

      // THEN
      expect(qeGroupFormService.getQeGroup).toHaveBeenCalled();
      expect(qeGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeGroup>>();
      const qeGroup = { id: 123 };
      jest.spyOn(qeGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareQuestionnaire', () => {
      it('Should forward to questionnaireService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(questionnaireService, 'compareQuestionnaire');
        comp.compareQuestionnaire(entity, entity2);
        expect(questionnaireService.compareQuestionnaire).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
