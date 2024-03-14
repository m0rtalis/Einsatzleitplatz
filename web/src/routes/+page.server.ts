import {client} from "../lib/server/api";

export const load = async ({fetch }) => ({
       username: (await client.GET('/users/me', {fetch, credentials: 'same-origin'})).data!
})
