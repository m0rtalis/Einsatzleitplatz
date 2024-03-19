import type { PageServerLoad } from './$types';
import { client } from '$lib/server/api';

export const load = (async ({ fetch }) => {

	return {
		journalTypes: (await client.GET('/journal/types', { fetch })).data!
	};

}) satisfies PageServerLoad;

export const actions = {
	journal: async ({ request, fetch, cookies }) => {
		let data = await request.formData();
		const type = data.get('type') as string;
		const entry = data.get('entry') as string;
		const operationId = cookies.get("operation")!;

		const response = await client.POST('/journal', { body: {operationId, type, event: entry}, fetch });
	}
};
