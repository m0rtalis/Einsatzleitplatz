import type { Schema } from '$lib/server/api';
import { client } from '$lib/server/api';
import parseCookie from 'cookie';

const AUTH_COOKIE_NAME = 'JSESSIONID';

export const load = async ({ fetch }) => ({
	openOperations: (await client.GET('/operations/names', { fetch })).data!
});

export const actions = {
		login: async ({ request, fetch, cookies }) => {
			let data = await request.formData();
			const username = data.get("username") as string
			const password = data.get("password") as string
			const operation = data.get("operation") as string
			const isNewOperation = data.get("new") as string === 'true'

			// 1. Login user
			let loginResponse = await client.POST('/users/login', {
				body: {
					username,
					password,
				}, fetch
			});
			const user = loginResponse.data!;

			const setCookies = loginResponse.response.headers.getSetCookie();
			const authCookie = setCookies.map(cookie => parseCookie.parse(cookie)).find(cookie => AUTH_COOKIE_NAME in cookie);
			let authCookieString: string
			if (authCookie) {
				authCookieString = `${AUTH_COOKIE_NAME}=${authCookie[AUTH_COOKIE_NAME]}`;
				cookies.set(AUTH_COOKIE_NAME, authCookie[AUTH_COOKIE_NAME]!, { path: '/', ...authCookie });
			} else {
				authCookieString = cookies.get(AUTH_COOKIE_NAME)!
			}

			// 2. If "createNewOperation=true": Create new Operation
			let operationResponse: Schema.components['schemas']['Operation'];
			if (isNewOperation) {
				operationResponse = (await client.POST('/operations', {
					body: { name: operation },
					headers: { Cookie: authCookieString },
					fetch
				})).data!;
			} else {
				operationResponse = (await client.GET('/operations/{id}', {
						params: { path: { id: operation } }, headers: { Cookie: authCookieString }, fetch
					}
				)).data!;
			}
			cookies.set("operation", operationResponse.id, {path: '/'})
			// 3. Return operation and user to Page and then set there
			//    redirect on client side. I'd rather do it on server side but not possible.
			return { isLoggedIn: true, user, operation: operationResponse };
		}
	}
;
