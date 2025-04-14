import { redirect } from '@sveltejs/kit';
import { AUTH_COOKIE_NAME } from '$lib/server/api';
import { StatusCode } from '$lib/utility/statusCode';

export async function handle({ event, resolve }) {
	let operationId = event.cookies.get('operationId');
	if (
		event.route.id?.startsWith('/(app)') &&
		(!event.cookies.get(AUTH_COOKIE_NAME) || !operationId)
	) {
		redirect(StatusCode.TEMPORARY_REDIRECT, '/login');
	}
	event.locals.operationId = operationId!;
	return resolve(event);
}

export async function handleFetch({ request, fetch }) {
	const response = await fetch(request);
	if (response.status === StatusCode.FORBIDDEN) {
		console.log('Redirect to login', request.url, request.method);
		// TODO: Add current page as param so login can redirect back again
		// TODO: Do not redirect for login request
		redirect(StatusCode.TEMPORARY_REDIRECT, '/login');
	}
	return response;
}

export async function handleError({ status, error, event, message }) {
	if (status === StatusCode.NOT_FOUND) {
		console.log('Error not found');
		redirect(StatusCode.TEMPORARY_REDIRECT, '/');
	}
	console.error(error, event, message);
}
