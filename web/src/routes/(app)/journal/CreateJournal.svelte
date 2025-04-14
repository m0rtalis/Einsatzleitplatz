<script lang="ts">
	import { enhance } from '$app/forms';
	import type { KeyboardEventHandler } from 'svelte/elements';
	import { afterNavigate } from '$app/navigation';
	import type { SubmitFunction } from '@sveltejs/kit';
	import type { JournalEntry } from '$lib/api';

	let {
		editEntry,
	}: {
		editEntry?: JournalEntry;
	} = $props();

	let journalEntryForm: HTMLFormElement | undefined = $state();
	const textarea = $derived(journalEntryForm?.querySelector('textarea') ?? undefined);

	const submitOnCtrlEnter: KeyboardEventHandler<HTMLTextAreaElement> = ({ ctrlKey, key }) => {
		if (ctrlKey && key === 'Enter') {
			journalEntryForm?.requestSubmit();
		}
	};

	const submitJournalEntry: SubmitFunction =
		() =>
		async ({ update }) => {
			await update();
			editEntry = undefined;
			textarea?.focus();
		};

	afterNavigate(() => {
		textarea?.focus();
	});

	$effect(() => {
		editEntry;
		textarea?.focus();
	});
</script>

<!--
I don't think this is the best way to handle the two states "Create" and "Edit",
but until I can come up with a better way I'll leave it like this.
-->
{#if !editEntry}
	<form
		bind:this={journalEntryForm}
		name="journal-entry-form"
		method="post"
		action="?/createJournalEntry"
		use:enhance={submitJournalEntry}
	>
		<fieldset>
			<legend>Create new journal entry</legend>
			<label for="journal-entry">Event</label>
			<textarea id="journal-entry" name="entry" onkeydown={submitOnCtrlEnter}></textarea>
			<div class="btn-group">
				<!-- TODO: Disable is textarea.content is blank -->
				<button type="submit">Create</button>
			</div>
		</fieldset>
	</form>
{:else}
	<form
		bind:this={journalEntryForm}
		name="journal-entry-form"
		method="post"
		action="?/editJournalEntry"
		use:enhance={submitJournalEntry}
	>
		<input name="entryId" type="hidden" readonly value={editEntry.id} />
		<fieldset>
			<legend>Edit journal entry</legend>
			<label for="journal-entry">Event</label>
			<textarea id="journal-entry" name="entry" onkeydown={submitOnCtrlEnter}
				>{editEntry.text}</textarea
			>
			<div class="btn-group">
				<button type="submit" disabled={!journalEntryForm?.textContent?.trim()}>Save</button
				>
				<button type="reset" onclick={() => (editEntry = undefined)}>Cancel</button>
			</div>
		</fieldset>
	</form>
{/if}

<style>
	form {
		padding-right: 15px;
		padding-left: 15px;
	}

	form textarea {
		height: 20rem;
	}

	.btn-group {
		display: flex;
		width: 100%;
		gap: 1rem;
		margin-top: 1rem;
	}

	form .btn-group button[type='submit'] {
		flex: 1;
	}
</style>
