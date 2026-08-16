import { APIRequestContext, APIResponse } from '@playwright/test';

export class BaseApiClient {
  constructor(protected readonly request: APIRequestContext) {}

  protected async get(url: string, params?: Record<string, unknown>): Promise<APIResponse> {
    return this.request.get(url, { params: this.cleanParams(params) });
  }

  protected async post(url: string, data?: unknown): Promise<APIResponse> {
    return this.request.post(url, { data });
  }

  protected async put(url: string, data?: unknown): Promise<APIResponse> {
    return this.request.put(url, { data });
  }

  protected async delete(url: string): Promise<APIResponse> {
    return this.request.delete(url);
  }

  /** Playwright rejects undefined/null query param values, so strip them. */
  private cleanParams(params?: Record<string, unknown>): Record<string, string> | undefined {
    if (!params) return undefined;
    const cleaned: Record<string, string> = {};
    for (const [key, value] of Object.entries(params)) {
      if (value !== undefined && value !== null) {
        cleaned[key] = String(value);
      }
    }
    return cleaned;
  }
}
