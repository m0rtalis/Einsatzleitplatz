<script lang="ts">
	import { enhance } from '$app/forms';
	import { setPageName } from '$lib/store';
	import Combobox from '$lib/component/Combobox.svelte';
	import type { SubmitFunction } from '@sveltejs/kit';

	setPageName('Journal');

	export let data;

	const submitNewEntry: SubmitFunction = ({submitter}) => { return async ({update}) => {await update();}}
</script>

<div class="content">
	<form class="new-journal-form" id="create-journal-entry-form" name="create-journal-entry-form" method="post" action="?/journal" use:enhance={submitNewEntry}>
		<fieldset>
			<legend>Create new journal entry</legend>
			<label for="journal-type">Type</label>
			<Combobox id="journal-type" options={data.journalTypes.map(t => ({id: t, name: t}))}
					  translation={{listButton: "Show types", addButton: "Add new type", newText: "New Journal Type"}}
					  componentName="type"
			/>
			<label for="journal-entry">Event</label>
			<textarea id="journal-entry" name="entry" class="entry-area" />
			<button type="submit">Create</button>
		</fieldset>
	</form>
	<!-- List journal entries -->
</div>

<style>
    .new-journal-form {
        padding-right: 15px;
        padding-left: 15px;
    }

    .entry-area {
        height: 20rem;
    }

    .new-journal-form button[type=submit] {
        width: 100%;
        margin-top: 1rem;
        cursor: pointer;
    }

</style>
