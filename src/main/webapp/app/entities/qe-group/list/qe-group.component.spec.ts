import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { QeGroupService } from '../service/qe-group.service';

import { QeGroupComponent } from './qe-group.component';

describe('QeGroup Management Component', () => {
  let comp: QeGroupComponent;
  let fixture: ComponentFixture<QeGroupComponent>;
  let service: QeGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'qe-group', component: QeGroupComponent }]), HttpClientTestingModule],
      declarations: [QeGroupComponent],
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
      .overrideTemplate(QeGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QeGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(QeGroupService);

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
    expect(comp.qeGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to qeGroupService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getQeGroupIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getQeGroupIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
