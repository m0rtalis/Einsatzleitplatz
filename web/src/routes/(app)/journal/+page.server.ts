import type { PageServerLoad } from './$types';
import { client } from '$lib/server/api';

export const load: PageServerLoad = async ({ fetch, locals, depends }) => {
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
};

export const actions = {
	journal: async ({ request, fetch, locals }) => {
		let data = await request.formData();
		const entry = data.get('entry') as string;
		const operationId = locals.operationId;

		await client.POST('/journal', { body: { operationId, text: entry }, fetch });
	},

	journalEdit: async ({ request, fetch }) => {
		let data = await request.formData();
		const entryId = data.get('entryId') as string;
		const entry = data.get('entry') as string;
		console.log('journalEdit', { entry });

		await client.PUT('/journal/{id}', {
			params: { path: { id: entryId } },
			body: { text: entry },
			fetch,
		});
	},
};
