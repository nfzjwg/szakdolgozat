import { TestBed } from '@angular/core/testing';

import { MotobikeService } from './motobike.service';

describe('MotobikeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MotobikeService = TestBed.get(MotobikeService);
    expect(service).toBeTruthy();
  });
});
