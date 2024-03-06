import {redirect} from "@sveltejs/kit";

export async function handleFetch({request, fetch}) {
    const result = await fetch(request)
    if (result.status === 403) {
        console.log("Redirect to login")
        // TODO: Add current page as param so login can redirect back again
        // TODO: Do not redirect for login request
        redirect(307, '/login')
    }
    return result;
}
