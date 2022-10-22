import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeRadioGroupFormService, QeRadioGroupFormGroup } from './qe-radio-group-form.service';
import { IQeRadioGroup } from '../qe-radio-group.model';
import { QeRadioGroupService } from '../service/qe-radio-group.service';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';
import { Orientation } from 'app/entities/enumerations/orientation.model';

@Component({
  selector: 'jhi-qe-radio-group-update',
  templateUrl: './qe-radio-group-update.component.html',
})
export class QeRadioGroupUpdateComponent implements OnInit {
  isSaving = false;
  qeRadioGroup: IQeRadioGroup | null = null;
  orientationValues = Object.keys(Orientation);

  qeQuestionsSharedCollection: IQeQuestion[] = [];

  editForm: QeRadioGroupFormGroup = this.qeRadioGroupFormService.createQeRadioGroupFormGroup();

  constructor(
    protected qeRadioGroupService: QeRadioGroupService,
    protected qeRadioGroupFormService: QeRadioGroupFormService,
    protected qeQuestionService: QeQuestionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeQuestion = (o1: IQeQuestion | null, o2: IQeQuestion | null): boolean => this.qeQuestionService.compareQeQuestion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeRadioGroup }) => {
      this.qeRadioGroup = qeRadioGroup;
      if (qeRadioGroup) {
        this.updateForm(qeRadioGroup);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeRadioGroup = this.qeRadioGroupFormService.getQeRadioGroup(this.editForm);
    if (qeRadioGroup.id !== null) {
      this.subscribeToSaveResponse(this.qeRadioGroupService.update(qeRadioGroup));
    } else {
      this.subscribeToSaveResponse(this.qeRadioGroupService.create(qeRadioGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeRadioGroup>>): void {
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

  protected updateForm(qeRadioGroup: IQeRadioGroup): void {
    this.qeRadioGroup = qeRadioGroup;
    this.qeRadioGroupFormService.resetForm(this.editForm, qeRadioGroup);

    this.qeQuestionsSharedCollection = this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(
      this.qeQuestionsSharedCollection,
      qeRadioGroup.qeQuestion
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeQuestionService
      .query()
      .pipe(map((res: HttpResponse<IQeQuestion[]>) => res.body ?? []))
      .pipe(
        map((qeQuestions: IQeQuestion[]) =>
          this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(qeQuestions, this.qeRadioGroup?.qeQuestion)
        )
      )
      .subscribe((qeQuestions: IQeQuestion[]) => (this.qeQuestionsSharedCollection = qeQuestions));
  }
}
