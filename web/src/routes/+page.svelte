<script lang="ts">
	import 'chota';
	import { enhance } from '$app/forms';
	import IconPlus from 'virtual:icons/mdi/plus';
	import IconList from 'virtual:icons/mdi/format-list-bulleted';

	export let data;
	let selectedOperationId = data.openOperations[0]?.id;
	let isSelectFromList: boolean = !!data.openOperations.length;
</script>

<div class="center">
	<h1 class="heading">Einsatzleitplatz</h1>
	<form id="login-form" name="login-form" method="post" action="?/login" class="login-form"
		  use:enhance>
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
				<button type="button" title="Add new operation" class="icon-only" on:click={() => isSelectFromList = false}>
					<IconPlus />
				</button>
			{:else}
				<input type="text" maxlength="100" minlength="3" name="Operation" placeholder="New Operation" enterkeyhint="done"
					   required={isSelectFromList === false}>
				<input type="hidden" name="createNewOperation" value=true >
				<button type="button" title="List operations" class="icon-only" on:click={() => isSelectFromList = true}>
					<IconList />
				</button>
			{/if}
		</div>
		<button type="submit">Login</button>
	</form>
</div>

<style>
    .center {
        position: fixed;
        top: 30%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .heading {
        text-align: center;
    }

    .login-form {
        align-items: center;
    }

    .login-form > label {
		display: inline-block;
		margin-top: .5rem;
        text-align: left;
        font-weight: bold;
    }

    .login-form > button[type="submit"] {
		width: 100%;
        margin-top: 1rem;
    }

    .select-div {
        display: flex;
    }

    .select-div > select, .select-div > input {
        min-width: 25rem;
    }

</style>
