import type { PageServerLoad } from './$types';
import { client } from '$lib/api';

export const load = (async ({ fetch }) => {
	return {
		openOperations: (await client.GET('/operations/names', { fetch })).data!
	}
}) satisfies PageServerLoad;

export const actions = {
	login: async () => console.log('Login called')
};
