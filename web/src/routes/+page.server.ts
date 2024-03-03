import type { PageServerLoad } from './$types';
import { client } from '$lib/api';

export const load = (async ({ fetch }) => {
	return {
		openOperations: (await client.GET('/operations/names', { fetch })).data!
	}
}) satisfies PageServerLoad;

export const actions = {
	login: async ({request}) => {
		console.log('Login called', await request.formData())
		// 1. Login user
		// 2. If "createNewOperation=true": Create new Operation
		// 3. Set operationId in store
		// 4. redirect
	}
};
