import { json, type RequestHandler } from '@sveltejs/kit';
import { client } from '$lib/server/api';

// Don't like that endpoints are not typed
export const GET: RequestHandler = async ({fetch,params }) => {
	const id = params.id
	if (!id) {
		throw new Error("UserId not defined")
	}

	const user = await client.GET("/users/{id}", {params: {path: {id}}, fetch})
	return json(user.data!);
}