import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IQeJumpExpression } from '../qe-jump-expression.model';
import { QeJumpExpressionService } from '../service/qe-jump-expression.service';

import { QeJumpExpressionRoutingResolveService } from './qe-jump-expression-routing-resolve.service';

describe('QeJumpExpression routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: QeJumpExpressionRoutingResolveService;
  let service: QeJumpExpressionService;
  let resultQeJumpExpression: IQeJumpExpression | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(QeJumpExpressionRoutingResolveService);
    service = TestBed.inject(QeJumpExpressionService);
    resultQeJumpExpression = undefined;
  });

  describe('resolve', () => {
    it('should return IQeJumpExpression returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQeJumpExpression = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultQeJumpExpression).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQeJumpExpression = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultQeJumpExpression).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IQeJumpExpression>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQeJumpExpression = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultQeJumpExpression).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
