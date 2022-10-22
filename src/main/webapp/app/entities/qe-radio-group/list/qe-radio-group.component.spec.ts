import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { QeRadioGroupService } from '../service/qe-radio-group.service';

import { QeRadioGroupComponent } from './qe-radio-group.component';

describe('QeRadioGroup Management Component', () => {
  let comp: QeRadioGroupComponent;
  let fixture: ComponentFixture<QeRadioGroupComponent>;
  let service: QeRadioGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'qe-radio-group', component: QeRadioGroupComponent }]), HttpClientTestingModule],
      declarations: [QeRadioGroupComponent],
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
      .overrideTemplate(QeRadioGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeRadioGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(QeRadioGroupService);

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
    expect(comp.qeRadioGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to qeRadioGroupService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getQeRadioGroupIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getQeRadioGroupIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
