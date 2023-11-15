import { TestBed } from '@angular/core/testing';

import { SinistreService } from './sinistre.service';

describe('SinistreService', () => {
  let service: SinistreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SinistreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
