# Job API Tests

Playwright + TypeScript API test suite for the `JobController` endpoints:

| Method | Path            | Covered by                        |
|--------|-----------------|------------------------------------|
| GET    | `/jobs`         | `tests/jobs/jobs-get-all.spec.ts`     |
| GET    | `/jobs/{id}`    | `tests/jobs/jobs-get-details.spec.ts` |
| POST   | `/jobs`         | `tests/jobs/jobs-create.spec.ts`      |
| POST   | `/jobs/process` | `tests/jobs/jobs-process.spec.ts`     |

## ⚠️ Assumptions to verify first

The controller you shared references DTOs (`JobDto`, `CreateJobRequest`,
`CollectionQueryRequest`, `CollectionQueryResponse<T>`) whose fields weren't
included. This project assumes:

- `CollectionQueryResponse<T>` is a Spring Data `Page`-like shape:
  `{ content, totalElements, totalPages, page, size }`.
- `JobDto` has at least `id`, `name`, `status`, `createdAt`, `updatedAt`.
- `CreateJobRequest` has a required `name` and optional `description`, `type`,
  `parameters`.
- `CollectionQueryRequest` accepts `page`, `size`, `sort`, `search` as query
  params (bound via `@ModelAttribute`).
- A malformed UUID path variable resolves to Spring's default `400 Bad
  Request` (no custom `@ExceptionHandler` overriding that).

**Before running the suite**, open `src/types/job.types.ts` and
`src/schemas/job.schema.ts` and align them with your real DTOs — everything
else in the project is built on top of those two files, so fixing them there
keeps the rest of the suite consistent.

## Project structure

```
playwright-api-tests/
├── playwright.config.ts       # baseURL, headers, reporters
├── src/
│   ├── types/job.types.ts     # TS interfaces for the DTOs (⚠️ verify)
│   ├── schemas/job.schema.ts  # zod schemas used to validate responses at runtime
│   ├── api/
│   │   ├── base.api.ts        # thin wrapper around APIRequestContext
│   │   └── job.api.ts         # one method per controller endpoint
│   ├── fixtures/api.fixture.ts# injects a ready-to-use `jobApi` into tests
│   ├── data/job.factory.ts    # faker-based valid/invalid payload builders
│   └── utils/assertions.ts    # expectStatus / expectSchema helpers
└── tests/jobs/
    ├── jobs-get-all.spec.ts
    ├── jobs-get-details.spec.ts
    ├── jobs-create.spec.ts
    └── jobs-process.spec.ts
```

## Setup

```bash
npm install
npx playwright install   # not strictly needed for pure API tests, but harmless
cp .env.example .env
```

Edit `.env`:

```
BASE_URL=http://localhost:8080
API_TOKEN=            # only if your endpoints require a bearer token
```

## Running

```bash
npm test                # run everything headlessly
npm run test:jobs       # just the /jobs suite
npm run test:ui         # Playwright's interactive UI mode
npm run report          # open the last HTML report
```

## Design notes

- **No UI/browser dependency** — tests use Playwright's `request` fixture
  (`APIRequestContext`), so no browsers are launched; this suite is fast and
  CI-friendly.
- **Schema validation, not just status codes** — `expectSchema()` parses
  every response body against a zod schema, so a field being renamed or
  dropped on the backend fails the test with a precise diff instead of a
  downstream `undefined` error.
- **Client layer (`JobApiClient`)** isolates HTTP details (paths, param
  cleanup) from test logic, so if a route changes you edit one file.
- **Data factory** builds realistic payloads with `@faker-js/faker` so tests
  aren't coupled to a single hardcoded value and can run repeatedly without
  unique-constraint collisions.
- **`.passthrough()`** is used on the `JobDto` zod schema so the tests don't
  break if the backend adds extra fields — they only fail if a field they
  actually assert on is missing/wrong-typed.

## Extending

- Add new endpoints: extend `JobApiClient` with a method, add/extend a zod
  schema, write a spec file under `tests/jobs/`.
- Add auth flows (login → token): create a `global-setup.ts` that calls the
  auth endpoint and writes a storage state or exports `API_TOKEN`, then wire
  it into `playwright.config.ts` via `globalSetup`.
