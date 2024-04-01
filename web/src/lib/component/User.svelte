<script lang="ts">
	import { onMount } from 'svelte';
	import type { User } from '$lib/server/api';

	export let id: string;

	let user: User | undefined;
	onMount(async () => {
		// Not sure about this pattern yet. Let's see how it works out
		user = await (await fetch(`/api/users/${id}`)).json();
	});
</script>

<span>{user?.username}</span>

<style>

</style>
