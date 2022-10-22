import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeRadioBoxDetailComponent } from './qe-radio-box-detail.component';

describe('QeRadioBox Management Detail Component', () => {
  let comp: QeRadioBoxDetailComponent;
  let fixture: ComponentFixture<QeRadioBoxDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeRadioBoxDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeRadioBox: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeRadioBoxDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeRadioBoxDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeRadioBox on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeRadioBox).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
