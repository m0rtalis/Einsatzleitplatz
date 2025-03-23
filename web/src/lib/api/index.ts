import type { components } from '$lib/api/elp';

export type * as Schema from './elp';
export { EventName } from './elp';

export type JournalEntry = components['schemas']['JournalEntry'];
export type SseEventName = components['schemas']['EventName'];
export type User = components['schemas']['User'];
