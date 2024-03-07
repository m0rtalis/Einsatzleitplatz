import type { Schema } from '$lib/api';
import { client } from '$lib/api';
import parseCookie from 'cookie';

export const load = async ({ fetch }) => ({
	openOperations: (await client.GET('/operations/names', { fetch })).data!
});

const AUTH_COOKIE_NAME = 'JSESSIONID';

export const actions = {
		login: async ({ request, fetch, cookies }) => {
			let data = await request.formData();

			// 1. Login user
			let loginResponse = await client.POST('/users/login', {
				body: {
					username: data.get('username') as string,
					password: data.get('password') as string
				}, fetch
			});
			const user = loginResponse.data!;

			const setCookies = loginResponse.response.headers.getSetCookie();
			const authCookie = setCookies.map(cookie => parseCookie.parse(cookie)).find(cookie => AUTH_COOKIE_NAME in cookie)!;
			const authCookieString = `${AUTH_COOKIE_NAME}=${authCookie[AUTH_COOKIE_NAME]}`;
			cookies.set(AUTH_COOKIE_NAME, authCookie[AUTH_COOKIE_NAME]!, { path: '/', ...authCookie });

			// 2. If "createNewOperation=true": Create new Operation
			let operation: Schema.components['schemas']['Operation'];
			if (data.get('createNewOperation') as string === 'true') {
				operation = (await client.POST('/operations', {
					body: { name: data.get('operation') as string },
					headers: { Cookie: authCookieString },
					fetch
				})).data!;
			} else {
				operation = (await client.GET('/operations/{id}', {
						params: { path: { id: Number(data.get('operation')) } }, headers: { Cookie: authCookieString }, fetch
					}
				)).data!;
			}
			// 3. Return operation and user to Page and then set there
			//    redirect on client side. I'd rather do it on server side but not possible.
			return { isLoggedIn: true, user, operation };
		}
	}
;
