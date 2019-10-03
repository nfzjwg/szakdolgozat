import { TestBed, async } from '@angular/core/testing';

import { LocalStorageService } from './local-storage.service';
import { HttpClientModule } from '@angular/common/http';

describe('LocalDataService', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ]
    }).compileComponents();
  }));

  it('should be created', async(() => {
    const service: LocalStorageService = TestBed.get(LocalStorageService);
    expect(service).toBeTruthy();
  }));
});

