import { writable } from 'svelte/store';
import type { SseEventName } from '$lib/server/api';

export interface SseEvent {
	id: string;
	name: SseEventName;
	data?: string;
}

export const sseStore = writable<SseEvent | undefined>();

export const setFromMessageEvent = (ev: MessageEvent) => sseStore.set({
	id: ev.lastEventId,
	name: ev.type as SseEventName,
	data: ev.data
});