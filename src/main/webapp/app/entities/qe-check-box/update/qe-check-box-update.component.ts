import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeCheckBoxFormService, QeCheckBoxFormGroup } from './qe-check-box-form.service';
import { IQeCheckBox } from '../qe-check-box.model';
import { QeCheckBoxService } from '../service/qe-check-box.service';
import { IQeCheckGroup } from 'app/entities/qe-check-group/qe-check-group.model';
import { QeCheckGroupService } from 'app/entities/qe-check-group/service/qe-check-group.service';

@Component({
  selector: 'jhi-qe-check-box-update',
  templateUrl: './qe-check-box-update.component.html',
})
export class QeCheckBoxUpdateComponent implements OnInit {
  isSaving = false;
  qeCheckBox: IQeCheckBox | null = null;

  qeCheckGroupsSharedCollection: IQeCheckGroup[] = [];

  editForm: QeCheckBoxFormGroup = this.qeCheckBoxFormService.createQeCheckBoxFormGroup();

  constructor(
    protected qeCheckBoxService: QeCheckBoxService,
    protected qeCheckBoxFormService: QeCheckBoxFormService,
    protected qeCheckGroupService: QeCheckGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeCheckGroup = (o1: IQeCheckGroup | null, o2: IQeCheckGroup | null): boolean =>
    this.qeCheckGroupService.compareQeCheckGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeCheckBox }) => {
      this.qeCheckBox = qeCheckBox;
      if (qeCheckBox) {
        this.updateForm(qeCheckBox);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeCheckBox = this.qeCheckBoxFormService.getQeCheckBox(this.editForm);
    if (qeCheckBox.id !== null) {
      this.subscribeToSaveResponse(this.qeCheckBoxService.update(qeCheckBox));
    } else {
      this.subscribeToSaveResponse(this.qeCheckBoxService.create(qeCheckBox));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeCheckBox>>): void {
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

  protected updateForm(qeCheckBox: IQeCheckBox): void {
    this.qeCheckBox = qeCheckBox;
    this.qeCheckBoxFormService.resetForm(this.editForm, qeCheckBox);

    this.qeCheckGroupsSharedCollection = this.qeCheckGroupService.addQeCheckGroupToCollectionIfMissing<IQeCheckGroup>(
      this.qeCheckGroupsSharedCollection,
      qeCheckBox.qeCheckGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeCheckGroupService
      .query()
      .pipe(map((res: HttpResponse<IQeCheckGroup[]>) => res.body ?? []))
      .pipe(
        map((qeCheckGroups: IQeCheckGroup[]) =>
          this.qeCheckGroupService.addQeCheckGroupToCollectionIfMissing<IQeCheckGroup>(qeCheckGroups, this.qeCheckBox?.qeCheckGroup)
        )
      )
      .subscribe((qeCheckGroups: IQeCheckGroup[]) => (this.qeCheckGroupsSharedCollection = qeCheckGroups));
  }
}
