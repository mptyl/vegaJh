import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeCheckGroupDetailComponent } from './qe-check-group-detail.component';

describe('QeCheckGroup Management Detail Component', () => {
  let comp: QeCheckGroupDetailComponent;
  let fixture: ComponentFixture<QeCheckGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeCheckGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeCheckGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeCheckGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeCheckGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeCheckGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeCheckGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
