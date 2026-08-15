DROP TABLE IF EXISTS "job" CASCADE;
CREATE TABLE IF NOT EXISTS "job" (
  "id" UUID PRIMARY KEY,
  "type" VARCHAR NOT NULL,
  "status" VARCHAR NOT NULL,
  "retry_count" BIGINT NOT NULL,
  "payload" JSONB,
  "error_message" VARCHAR,
  "created_at" TIMESTAMPTZ NOT NULL,
  "updated_at" TIMESTAMPTZ NOT NULL,
);
