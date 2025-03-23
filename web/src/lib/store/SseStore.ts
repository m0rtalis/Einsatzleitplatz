import { type Writable, writable } from 'svelte/store';
import type { SseEventName } from '$lib/api';
import { getContext, setContext } from 'svelte';

export type SseEvent =
	| {
			id: string;
			name: SseEventName;
			data?: string;
	  }
	| undefined;

const SSE_CTX = Symbol.for('SSE_CTX');

// Honestly not sure if this is how it's done, but it works for now so I might come back to it later
let thisSseStore: Writable<SseEvent>;

export const createSseStore = () => {
	const sseStore = writable<SseEvent>();
	setContext(SSE_CTX, sseStore);
	thisSseStore = sseStore;
};

export const getSseStore = () => getContext<Writable<SseEvent>>(SSE_CTX);

export const setFromMessageEvent = (ev: MessageEvent) => {
	thisSseStore.set({
		id: ev.lastEventId,
		name: ev.type as SseEventName,
		data: ev.data,
	});
};
