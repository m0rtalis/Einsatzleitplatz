<script lang="ts" module>
	import { local } from '$lib/state/persisted';
	import type { User } from '$lib/api';
	import { Map as IMap } from 'immutable';

	const usersStore = local<IMap<string, User>>('Users', IMap<string, User>(), {
		deserializer: (value) => IMap<string, User>(JSON.parse(value))
	});
</script>

<script lang="ts">
	import { onMount } from 'svelte';

	interface Props {
		id: string;
	}

	let { id }: Props = $props();

	let user = $derived($usersStore?.get(id));

	onMount(async () => {
		// Not sure about this pattern yet. Let's see how it works out
		// Don't like the extra api endpoint. Would be better if I can define a server only function in this component.
		// Definitively needs caching
		if (!$usersStore.has(id)) {
			const downloadedUser = (await (await fetch(`/api/users/${id}`)).json()) as User;
			usersStore.set($usersStore.set(id, downloadedUser));
		}
	});
</script>

<span>{user?.username}</span>
