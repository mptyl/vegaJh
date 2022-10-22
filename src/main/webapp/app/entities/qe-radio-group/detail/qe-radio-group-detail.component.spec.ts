import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeRadioGroupDetailComponent } from './qe-radio-group-detail.component';

describe('QeRadioGroup Management Detail Component', () => {
  let comp: QeRadioGroupDetailComponent;
  let fixture: ComponentFixture<QeRadioGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeRadioGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeRadioGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeRadioGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeRadioGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeRadioGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeRadioGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
