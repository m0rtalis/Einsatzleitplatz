<script lang="ts">
	import { getSseStore, type SseEvent } from '$lib/state';

	const sseStore = getSseStore();
	const messages: SseEvent[] = $state([]);

	$effect(() => {
		messages.push($sseStore);
	});
</script>

<div>
	<table>
		<thead>
			<tr>
				<td>Id</td>
				<td>Name</td>
				<td>Data</td>
			</tr>
		</thead>
		<tbody>
			{#each messages as m}
				<tr>
					<td>{m?.id}</td>
					<td>{m?.name}</td>
					<td>{m?.data}</td>
				</tr>
			{/each}
		</tbody>
	</table>
</div>
