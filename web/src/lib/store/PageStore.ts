import { writable } from 'svelte/store';

export const pageStore = writable<{ name: string }>();

export const setPageName = (name: string) => pageStore.update(data => ({ ...data, name }));
