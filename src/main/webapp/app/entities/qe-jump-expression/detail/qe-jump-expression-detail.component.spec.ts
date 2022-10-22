import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeJumpExpressionDetailComponent } from './qe-jump-expression-detail.component';

describe('QeJumpExpression Management Detail Component', () => {
  let comp: QeJumpExpressionDetailComponent;
  let fixture: ComponentFixture<QeJumpExpressionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeJumpExpressionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeJumpExpression: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeJumpExpressionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeJumpExpressionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeJumpExpression on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeJumpExpression).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
