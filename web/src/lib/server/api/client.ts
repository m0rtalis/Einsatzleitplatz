import createClient from 'openapi-fetch';
import { type Schema } from '$lib/api';
import type { Cookies } from '@sveltejs/kit';

export const SERVER_URL = 'http://localhost:8080';
export const AUTH_COOKIE_NAME = 'JSESSIONID';

export const getAuthCookieStr = (cookies: Cookies): string =>
	`${AUTH_COOKIE_NAME}=${cookies.get(AUTH_COOKIE_NAME)}`;

export const client = createClient<Schema.paths>({ baseUrl: SERVER_URL });
