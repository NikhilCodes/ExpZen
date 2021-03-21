import { TestBed } from '@angular/core/testing';

import { DueService } from './due.service';

describe('DueService', () => {
  let service: DueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
