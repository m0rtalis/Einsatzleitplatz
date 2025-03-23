import { type Writable, writable } from 'svelte/store';
import { getContext, setContext } from 'svelte';

type PageData = { name: string };

const PAGE_CTX = Symbol.for('PAGE_CTX');

export const createPageStore = () => {
	const pageStore = writable<PageData>();
	setContext(PAGE_CTX, pageStore);
	return pageStore;
};

export const getPageStore = () => getContext<Writable<PageData>>(PAGE_CTX);

export const setPageName = (name: string) => getPageStore().update((data) => ({ ...data, name }));
