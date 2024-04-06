import { session } from '$lib/store/persisted';
import { browser } from '$app/environment';
import { getContext, setContext } from 'svelte';
import type { Writable } from 'svelte/store';
import parseCookie from 'cookie';

type OperationData = { id: string, name: string } | null;

const OPERATION_CTX = Symbol.for('OPERATION_CTX');

export const createOperationStore = (operation: OperationData = null) => {
	const operationStore = session<OperationData>('Operation', operation);
	setContext(OPERATION_CTX, operationStore);
	operationStore.subscribe(value => {
		if (browser && value) {
			document.cookie = parseCookie.serialize("operationId", value.id, {path: "/", sameSite: 'strict'})
		}
	});
	return operationStore;
};

export const getOperationStore = () => getContext<Writable<OperationData>>(OPERATION_CTX);