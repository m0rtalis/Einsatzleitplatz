import { redirect } from '@sveltejs/kit';
import { AUTH_COOKIE } from '$lib/server/api';

export async function handle({event, resolve}) {
    let operationId = event.cookies.get("operationId");
    if (event.route.id?.startsWith("/(app)") && (!event.cookies.get(AUTH_COOKIE) || !operationId)) {
        redirect(307, "/login")
    }
    event.locals.operationId = operationId!;
    return resolve(event)
}

export async function handleFetch({request, fetch  }) {
    const response = await fetch(request)
    if (response.status === 403) {
        console.log("Redirect to login")
        // TODO: Add current page as param so login can redirect back again
        // TODO: Do not redirect for login request
        redirect(307, '/login')
    }
    return response;
}

export async function handleError({status, error, event, message}) {
    if (status === 404) {
        console.log("Error not found")
        redirect(307, '/')
    }
    console.error(error, event, message)
}
