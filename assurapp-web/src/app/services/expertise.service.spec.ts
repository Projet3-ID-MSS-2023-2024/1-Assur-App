import { TestBed } from '@angular/core/testing';

import { ExpertiseService } from './expertise.service';

describe('ExpertiseService', () => {
  let service: ExpertiseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExpertiseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
