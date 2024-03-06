import {client } from '$lib/api';
import type {Schema} from '$lib/api'

export const load = async ({fetch}) => ({
    openOperations: (await client.GET('/operations/names', {fetch})).data!
})

export const actions = {
        login: async ({request, fetch }) => {
            let data = await request.formData();

            console.log('Login called', data)

            // 1. Login user
            let loginResponse = await client.POST('/users/login', {
                body: {
                    username: data.get("username") as string,
                    password: data.get("password") as string
                }, fetch
            });
            const user = loginResponse.data!

            // 2. If "createNewOperation=true": Create new Operation
            let operation: Schema.components["schemas"]["Operation"]
            if (data.get("createNewOperation") as string === "true") {
                operation = (await client.POST("/operations", {
                    body: {name: data.get("operation") as string},
                    fetch
                })).data!
            } else {
                operation = (await client.GET("/operations/{id}", {
                        params: {path: {id: Number(data.get("operation"))}}, fetch
                    }
                )).data!
            }
            // 3. Return operation and user to Page and then set there
            return {user, operation}

            // 4. redirect
            // await goto('..')
        }
    }
;
