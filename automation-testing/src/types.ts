export type UUID = string;

export enum JobStatus {
  PENDING = 'PENDING',
  PROCESSING = 'PROCESSING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
}

// export interface JobDto {
//   id: UUID;
//   name: string;
//   status: JobStatus;
//   createdAt: string;
//   updatedAt: string;
//   [key: string]: unknown;
// }

export interface CreateJobRequest {
  type: string;
  payload: any;
}

export interface SendEmailRequest extends CreateJobRequest {
  payload: SendEmailRequestPayload
}

type SendEmailRequestPayload = {
  recipient: string,
  subject: string,
  body: string,
  fail?: boolean,
}

export interface SendSmsRequest extends CreateJobRequest {
  payload: SendSmsRequestPayload
}

type SendSmsRequestPayload = {
  phoneNumber: string,
  message: string,
  fail?: boolean,
}

export interface SyncExternalDataRequest extends CreateJobRequest {
  payload: SyncExternalDataRequestPayload
}

type SyncExternalDataRequestPayload = {
  apiEndpoint: string,
  authToken: string,
  fail?: boolean,
}

export interface ScrapeWebsiteRequest extends CreateJobRequest {
  payload: ScrapeWebsiteRequestPayload
}

type ScrapeWebsiteRequestPayload = {
  url: string,
  keywords: string[],
  fail?: boolean,
}

export interface ProcessVideoRequest extends CreateJobRequest {
  payload: ProcessVideoRequestPayload
}

type ProcessVideoRequestPayload = {
  videoUrl: string,
  outputFormat: string,
  webhookUrl: string,
  fail?: boolean,
}

export interface CleanupTempFileRequest extends CreateJobRequest {
  payload: CleanupTempFileRequestPayload
}

type CleanupTempFileRequestPayload = {
  directory: string,
  fail?: boolean,
}

export interface CollectionQueryRequest {
  page?: number;
  size?: number;
  status?: string;
  [key: string]: unknown;
}

export interface CollectionQueryResponse<T> {
  content: T[];
  totalPages: number;
  page: number;
  size: number;
}
