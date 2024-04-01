<script lang="ts">
	import { enhance } from '$app/forms';
	import { operationStore, setPageName, userStore } from '$lib/store';
	import { afterNavigate, goto } from '$app/navigation';
	import Combobox from '$lib/component/Combobox.svelte';
	import type { SubmitFunction } from '@sveltejs/kit';

	setPageName('Login');

	export let data;
	export let form;

	afterNavigate(() => {
		const usernameInput: HTMLInputElement | null = document.querySelector('#login-username')
		usernameInput?.focus();
	})

	const submitLogin: SubmitFunction = ({ submitter }) => {
		if (submitter && submitter instanceof HTMLButtonElement) {
			submitter.disabled = true;
		}

		return async ({ update }) => {
			if (submitter && submitter instanceof HTMLButtonElement) {
				submitter.disabled = false;
			}
			await update();
		};
	};

	$: if (form?.isLoggedIn) {
		userStore.set({ username: form.user.username });
		operationStore.set({ id: form.operation.id, name: form.operation.name });
		goto('/');
	}
</script>

<div class="content">
	<h1 class="heading">Einsatzleitplatz</h1>
	<form id="login-form" name="login-form" method="post" action="?/login" class="login-form"
		  use:enhance={submitLogin}>
		<label for="login-username">Username</label>
		<input id="login-username" name="username" type="text" placeholder="Username" required
			   autocapitalize="none" />
		<label for="login-password">Password</label>
		<input id="login-password" name="password" type="password" placeholder="Password"
			   required />
		<label for="login-operations">Operation</label>
		<Combobox id="login-operations"
			options={data.openOperations}
				  translation={{addButton: "Add new Operation", listButton: "List operations", newText: "New Operation"}}
				  componentName="operation" />
		<button type="submit">Login</button>
	</form>
</div>

<style>
    .content {
        display: flex;
        flex-direction: column;
        align-items: center;
        flex: 1;
        padding-top: 10%;
        height: 100%
    }

    .heading {
        text-align: center;
    }

    .login-form {
        padding-bottom: 2rem;
    }

    .login-form > * {
        max-width: 100%;
    }

    .login-form > label {
        display: block;
        margin-top: .5rem;
        text-align: left;
        font-weight: bold;
    }

    .login-form > button[type="submit"] {
        width: 100%;
        margin-top: 1rem;
        cursor: pointer;
    }
</style>
