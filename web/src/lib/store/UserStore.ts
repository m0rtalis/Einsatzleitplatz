import { session } from '$lib/store/persisted';

export const userStore = session<{username: string} | null>("User", null)
