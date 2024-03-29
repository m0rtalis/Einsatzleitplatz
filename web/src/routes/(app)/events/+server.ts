import { type RequestHandler } from '@sveltejs/kit';
import EventSource from 'eventsource';

export const GET: RequestHandler = ({ cookies }) => {
	// Alternatively use https://github.com/tncrazvan/sveltekit-sse but this is closer to vanilla
	// We could have only one sse connection per user to the server and duplicate here. But let's make it work first.
	const authCookieStr = `JSESSIONID=${cookies.get('JSESSIONID')}`;
	let eventSource: EventSource | undefined = undefined
	const stream = new ReadableStream({
		start(controller) {
			eventSource = new EventSource('http://localhost:8080/sse', { headers: { Cookie: authCookieStr } });
			eventSource.onmessage = evt => {
				controller.enqueue(JSON.stringify(evt))
			}
			eventSource.onerror = evt => {
				console.log("Error", evt)
				eventSource?.close()
				controller.close()
			}
		},
		cancel(reason) {
			console.log(reason, "Canceled")
			eventSource?.close()
		}
	});
	return new Response(stream, {headers: {'Content-Type': 'text/event-stream', 'Cache-Control': 'no-cache'}});
};
