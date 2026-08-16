import { test as base, expect } from '@playwright/test';
import { JobApiClient } from '@src/api';

type ApiFixtures = {
  jobApi: JobApiClient;
};

export const test = base.extend<ApiFixtures>({
  jobApi: async ({ request }, use) => {
    await use(new JobApiClient(request));
  },
});

export { expect };
