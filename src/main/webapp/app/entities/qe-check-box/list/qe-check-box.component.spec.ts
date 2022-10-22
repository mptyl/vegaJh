import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { QeCheckBoxService } from '../service/qe-check-box.service';

import { QeCheckBoxComponent } from './qe-check-box.component';

describe('QeCheckBox Management Component', () => {
  let comp: QeCheckBoxComponent;
  let fixture: ComponentFixture<QeCheckBoxComponent>;
  let service: QeCheckBoxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'qe-check-box', component: QeCheckBoxComponent }]), HttpClientTestingModule],
      declarations: [QeCheckBoxComponent],
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
      .overrideTemplate(QeCheckBoxComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeCheckBoxComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(QeCheckBoxService);

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
    expect(comp.qeCheckBoxes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to qeCheckBoxService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getQeCheckBoxIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getQeCheckBoxIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
