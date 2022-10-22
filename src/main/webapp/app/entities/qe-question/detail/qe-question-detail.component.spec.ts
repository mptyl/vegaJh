import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeQuestionDetailComponent } from './qe-question-detail.component';

describe('QeQuestion Management Detail Component', () => {
  let comp: QeQuestionDetailComponent;
  let fixture: ComponentFixture<QeQuestionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeQuestionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeQuestion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeQuestionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeQuestionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeQuestion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeQuestion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
