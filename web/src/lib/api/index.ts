import createClient from 'openapi-fetch';
import type { paths } from './elp';

// We are glad if we don't need it
// noinspection JSUnusedGlobalSymbols
export const debugFetch = (fetch_: typeof fetch): typeof fetch =>
	async (input: RequestInfo | URL, init?: RequestInit | undefined): Promise<Response> => {
		console.log("Fetch: ", input)
		const s = await fetch_(input, init);
		console.log("Fetch Result: ", s)
		return s
	}


export const client = createClient<paths>({ baseUrl: 'http://127.0.0.1:8080/' });
