import { buildCleanupTempFileRequest, buildProcessVideoRequest, buildScrapeWebsiteRequest, buildSendEmailRequest, buildSendSmsRequest, buildSyncExternalDataRequest, randomUUID } from '@src/data';
import { test, expect } from '@src/fixtures';
import { CollectionQueryResponseSchema, JobDtoSchema } from '@src/schemas';
import { expectSchema, expectStatus } from '@src/utils';


test.describe('Whole flow', () => {

  test('Step 1: GET empty all jobs', async ({ jobApi }) => {
    const response = await jobApi.getAll();
    await expectStatus(response, 200);
    const body = await expectSchema(response, CollectionQueryResponseSchema);
    expect(Array.isArray(body.data)).toBeTruthy();
    expect(body.page).toEqual(0);
    expect(body.total).toEqual(0);
    expect(body.totalPages).toEqual(0);
  });

  test('Step 2: returns 404 for a job that does not exist', async ({ jobApi }) => {
    const response = await jobApi.getDetails(randomUUID());
    await expectStatus(response, 404);
  });

  test.describe('Step 3: Create jobs', () => {
    test('Sending email jobs', async ({ jobApi }) => {
      const payload = buildSendEmailRequest(false);
      const falsePayload = buildSendEmailRequest(false);
      for (const item of [payload, falsePayload]) {
        const response = await jobApi.create(item);
        await expectStatus(response, 201);
        const job = await expectSchema(response, JobDtoSchema);
        expect(job.id).toBeTruthy();
      }
    });


    test('Scraping website jobs', async ({ jobApi }) => {
      const payload = buildScrapeWebsiteRequest(false);
      const response = await jobApi.create(payload);
      await expectStatus(response, 201);
      const job = await expectSchema(response, JobDtoSchema);
      expect(job.id).toBeTruthy();
    });


    test('Sending sms jobs', async ({ jobApi }) => {
      const payload = buildSendSmsRequest(false);
      const falsePayload = buildSendSmsRequest(false);
      for (const item of [payload, falsePayload]) {
        const response = await jobApi.create(item);
        await expectStatus(response, 201);
        const job = await expectSchema(response, JobDtoSchema);
        expect(job.id).toBeTruthy();
      }
    });


    test('Syncing external data jobs', async ({ jobApi }) => {
      const falsePayload = buildSyncExternalDataRequest(false);
      const response = await jobApi.create(falsePayload);
      await expectStatus(response, 201);
      const job = await expectSchema(response, JobDtoSchema);
      expect(job.id).toBeTruthy();
    });


    test('Processing video jobs', async ({ jobApi }) => {
      const payload = buildProcessVideoRequest(false)
      const response = await jobApi.create(payload);
      await expectStatus(response, 201);
      const job = await expectSchema(response, JobDtoSchema);
      expect(job.id).toBeTruthy();
    });


    test('Cleaning up temp files jobs', async ({ jobApi }) => {
      const payload = buildCleanupTempFileRequest(false)
      const response = await jobApi.create(payload);
      await expectStatus(response, 201);
      const job = await expectSchema(response, JobDtoSchema);
      expect(job.id).toBeTruthy();
    });

    test('Check idempotency feature by sending duplicated message', async ({ jobApi }) => {
      const payload = buildSendEmailRequest(false);
      let response = await jobApi.create(payload);
      await expectStatus(response, 201);
      const job = await expectSchema(response, JobDtoSchema);
      expect(job.id).toBeTruthy();
      response = await jobApi.create(payload);
      await expectStatus(response, 425)
    })
  })

  test.describe('Step 4: Check created job', () => {
    test('Check list of created jobs from step 3', async ({ jobApi }) => {
      const response = await jobApi.getAll();
      await expectStatus(response, 200);
      const body = await expectSchema(response, CollectionQueryResponseSchema);
      expect(Array.isArray(body.data)).toBeTruthy();
      expect(body.page).toBeGreaterThanOrEqual(0);
      expect(body.total).toBeGreaterThan(0);
      expect(body.totalPages).toBeGreaterThan(0);
      expect(body.data.every(inner => inner.status === 'PENDING')).toBeTruthy();
    })
  });

  test.describe('Step 5: Process all pending jobs', () => {
    test('Process all pending jobs', async ({ jobApi }) => {
      let response = await jobApi.process();
      await expectStatus(response, 202);
      response = await jobApi.getAll();
      await expectStatus(response, 200);
      const body = await expectSchema(response, CollectionQueryResponseSchema);
      expect(body.data.every(inner => inner.status !== 'PENDING')).toBeTruthy();
    })
  });

  test.describe('Step 6: Check all jobs', () => {
    test('Make sure there is not PENDING or PROCESSING jobs', async ({ jobApi }) => {
      await new Promise((resolve) => setTimeout(resolve, 5_000));
      const response = await jobApi.getAll();
      await expectStatus(response, 200);
      const body = await expectSchema(response, CollectionQueryResponseSchema);
      expect(body.data.every(inner => !['PENDING', 'PROCESSING'].includes(inner.status))).toBeTruthy();
    })
  });
})
