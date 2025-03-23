# Einsatzleitplatz (ELP)
## Backend
The packages should probably all be separate modules or at least make sure it's not possible to import between them except shared and common.

TODO: Don't use java.util.Date
https://github.com/google/error-prone
TODO: Fail if Hibernate create table fails

Import order and formatting, java below, static very bottom

### Testing Strategy

Realistically most tests will be integration tests.

#### Unit Tests
Test one specific unit in isolation.

Files are named "xxxTests"

#### Integration Tests
Test Components. With the database setup but usually skip the controller layer.

Files are named "xxxIntTests"

#### Functional Tests
Test the API, expect for test setup only communication is via the API.

Files are named "xxxFuncTests"

## Frontend

Right now only working on success path and forcing all undefined away with !

https://www.shadcn-svelte.com/ looks pretty nice. Would probably use it in a prod app. But for now want to learn / stay close to vanilla html and css

https://atomiks.github.io/tippyjs/

https://jenil.github.io/chota/
offers dark mode

https://icongr.am/

unlighthouse and web vitals (chrome)

Use syncpack and depcheck after switch to pnpm
https://adamcoster.com/blog/pnpm-config