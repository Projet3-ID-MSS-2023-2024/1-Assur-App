import { TestBed } from '@angular/core/testing';

import { ActivateAccountService } from './activate-account.service';

describe('ActivateAccountService', () => {
  let service: ActivateAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActivateAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
