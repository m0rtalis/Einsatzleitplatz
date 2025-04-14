import type { PageServerLoad } from './$types';
import { client } from '$lib/server/api';

export const load: PageServerLoad = async ({ fetch, locals, depends, url }) => {
	// Note: This fires twice when creating a new journal entry https://svelte.dev/docs/kit/form-actions#Loading-data
	depends('journal');
	const operationId = locals.operationId;
	const page = Number(url.searchParams.get('page') || 1);
	const size = Number(url.searchParams.get('size') || 10);
	const offset = (page - 1) * size;

	console.log(url.searchParams, { page, size, offset });
	const response = await client.GET('/journal', {
		params: { query: { operationId, offset, limit: size, sort: ['id'] } },
		fetch,
	});
	console.log(response.data!.pagination)
	return {
		journalData: response.data!,
	};
};

export const actions = {
	createJournalEntry: async ({ request, fetch, locals }) => {
		let data = await request.formData();
		const entry = data.get('entry') as string;
		const operationId = locals.operationId;

		await client.POST('/journal', { body: { operationId, text: entry }, fetch });
	},

	editJournalEntry: async ({ request, fetch }) => {
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
