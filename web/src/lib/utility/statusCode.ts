export enum StatusCode {
	// 1xx
	// 2xx
	OK = 200,
	NO_CONTENT = 204,
	// 3xx
	// TODO: Use 303 https://svelte.dev/tutorial/kit/redirects
	SEE_OTHER = 303,
	TEMPORARY_REDIRECT = 307,
	// 4xx
	FORBIDDEN = 403,
	NOT_FOUND = 404,
}
