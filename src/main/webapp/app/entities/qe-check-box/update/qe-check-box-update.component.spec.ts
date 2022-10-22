import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QeCheckBoxFormService } from './qe-check-box-form.service';
import { QeCheckBoxService } from '../service/qe-check-box.service';
import { IQeCheckBox } from '../qe-check-box.model';
import { IQeCheckGroup } from 'app/entities/qe-check-group/qe-check-group.model';
import { QeCheckGroupService } from 'app/entities/qe-check-group/service/qe-check-group.service';

import { QeCheckBoxUpdateComponent } from './qe-check-box-update.component';

describe('QeCheckBox Management Update Component', () => {
  let comp: QeCheckBoxUpdateComponent;
  let fixture: ComponentFixture<QeCheckBoxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let qeCheckBoxFormService: QeCheckBoxFormService;
  let qeCheckBoxService: QeCheckBoxService;
  let qeCheckGroupService: QeCheckGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QeCheckBoxUpdateComponent],
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
      .overrideTemplate(QeCheckBoxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeCheckBoxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    qeCheckBoxFormService = TestBed.inject(QeCheckBoxFormService);
    qeCheckBoxService = TestBed.inject(QeCheckBoxService);
    qeCheckGroupService = TestBed.inject(QeCheckGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QeCheckGroup query and add missing value', () => {
      const qeCheckBox: IQeCheckBox = { id: 456 };
      const qeCheckGroup: IQeCheckGroup = { id: 19353 };
      qeCheckBox.qeCheckGroup = qeCheckGroup;

      const qeCheckGroupCollection: IQeCheckGroup[] = [{ id: 86860 }];
      jest.spyOn(qeCheckGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: qeCheckGroupCollection })));
      const additionalQeCheckGroups = [qeCheckGroup];
      const expectedCollection: IQeCheckGroup[] = [...additionalQeCheckGroups, ...qeCheckGroupCollection];
      jest.spyOn(qeCheckGroupService, 'addQeCheckGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ qeCheckBox });
      comp.ngOnInit();

      expect(qeCheckGroupService.query).toHaveBeenCalled();
      expect(qeCheckGroupService.addQeCheckGroupToCollectionIfMissing).toHaveBeenCalledWith(
        qeCheckGroupCollection,
        ...additionalQeCheckGroups.map(expect.objectContaining)
      );
      expect(comp.qeCheckGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const qeCheckBox: IQeCheckBox = { id: 456 };
      const qeCheckGroup: IQeCheckGroup = { id: 5302 };
      qeCheckBox.qeCheckGroup = qeCheckGroup;

      activatedRoute.data = of({ qeCheckBox });
      comp.ngOnInit();

      expect(comp.qeCheckGroupsSharedCollection).toContain(qeCheckGroup);
      expect(comp.qeCheckBox).toEqual(qeCheckBox);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeCheckBox>>();
      const qeCheckBox = { id: 123 };
      jest.spyOn(qeCheckBoxFormService, 'getQeCheckBox').mockReturnValue(qeCheckBox);
      jest.spyOn(qeCheckBoxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeCheckBox });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeCheckBox }));
      saveSubject.complete();

      // THEN
      expect(qeCheckBoxFormService.getQeCheckBox).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(qeCheckBoxService.update).toHaveBeenCalledWith(expect.objectContaining(qeCheckBox));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeCheckBox>>();
      const qeCheckBox = { id: 123 };
      jest.spyOn(qeCheckBoxFormService, 'getQeCheckBox').mockReturnValue({ id: null });
      jest.spyOn(qeCheckBoxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeCheckBox: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: qeCheckBox }));
      saveSubject.complete();

      // THEN
      expect(qeCheckBoxFormService.getQeCheckBox).toHaveBeenCalled();
      expect(qeCheckBoxService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IQeCheckBox>>();
      const qeCheckBox = { id: 123 };
      jest.spyOn(qeCheckBoxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ qeCheckBox });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(qeCheckBoxService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareQeCheckGroup', () => {
      it('Should forward to qeCheckGroupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(qeCheckGroupService, 'compareQeCheckGroup');
        comp.compareQeCheckGroup(entity, entity2);
        expect(qeCheckGroupService.compareQeCheckGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
