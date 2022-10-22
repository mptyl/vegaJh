import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeRadioBoxFormService } from './qe-radio-box-form.service';
import { QeRadioBoxService } from '../service/qe-radio-box.service';
import { IQeRadioBox } from '../qe-radio-box.model';
import { IQeRadioGroup } from 'app/entities/qe-radio-group/qe-radio-group.model';
import { QeRadioGroupService } from 'app/entities/qe-radio-group/service/qe-radio-group.service';

import { QeRadioBoxUpdateComponent } from './qe-radio-box-update.component';

describe('QeRadioBox Management Update Component', () => {
  let comp: QeRadioBoxUpdateComponent;
  let fixture: ComponentFixture<QeRadioBoxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeRadioBoxFormService: QeRadioBoxFormService;
  let qeRadioBoxService: QeRadioBoxService;
  let qeRadioGroupService: QeRadioGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeRadioBoxUpdateComponent],
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
      .overrideTemplate(QeRadioBoxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeRadioBoxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeRadioBoxFormService = TestBed.inject(QeRadioBoxFormService);
    qeRadioBoxService = TestBed.inject(QeRadioBoxService);
    qeRadioGroupService = TestBed.inject(QeRadioGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeRadioGroup query and add missing value', () => {
      const qeRadioBox: IQeRadioBox = { id: 456 };
      const qeRadioGroup: IQeRadioGroup = { id: 13 };
      qeRadioBox.qeRadioGroup = qeRadioGroup;

      const qeRadioGroupCollection: IQeRadioGroup[] = [{ id: 42668 }];
      jest.spyOn(qeRadioGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: qeRadioGroupCollection })));
      const additionalQeRadioGroups = [qeRadioGroup];
      const expectedCollection: IQeRadioGroup[] = [...additionalQeRadioGroups, ...qeRadioGroupCollection];
      jest.spyOn(qeRadioGroupService, 'addQeRadioGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeRadioBox });
      comp.ngOnInit();

      expect(qeRadioGroupService.query).toHaveBeenCalled();
      expect(qeRadioGroupService.addQeRadioGroupToCollectionIfMissing).toHaveBeenCalledWith(
        qeRadioGroupCollection,
        ...additionalQeRadioGroups.map(expect.objectContaining)
      );
      expect(comp.qeRadioGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeRadioBox: IQeRadioBox = { id: 456 };
      const qeRadioGroup: IQeRadioGroup = { id: 95258 };
      qeRadioBox.qeRadioGroup = qeRadioGroup;

      activatedRoute.data = of({ qeRadioBox });
      comp.ngOnInit();

      expect(comp.qeRadioGroupsSharedCollection).toContain(qeRadioGroup);
      expect(comp.qeRadioBox).toEqual(qeRadioBox);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeRadioBox>>();
      const qeRadioBox = { id: 123 };
      jest.spyOn(qeRadioBoxFormService, 'getQeRadioBox').mockReturnValue(qeRadioBox);
      jest.spyOn(qeRadioBoxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeRadioBox });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeRadioBox }));
      saveSubject.complete();

      // THEN
      expect(qeRadioBoxFormService.getQeRadioBox).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeRadioBoxService.update).toHaveBeenCalledWith(expect.objectContaining(qeRadioBox));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeRadioBox>>();
      const qeRadioBox = { id: 123 };
      jest.spyOn(qeRadioBoxFormService, 'getQeRadioBox').mockReturnValue({ id: null });
      jest.spyOn(qeRadioBoxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeRadioBox: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeRadioBox }));
      saveSubject.complete();

      // THEN
      expect(qeRadioBoxFormService.getQeRadioBox).toHaveBeenCalled();
      expect(qeRadioBoxService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeRadioBox>>();
      const qeRadioBox = { id: 123 };
      jest.spyOn(qeRadioBoxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeRadioBox });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeRadioBoxService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareQeRadioGroup', () => {
      it('Should forward to qeRadioGroupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(qeRadioGroupService, 'compareQeRadioGroup');
        comp.compareQeRadioGroup(entity, entity2);
        expect(qeRadioGroupService.compareQeRadioGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
