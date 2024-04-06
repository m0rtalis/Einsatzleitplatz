import { type RequestHandler } from '@sveltejs/kit';
import { client } from '$lib/server/api';

export const DELETE: RequestHandler = async ({params, fetch}) => {
	const id = params.id!
	console.log(`Delete ${id}`)
	await client.DELETE("/journal/{id}", {params: {path: {id}}, fetch})
	return new Response(null, {status: 204})
}