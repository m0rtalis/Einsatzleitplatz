import { writable, type Writable } from 'svelte/store';
import { browser } from '$app/environment';

export const local = <T, >(name: string, value: T): Writable<T> => storage<T>(name, value, 'local');

export const session = <T, >(name: string, value: T): Writable<T> => storage<T>(name, value, 'session');

// TODO: Listener for changes from other tabs
const storage = <T, >(name: string, value: T, storage: 'session' | 'local'): Writable<T> => {
	const item: string | null = getStorage(storage)?.getItem(name) ?? null;

	const store = writable<T>(item ? JSON.parse(item) : value);

	store.subscribe(value => {
		getStorage(storage)?.setItem(name, JSON.stringify(value));
	});

	return store;
};

const getStorage = (type: 'session' | 'local'): Storage | undefined => {
	if (!browser) return undefined;
	return type === 'session' ? sessionStorage : localStorage
}
