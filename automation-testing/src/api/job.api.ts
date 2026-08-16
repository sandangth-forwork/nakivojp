import { APIRequestContext, APIResponse } from '@playwright/test';
import { BaseApiClient } from './base.api';
import { CollectionQueryRequest, CreateJobRequest, UUID } from '@src/types';

export class JobApiClient extends BaseApiClient {
  private readonly basePath = '/api/jobs';

  constructor(request: APIRequestContext) {
    super(request);
  }

  async getAll(query?: CollectionQueryRequest): Promise<APIResponse> {
    return this.get(this.basePath, query);
  }

  async getDetails(id: UUID | string): Promise<APIResponse> {
    return this.get(`${this.basePath}/${id}`);
  }

  async create(payload: CreateJobRequest | Record<string, unknown>): Promise<APIResponse> {
    return this.post(this.basePath, payload);
  }

  async process(): Promise<APIResponse> {
    return this.post(`${this.basePath}/process`);
  }
}
