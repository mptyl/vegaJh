import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeGroupDetailComponent } from './qe-group-detail.component';

describe('QeGroup Management Detail Component', () => {
  let comp: QeGroupDetailComponent;
  let fixture: ComponentFixture<QeGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
