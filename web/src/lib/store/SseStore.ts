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

export const createSseStore = () => {
	const sseStore = writable<SseEvent>();
	setContext(SSE_CTX, sseStore);
};

export const getSseStore = () => getContext<Writable<SseEvent>>(SSE_CTX);

export const setFromMessageEvent = (ev: MessageEvent) =>
	getSseStore().set({
		id: ev.lastEventId,
		name: ev.type as SseEventName,
		data: ev.data,
	});
