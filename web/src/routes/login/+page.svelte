<script lang="ts">
	import { enhance } from '$app/forms';
	import PlusIcon from 'virtual:icons/mdi/plus';
	import ListIcon from 'virtual:icons/mdi/format-list-bulleted';
	import { operationStore, setPageName, userStore } from '$lib/store';
	import { goto } from '$app/navigation';
	import type { SubmitFunction } from '@sveltejs/kit';

	setPageName('Login')

	export let data;
	export let form;

	let selectedOperationId = data.openOperations[0]?.id;
	let isSelectFromList: boolean = !!data.openOperations.length;

	$: if (form?.isLoggedIn) {
		userStore.set({ username: form.user.username });
		operationStore.set({ id: form.operation.id, name: form.operation.name });
		goto('/');
	}

	const submitLogin:SubmitFunction = ({submitter}) => {
		if (submitter && submitter instanceof HTMLButtonElement) {
			submitter.disabled = true;
		}

		return async ({update}) => {
			if (submitter && submitter instanceof HTMLButtonElement) {
				submitter.disabled = false;
			}
			await update()
		}
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
		<div class="select-div">
			{#if isSelectFromList}
				<select
					id="login-operations"
					name="operation"
					bind:value={selectedOperationId}
					required={isSelectFromList}
				>
					{#each data.openOperations as openOperation}
						<option value={openOperation.id}>{openOperation.name}</option>
					{/each}
				</select>
				<button type="button" title="Add new operation" class="icon-only"
						on:click={() => isSelectFromList = false}>
					<PlusIcon />
				</button>
			{:else}
				<input type="text" maxlength="100" minlength="3" name="operation" placeholder="New Operation"
					   enterkeyhint="done"
					   required={isSelectFromList === false}>
				<input type="hidden" name="createNewOperation" value=true>
				<button type="button" title="List operations" class="icon-only"
						on:click={() => isSelectFromList = true}>
					<ListIcon />
				</button>
			{/if}
		</div>
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

    .select-div {
        display: flex;
    }

    .select-div > select, .select-div > input {
        width: 20em;
    }

</style>
