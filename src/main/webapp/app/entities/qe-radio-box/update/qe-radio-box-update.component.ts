import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeRadioBoxFormService, QeRadioBoxFormGroup } from './qe-radio-box-form.service';
import { IQeRadioBox } from '../qe-radio-box.model';
import { QeRadioBoxService } from '../service/qe-radio-box.service';
import { IQeRadioGroup } from 'app/entities/qe-radio-group/qe-radio-group.model';
import { QeRadioGroupService } from 'app/entities/qe-radio-group/service/qe-radio-group.service';

@Component({
  selector: 'jhi-qe-radio-box-update',
  templateUrl: './qe-radio-box-update.component.html',
})
export class QeRadioBoxUpdateComponent implements OnInit {
  isSaving = false;
  qeRadioBox: IQeRadioBox | null = null;

  qeRadioGroupsSharedCollection: IQeRadioGroup[] = [];

  editForm: QeRadioBoxFormGroup = this.qeRadioBoxFormService.createQeRadioBoxFormGroup();

  constructor(
    protected qeRadioBoxService: QeRadioBoxService,
    protected qeRadioBoxFormService: QeRadioBoxFormService,
    protected qeRadioGroupService: QeRadioGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeRadioGroup = (o1: IQeRadioGroup | null, o2: IQeRadioGroup | null): boolean =>
    this.qeRadioGroupService.compareQeRadioGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeRadioBox }) => {
      this.qeRadioBox = qeRadioBox;
      if (qeRadioBox) {
        this.updateForm(qeRadioBox);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeRadioBox = this.qeRadioBoxFormService.getQeRadioBox(this.editForm);
    if (qeRadioBox.id !== null) {
      this.subscribeToSaveResponse(this.qeRadioBoxService.update(qeRadioBox));
    } else {
      this.subscribeToSaveResponse(this.qeRadioBoxService.create(qeRadioBox));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeRadioBox>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(qeRadioBox: IQeRadioBox): void {
    this.qeRadioBox = qeRadioBox;
    this.qeRadioBoxFormService.resetForm(this.editForm, qeRadioBox);

    this.qeRadioGroupsSharedCollection = this.qeRadioGroupService.addQeRadioGroupToCollectionIfMissing<IQeRadioGroup>(
      this.qeRadioGroupsSharedCollection,
      qeRadioBox.qeRadioGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeRadioGroupService
      .query()
      .pipe(map((res: HttpResponse<IQeRadioGroup[]>) => res.body ?? []))
      .pipe(
        map((qeRadioGroups: IQeRadioGroup[]) =>
          this.qeRadioGroupService.addQeRadioGroupToCollectionIfMissing<IQeRadioGroup>(qeRadioGroups, this.qeRadioBox?.qeRadioGroup)
        )
      )
      .subscribe((qeRadioGroups: IQeRadioGroup[]) => (this.qeRadioGroupsSharedCollection = qeRadioGroups));
  }
}
