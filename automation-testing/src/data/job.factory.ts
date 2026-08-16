import { faker } from '@faker-js/faker';
import { CleanupTempFileRequest, CreateJobRequest, ProcessVideoRequest, ScrapeWebsiteRequest, SendEmailRequest, SendSmsRequest, SyncExternalDataRequest } from '@src/types';

export function buildCreateJobRequest(overrides: Partial<CreateJobRequest> = {}): CreateJobRequest {
  return {
    name: `job-${faker.word.sample()}-${faker.string.alphanumeric(6)}`,
    description: faker.lorem.sentence(),
    type: faker.helpers.arrayElement(['IMPORT', 'EXPORT', 'SYNC']),
    parameters: { batchSize: faker.number.int({ min: 1, max: 100 }) },
    ...overrides,
  };
}

export const buildSendEmailRequest = (fail: boolean): SendEmailRequest => {
  return {
    type: 'SEND_EMAIL',
    payload: {
      recipient: faker.internet.email(),
      subject: faker.lorem.sentence(10),
      body: faker.lorem.paragraphs(10),
      fail,
    }
  }
}

export const buildSendSmsRequest = (fail: boolean): SendSmsRequest => {
  return {
    type: 'SEND_SMS',
    payload: {
      phoneNumber: faker.phone.number(),
      message: faker.lorem.sentences(2, '.'),
      fail,
    }
  }
}

export const buildSyncExternalDataRequest = (fail: boolean): SyncExternalDataRequest => {
  return {
    type: 'SYNC_EXTERNAL_DATA',
    payload: {
      apiEndpoint: faker.internet.url(),
      authToken: faker.lorem.slug(5),
      fail,
    }
  }
}

export const buildScrapeWebsiteRequest = (fail: boolean): ScrapeWebsiteRequest => {
  return {
    type: 'SCRAPE_WEBSITE',
    payload: {
      url: faker.internet.url(),
      keywords: faker.helpers.multiple(faker.lorem.word, { count: 11 }),
      fail,
    }
  }
}

export const buildProcessVideoRequest = (fail: boolean): ProcessVideoRequest => {
  return {
    type: 'PROCESS_VIDEO',
    payload: {
      videoUrl: faker.internet.url(),
      outputFormat: faker.lorem.word(),
      webhookUrl: faker.internet.url(),
      fail,
    }
  }
}

export const buildCleanupTempFileRequest = (fail: boolean): CleanupTempFileRequest => {
  return {
    type: 'CLEANUP_TEMP_FILES',
    payload: {
      directory: faker.git.branch(),
      fail,
    }
  }
}

export const randomUUID = (): string => faker.string.uuid();
