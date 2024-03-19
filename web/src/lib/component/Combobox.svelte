<script lang="ts">
	import PlusIcon from 'virtual:icons/mdi/plus';
	import ListIcon from 'virtual:icons/mdi/format-list-bulleted';

	export let id: string;
	export let options: { id: string, name: string }[] = [];
	export let translation: { addButton: string, listButton: string, newText: string };
	export let componentName: string;

	let selectedId: string | undefined = options[0]?.id;
	let isSelectFromList = !!options.length;
</script>

<div>
	{#if isSelectFromList}
		<select
			{id}
			name={componentName}
			bind:value={selectedId}
			required={isSelectFromList}
		>
			{#each options as option}
				<option value={option.id}>{option.name}</option>
			{/each}
		</select>
		<button type="button" title={translation.addButton} class="icon-only"
				on:click={() => isSelectFromList = false}>
			<PlusIcon />
		</button>
	{:else}
		<input {id}
			   type="text" maxlength="100" minlength="3" name={componentName} placeholder={translation.newText}
			   enterkeyhint="done"
			   required={isSelectFromList === false}>
		<input type="hidden" name="new" value=true>
		<button type="button" title={translation.listButton} class="icon-only"
				on:click={() => isSelectFromList = true}>
			<ListIcon />
		</button>
	{/if}
</div>

<style>
    div {
        display: flex;
    }

    select, input {
        min-width: 20em;
    }
</style>
