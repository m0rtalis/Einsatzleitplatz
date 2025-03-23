<script lang="ts">
	import { enhance } from '$app/forms';
	import type { KeyboardEventHandler } from 'svelte/elements';
	import { afterNavigate } from '$app/navigation';
	import type { SubmitFunction } from '@sveltejs/kit';

	let createEntryForm: HTMLFormElement | undefined = $state();

	const shouldSubmit: KeyboardEventHandler<HTMLTextAreaElement> = ({ ctrlKey, key }) => {
		if (ctrlKey && key === 'Enter') {
			createEntryForm?.requestSubmit();
		}
	};

	afterNavigate(() => {
		const textarea: HTMLTextAreaElement | null = document.querySelector('#journal-entry');
		textarea?.focus();
	});

	const submitJournalEntry: SubmitFunction =
		() =>
		async ({ update }) => {
			await update();
			const textarea: HTMLTextAreaElement | null = document.querySelector('#journal-entry');
			textarea?.focus();
		};
</script>

<form
	bind:this={createEntryForm}
	class="new-journal-form"
	id="create-journal-entry-form"
	name="create-journal-entry-form"
	method="post"
	action="?/journal"
	use:enhance={submitJournalEntry}
>
	<fieldset>
		<legend>Create new journal entry</legend>
		<label for="journal-entry">Event</label>
		<textarea id="journal-entry" name="entry" class="entry-area" onkeydown={shouldSubmit}
		></textarea>
		<button type="submit">Create</button>
	</fieldset>
</form>

<style>
	.new-journal-form {
		padding-right: 15px;
		padding-left: 15px;
	}

	.entry-area {
		height: 20rem;
	}

	.new-journal-form button[type='submit'] {
		width: 100%;
		margin-top: 1rem;
		cursor: pointer;
	}
</style>
