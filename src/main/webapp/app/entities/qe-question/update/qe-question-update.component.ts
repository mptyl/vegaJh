import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeQuestionFormService, QeQuestionFormGroup } from './qe-question-form.service';
import { IQeQuestion } from '../qe-question.model';
import { QeQuestionService } from '../service/qe-question.service';
import { IQeGroup } from 'app/entities/qe-group/qe-group.model';
import { QeGroupService } from 'app/entities/qe-group/service/qe-group.service';

@Component({
  selector: 'jhi-qe-question-update',
  templateUrl: './qe-question-update.component.html',
})
export class QeQuestionUpdateComponent implements OnInit {
  isSaving = false;
  qeQuestion: IQeQuestion | null = null;

  qeGroupsSharedCollection: IQeGroup[] = [];

  editForm: QeQuestionFormGroup = this.qeQuestionFormService.createQeQuestionFormGroup();

  constructor(
    protected qeQuestionService: QeQuestionService,
    protected qeQuestionFormService: QeQuestionFormService,
    protected qeGroupService: QeGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeGroup = (o1: IQeGroup | null, o2: IQeGroup | null): boolean => this.qeGroupService.compareQeGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeQuestion }) => {
      this.qeQuestion = qeQuestion;
      if (qeQuestion) {
        this.updateForm(qeQuestion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeQuestion = this.qeQuestionFormService.getQeQuestion(this.editForm);
    if (qeQuestion.id !== null) {
      this.subscribeToSaveResponse(this.qeQuestionService.update(qeQuestion));
    } else {
      this.subscribeToSaveResponse(this.qeQuestionService.create(qeQuestion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeQuestion>>): void {
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

  protected updateForm(qeQuestion: IQeQuestion): void {
    this.qeQuestion = qeQuestion;
    this.qeQuestionFormService.resetForm(this.editForm, qeQuestion);

    this.qeGroupsSharedCollection = this.qeGroupService.addQeGroupToCollectionIfMissing<IQeGroup>(
      this.qeGroupsSharedCollection,
      qeQuestion.qeGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeGroupService
      .query()
      .pipe(map((res: HttpResponse<IQeGroup[]>) => res.body ?? []))
      .pipe(
        map((qeGroups: IQeGroup[]) => this.qeGroupService.addQeGroupToCollectionIfMissing<IQeGroup>(qeGroups, this.qeQuestion?.qeGroup))
      )
      .subscribe((qeGroups: IQeGroup[]) => (this.qeGroupsSharedCollection = qeGroups));
  }
}
