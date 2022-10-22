import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QeReplyDetailComponent } from './qe-reply-detail.component';

describe('QeReply Management Detail Component', () => {
  let comp: QeReplyDetailComponent;
  let fixture: ComponentFixture<QeReplyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QeReplyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ qeReply: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QeReplyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QeReplyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load qeReply on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.qeReply).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
