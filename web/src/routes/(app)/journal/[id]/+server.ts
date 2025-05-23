import { type RequestHandler } from '@sveltejs/kit';
import { client } from '$lib/server/api';
import { StatusCode } from '$lib/utility/statusCode';

export const DELETE: RequestHandler = async ({ params, fetch }) => {
	const id = params.id!;
	await client.DELETE('/journal/{id}', { params: { path: { id } }, fetch });
	return new Response(null, { status: StatusCode.NO_CONTENT });
};

export const PATCH: RequestHandler = async ({ params, fetch }) => {
	const id = params.id!;
	await client.PATCH('/journal/{id}', {
		params: { path: { id } },
		body: { isDeleted: false },
		fetch,
	});
	return new Response(null, { status: StatusCode.NO_CONTENT });
};
