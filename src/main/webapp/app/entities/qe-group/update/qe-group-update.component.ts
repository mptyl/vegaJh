import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeGroupFormService, QeGroupFormGroup } from './qe-group-form.service';
import { IQeGroup } from '../qe-group.model';
import { QeGroupService } from '../service/qe-group.service';
import { IQuestionnaire } from 'app/entities/questionnaire/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire/service/questionnaire.service';

@Component({
  selector: 'jhi-qe-group-update',
  templateUrl: './qe-group-update.component.html',
})
export class QeGroupUpdateComponent implements OnInit {
  isSaving = false;
  qeGroup: IQeGroup | null = null;

  questionnairesSharedCollection: IQuestionnaire[] = [];

  editForm: QeGroupFormGroup = this.qeGroupFormService.createQeGroupFormGroup();

  constructor(
    protected qeGroupService: QeGroupService,
    protected qeGroupFormService: QeGroupFormService,
    protected questionnaireService: QuestionnaireService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQuestionnaire = (o1: IQuestionnaire | null, o2: IQuestionnaire | null): boolean =>
    this.questionnaireService.compareQuestionnaire(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeGroup }) => {
      this.qeGroup = qeGroup;
      if (qeGroup) {
        this.updateForm(qeGroup);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeGroup = this.qeGroupFormService.getQeGroup(this.editForm);
    if (qeGroup.id !== null) {
      this.subscribeToSaveResponse(this.qeGroupService.update(qeGroup));
    } else {
      this.subscribeToSaveResponse(this.qeGroupService.create(qeGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeGroup>>): void {
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

  protected updateForm(qeGroup: IQeGroup): void {
    this.qeGroup = qeGroup;
    this.qeGroupFormService.resetForm(this.editForm, qeGroup);

    this.questionnairesSharedCollection = this.questionnaireService.addQuestionnaireToCollectionIfMissing<IQuestionnaire>(
      this.questionnairesSharedCollection,
      qeGroup.questionnaire
    );
  }

  protected loadRelationshipsOptions(): void {
    this.questionnaireService
      .query()
      .pipe(map((res: HttpResponse<IQuestionnaire[]>) => res.body ?? []))
      .pipe(
        map((questionnaires: IQuestionnaire[]) =>
          this.questionnaireService.addQuestionnaireToCollectionIfMissing<IQuestionnaire>(questionnaires, this.qeGroup?.questionnaire)
        )
      )
      .subscribe((questionnaires: IQuestionnaire[]) => (this.questionnairesSharedCollection = questionnaires));
  }
}
