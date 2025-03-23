import type { PageServerLoad } from './$types';
import { client } from '$lib/server/api';

export const load = (async ({ fetch, locals, depends }) => {
	depends('journal');
	const operationId = locals.operationId;

	return {
		journalData: (
			await client.GET('/journal', {
				params: { query: { operationId, sort: ['id'] } },
				fetch,
			})
		).data!,
	};
}) satisfies PageServerLoad;

export const actions = {
	journal: async ({ request, fetch, locals }) => {
		let data = await request.formData();
		const entry = data.get('entry') as string;
		const operationId = locals.operationId;

		await client.POST('/journal', { body: { operationId, text: entry }, fetch });
	},
};
