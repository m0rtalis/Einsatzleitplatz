import { session } from '$lib/store/persisted';
import { getContext, setContext } from 'svelte';
import type { Writable } from 'svelte/store';

type UserData = { username: string } | null;

const USER_CTX = Symbol.for('USER_CTX');

export const createUserStore = () => {
	const userStore = session<UserData>('User', null);
	setContext(USER_CTX, userStore);
};

export const getUserStore = () => getContext<Writable<UserData>>(USER_CTX);
