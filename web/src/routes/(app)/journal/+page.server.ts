import type { PageServerLoad } from './$types';
import { client } from '$lib/server/api';

export const load = (async ({ fetch, cookies }) => {
	const operationId = cookies.get("operation")!;

	return {
		journalData: (await client.GET('/journal', { params: {query: {operationId, sort: ["id,desc"]}}, fetch })).data!
	};

}) satisfies PageServerLoad;

export const actions = {
	journal: async ({ request, fetch, cookies }) => {
		let data = await request.formData();
		const entry = data.get('entry') as string;
		const operationId = cookies.get("operation")!;

		await client.POST('/journal', { body: {operationId, text: entry}, fetch });
	}
};
