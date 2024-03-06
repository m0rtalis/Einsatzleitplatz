import {client} from "$lib/api";

export const load = async ({fetch }) => ({
       username: (await client.GET('/users/me', {fetch, credentials: 'same-origin'})).data!
})
