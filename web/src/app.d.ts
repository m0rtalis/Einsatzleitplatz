import 'unplugin-icons/types/svelte';

// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces
declare global {
	namespace App {
		interface Error {
			message: string;
			code: number;
		}

		interface Locals {
			operationId: string; // Only available below /(app) route
		}

		// TODO: Look into PageData and PageState
		// interface PageData {}
		// interface PageState {}
		// interface Platform {}
	}
}

export {};
