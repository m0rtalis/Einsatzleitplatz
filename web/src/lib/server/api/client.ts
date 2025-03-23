import createClient from 'openapi-fetch';
import { type components, EventName, type paths } from '../../api/elp';
import type { Cookies } from '@sveltejs/kit';

export const SERVER_URL = 'http://localhost:8080';
export const AUTH_COOKIE = 'JSESSIONID';

export const getAuthCookieStr = (cookies: Cookies): string =>
	`${AUTH_COOKIE}=${cookies.get(AUTH_COOKIE)}`;

export const client = createClient<paths>({ baseUrl: SERVER_URL });
