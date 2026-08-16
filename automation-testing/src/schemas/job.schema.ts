import { z } from 'zod';

export const JobStatusSchema = z.enum(['PENDING', 'PROCESSING', 'COMPLETED', 'FAILED']);
export const JobTypeSchema = z.enum([
    'SEND_EMAIL',
    'SEND_SMS',
    'PROCESS_VIDEO',
    'CLEANUP_TEMP_FILES',
    'SYNC_EXTERNAL_DATA',
    'SCRAPE_WEBSITE',
    'UNKNOWN',
]);

export const JobDtoSchema = z
  .object({
    id: z.string().uuid(),
    type: JobTypeSchema,
    status: JobStatusSchema,
    errorMesage: z.string().optional(),
    payload: z.any(),
    createdAt: z.number(),
    updatedAt: z.number(),
  })
  .passthrough();

export const CollectionQueryResponseSchema = z.object({
  data: z.array(JobDtoSchema),
  totalPages: z.number(),
  page: z.number(),
  total: z.number(),
});

export type JobDtoParsed = z.infer<typeof JobDtoSchema>;
export type CollectionQueryResponseParsed = z.infer<typeof CollectionQueryResponseSchema>;
