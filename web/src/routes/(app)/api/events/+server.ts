import { type RequestHandler } from '@sveltejs/kit';
import EventSource from 'eventsource';
import { client, getAuthCookieStr, SERVER_URL } from '$lib/server/api';

const sseToString = (message: MessageEvent): string => {
	let s = '';
	s += message.lastEventId ? `id: ${message.lastEventId}\n` : '';
	s += message.type ? `event: ${message.type}\n` : '';
	s += message.data ? `data: ${message.data}\n` : '';
	return s + '\n';
};

export const GET: RequestHandler = async ({ fetch, cookies }) => {
	// Alternatively use https://github.com/tncrazvan/sveltekit-sse but this is closer to vanilla
	// We could have only one sse connection per user to the server and duplicate here. But let's make it work first.
	const eventNames = await client.GET('/sse/names', { fetch });

	const authCookieStr = getAuthCookieStr(cookies);
	let eventSource: EventSource | undefined = undefined;

	const stream = new ReadableStream({
		start(controller) {
			eventSource = new EventSource(`${SERVER_URL}/sse`, {
				headers: { Cookie: authCookieStr },
			});
			eventNames.data!.forEach((name) =>
				eventSource?.addEventListener(name, (evt) => {
					controller.enqueue(sseToString(evt));
				}),
			);
			eventSource.onerror = (evt) => {
				console.log('Error', evt);
				eventSource?.close();
				controller.close();
			};
		},
		cancel(reason) {
			console.log(reason, 'Canceled');
			eventSource?.close();
		},
	});
	return new Response(stream, {
		headers: { 'Content-Type': 'text/event-stream', 'Cache-Control': 'no-cache' },
	});
};
