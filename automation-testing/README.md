# Job API Tests

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
└── tests/
    └── flow.spec.ts
```

## Setup

```bash
cp .env.example .env
pnpm install
```

Edit `.env`:

```
BASE_URL=http://localhost:8080
```

## Running

```bash
pnpm test                # run everything headlessly
pnpm test:jobs       # just the /jobs suite
pnpm test:ui         # Playwright's interactive UI mode
pnpm report          # open the last HTML report
```
