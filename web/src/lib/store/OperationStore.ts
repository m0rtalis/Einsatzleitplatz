import { session } from '$lib/store/persisted';
import { browser } from '$app/environment';

export const operationStore = session<{ id: string, name: string } | null>('Operation', null);

operationStore.subscribe(value => {
	if (browser && value) {
		document.cookie = `operationId=${value.id}; path=/`
	}
})