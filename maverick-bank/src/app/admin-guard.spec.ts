import { TestBed } from '@angular/core/testing';
import { AdminGuard } from './admin-guard';
import { AuthService } from './services/auth';
import { Router } from '@angular/router';

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const authSpy = jasmine.createSpyObj('AuthService', ['isAuthenticated', 'isAdmin']);
    const routerMock = jasmine.createSpyObj('Router', ['parseUrl']);

    TestBed.configureTestingModule({
      providers: [
        AdminGuard,
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routerMock }
      ]
    });

    guard = TestBed.inject(AdminGuard);
    authServiceSpy = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  it('should allow access for admin', () => {
    authServiceSpy.isAuthenticated.and.returnValue(true);
    authServiceSpy.isAdmin.and.returnValue(true);

    expect(guard.canActivate()).toBeTrue();
  });

  it('should redirect if not admin', () => {
    authServiceSpy.isAuthenticated.and.returnValue(true);
    authServiceSpy.isAdmin.and.returnValue(false);

    // 👇 Real UrlTree generation using real Router
    const realRouter = TestBed.inject(Router);
    const fakeUrlTree = realRouter.parseUrl('/customer/dashboard');
    routerSpy.parseUrl.and.returnValue(fakeUrlTree);

    expect(guard.canActivate()).toEqual(fakeUrlTree);
  });
});
