import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'questionnaire',
        data: { pageTitle: 'vegaJhApp.questionnaire.home.title' },
        loadChildren: () => import('./questionnaire/questionnaire.module').then(m => m.QuestionnaireModule),
      },
      {
        path: 'questionnaire-profile',
        data: { pageTitle: 'vegaJhApp.questionnaireProfile.home.title' },
        loadChildren: () => import('./questionnaire-profile/questionnaire-profile.module').then(m => m.QuestionnaireProfileModule),
      },
      {
        path: 'questionnaire-group',
        data: { pageTitle: 'vegaJhApp.questionnaireGroup.home.title' },
        loadChildren: () => import('./questionnaire-group/questionnaire-group.module').then(m => m.QuestionnaireGroupModule),
      },
      {
        path: 'qe-group',
        data: { pageTitle: 'vegaJhApp.qeGroup.home.title' },
        loadChildren: () => import('./qe-group/qe-group.module').then(m => m.QeGroupModule),
      },
      {
        path: 'qe-question',
        data: { pageTitle: 'vegaJhApp.qeQuestion.home.title' },
        loadChildren: () => import('./qe-question/qe-question.module').then(m => m.QeQuestionModule),
      },
      {
        path: 'qe-reply',
        data: { pageTitle: 'vegaJhApp.qeReply.home.title' },
        loadChildren: () => import('./qe-reply/qe-reply.module').then(m => m.QeReplyModule),
      },
      {
        path: 'qe-radio-box',
        data: { pageTitle: 'vegaJhApp.qeRadioBox.home.title' },
        loadChildren: () => import('./qe-radio-box/qe-radio-box.module').then(m => m.QeRadioBoxModule),
      },
      {
        path: 'qe-check-box',
        data: { pageTitle: 'vegaJhApp.qeCheckBox.home.title' },
        loadChildren: () => import('./qe-check-box/qe-check-box.module').then(m => m.QeCheckBoxModule),
      },
      {
        path: 'qe-radio-group',
        data: { pageTitle: 'vegaJhApp.qeRadioGroup.home.title' },
        loadChildren: () => import('./qe-radio-group/qe-radio-group.module').then(m => m.QeRadioGroupModule),
      },
      {
        path: 'qe-check-group',
        data: { pageTitle: 'vegaJhApp.qeCheckGroup.home.title' },
        loadChildren: () => import('./qe-check-group/qe-check-group.module').then(m => m.QeCheckGroupModule),
      },
      {
        path: 'qe-jump-expression',
        data: { pageTitle: 'vegaJhApp.qeJumpExpression.home.title' },
        loadChildren: () => import('./qe-jump-expression/qe-jump-expression.module').then(m => m.QeJumpExpressionModule),
      },
      {
        path: 'test-entity',
        data: { pageTitle: 'vegaJhApp.testEntity.home.title' },
        loadChildren: () => import('./test-entity/test-entity.module').then(m => m.TestEntityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
