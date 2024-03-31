import type { PageServerLoad } from './$types';
import { client } from '$lib/server/api';

export const load = (async ({ fetch, cookies, depends }) => {
	depends("journal")
	const operationId = cookies.get("operationId")!;

	return {
		journalData: (await client.GET('/journal', { params: { query: { operationId, sort: ["id"] } }, fetch })).data!
	};

}) satisfies PageServerLoad;

export const actions = {
	journal: async ({ request, fetch, cookies }) => {
		let data = await request.formData();
		const entry = data.get('entry') as string;
		const operationId = cookies.get("operationId")!;

		await client.POST('/journal', { body: {operationId, text: entry}, fetch });
	}
};
