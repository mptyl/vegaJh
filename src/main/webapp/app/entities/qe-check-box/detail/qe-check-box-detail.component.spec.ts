import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeCheckBoxDetailComponent } from './qe-check-box-detail.component';

describe('QeCheckBox Management Detail Component', () => {
  let comp: QeCheckBoxDetailComponent;
  let fixture: ComponentFixture<QeCheckBoxDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeCheckBoxDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeCheckBox: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeCheckBoxDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeCheckBoxDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeCheckBox on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeCheckBox).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
