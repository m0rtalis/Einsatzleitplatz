<script lang="ts">
	import PlusIcon from 'virtual:icons/mdi/plus';
	import ListIcon from 'virtual:icons/mdi/format-list-bulleted';

	interface Props {
		id: string;
		options?: readonly { id: string; name: string }[];
		translation: { addButton: string; listButton: string; newText: string };
		componentName: string;
	}

	let { id, options = [], translation, componentName }: Props = $props();

	let selectedId: string | undefined = $state(options[0]?.id);
	let isSelectFromList = $state(!!options.length);
</script>

<div>
	{#if isSelectFromList}
		<select {id} name={componentName} bind:value={selectedId} required={isSelectFromList}>
			{#each options as option (option.id)}
				<option value={option.id}>{option.name}</option>
			{/each}
		</select>
		<button
			type="button"
			title={translation.addButton}
			class="icon-only"
			onclick={() => (isSelectFromList = false)}
		>
			<PlusIcon />
		</button>
	{:else}
		<input
			{id}
			type="text"
			maxlength="100"
			minlength="3"
			name={componentName}
			placeholder={translation.newText}
			enterkeyhint="done"
			required={isSelectFromList === false}
		/>
		<input type="hidden" name="new" value="true" />
		<button
			type="button"
			title={translation.listButton}
			class="icon-only"
			onclick={() => (isSelectFromList = true)}
		>
			<ListIcon />
		</button>
	{/if}
</div>

<style>
	div {
		display: flex;
	}

	select,
	input {
		min-width: 20em;
	}
</style>
