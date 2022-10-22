import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { QeReplyService } from '../service/qe-reply.service';

import { QeReplyComponent } from './qe-reply.component';

describe('QeReply Management Component', () => {
  let comp: QeReplyComponent;
  let fixture: ComponentFixture<QeReplyComponent>;
  let service: QeReplyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'qe-reply', component: QeReplyComponent }]), HttpClientTestingModule],
      declarations: [QeReplyComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(QeReplyComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeReplyComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(QeReplyService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.qeReplies?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to qeReplyService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getQeReplyIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getQeReplyIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
