<script lang="ts">
	import { enhance } from '$app/forms';
	import { setPageName } from '$lib/store';
	import type { SubmitFunction } from '@sveltejs/kit';
	import type { KeyboardEventHandler } from 'svelte/elements';
	// import {Tabulator} from 'tabulator-tables'; Wait for typings to catch up :(

	setPageName('Journal');

	let createEntryForm: HTMLFormElement;
	const submitNewEntry: SubmitFunction = ({ submitter }) => {
		return async ({ update }) => {
			await update();
		};
	};
	const shouldSubmit: KeyboardEventHandler<HTMLTextAreaElement> = ({ ctrlKey, key }) => {
		if (ctrlKey && key === 'Enter') {
			createEntryForm.submit();
		}
	};
</script>

<div class="content">
	<form bind:this={createEntryForm} class="new-journal-form" id="create-journal-entry-form" name="create-journal-entry-form"
		  method="post" action="?/journal" use:enhance={submitNewEntry}>
		<fieldset>
			<legend>Create new journal entry</legend>
			<label for="journal-entry">Event</label>
			<textarea id="journal-entry" name="entry" class="entry-area" on:keydown={shouldSubmit} />
			<button type="submit">Create</button>
		</fieldset>
	</form>
	<!-- List journal entries (Tabulator) -->
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
