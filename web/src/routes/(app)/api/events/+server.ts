import { type RequestHandler } from '@sveltejs/kit';
import { EventSource } from 'eventsource';
import { client, getAuthCookieStr, SERVER_URL } from '$lib/server/api';
import { produce } from 'sveltekit-sse';

export const GET: RequestHandler = async ({ fetch, cookies }) => {
	// Alternatively use https://github.com/tncrazvan/sveltekit-sse but this is closer to vanilla
	// We could have only one sse connection per user to the server and duplicate here. But let's make it work first.
	const eventNames = await client.GET('/sse/names', { fetch });

	const authCookieStr = getAuthCookieStr(cookies);

	const eventSource = new EventSource(`${SERVER_URL}/sse`, {
		fetch: (input, init) =>
			fetch(input, {
				...init,
				headers: { ...init?.headers, Cookie: authCookieStr },
			}),
	});

	return produce(
		({ emit }) => {
			eventNames.data!.forEach((name) =>
				eventSource?.addEventListener(name, (evt) => {
					// FIX: This is throwing error if client closes stream
					emit(evt.type, evt.data);
				}),
			);
		},
		{
			stop() {
				console.log('Connection stopped');
				eventSource?.close();
			},
		},
	);
};
