import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { QeCheckGroupService } from '../service/qe-check-group.service';

import { QeCheckGroupComponent } from './qe-check-group.component';

describe('QeCheckGroup Management Component', () => {
  let comp: QeCheckGroupComponent;
  let fixture: ComponentFixture<QeCheckGroupComponent>;
  let service: QeCheckGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'qe-check-group', component: QeCheckGroupComponent }]), HttpClientTestingModule],
      declarations: [QeCheckGroupComponent],
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
      .overrideTemplate(QeCheckGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeCheckGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(QeCheckGroupService);

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
    expect(comp.qeCheckGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to qeCheckGroupService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getQeCheckGroupIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getQeCheckGroupIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
