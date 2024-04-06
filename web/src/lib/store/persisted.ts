import { writable, type Writable } from 'svelte/store';
import { browser } from '$app/environment';

export type Options<T> = {
	serializer?: (value: T) => string;
	deserializer?: (value: string) => T;
}

export const local = <T, >(name: string, value: T, options?: Options<T>): Writable<T> => storage<T>(name, value, 'local', options);

export const session = <T, >(name: string, value: T, options?: Options<T>): Writable<T> => storage<T>(name, value, 'session', options);

// TODO: Listener for changes from other tabs
// Can we somehow make it so T can only be primitive or Immutable?
const storage = <T, >(name: string, value: T, storage: 'session' | 'local', options?: Options<T>): Writable<T> => {
	const storageObject = getStorage(storage);
	const serializer = options?.serializer ?? JSON.stringify;
	const deserializer = options?.deserializer ?? JSON.parse;

	const item: string | null = storageObject?.getItem(name) ?? null;

	const store = writable<T>(item ? deserializer(item) : value);

	store.subscribe(value => {
		storageObject?.setItem(name, serializer(value));
	});

	return store;
};

const getStorage = (type: 'session' | 'local'): Storage | undefined => {
	if (!browser) return undefined;
	return type === 'session' ? sessionStorage : localStorage;
};
