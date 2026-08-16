import { APIResponse, expect } from '@playwright/test';
import { ZodSchema } from 'zod';

export async function expectStatus(response: APIResponse, status: number): Promise<void> {
  if (response.status() !== status) {
    const body = await safeBody(response);
    expect(
      response.status(),
      `Expected ${status} but got ${response.status()} for ${response.url()}. Body: ${JSON.stringify(body)}`
    ).toBe(status);
  }
}

export async function expectSchema<T>(response: APIResponse, schema: ZodSchema<T>): Promise<T> {
  const body = await response.json();
  const result = schema.safeParse(body);
  if (!result.success) {
    throw new Error(`Schema validation failed:\n${JSON.stringify(result.error.format(), null, 2)}`);
  }
  return result.data;
}

async function safeBody(response: APIResponse): Promise<unknown> {
  try {
    return await response.json();
  } catch {
    return response.text();
  }
}
