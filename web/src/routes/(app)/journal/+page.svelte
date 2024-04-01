<script lang="ts">
	import { enhance } from '$app/forms';
	import { setPageName, sseStore } from '$lib/store';
	import type { KeyboardEventHandler } from 'svelte/elements';
	import { afterNavigate, invalidate  } from '$app/navigation';
	import type { JournalEntry } from '$lib/server/api';
	import User from '$lib/component/User.svelte';
	import Time from '$lib/component/Time.svelte';
	// import {Tabulator} from 'tabulator-tables'; Wait for typings to catch up :( ... or create own :) ?

	setPageName('Journal');
	export let data;
	let journalEntries: JournalEntry[] = [];

	$: journalEntries = data.journalData?.data ?? []

	$: if ($sseStore?.name === 'NEW_JOURNAL_ENTRY') {
		invalidate('journal');
	}

	let createEntryForm: HTMLFormElement;

	const shouldSubmit: KeyboardEventHandler<HTMLTextAreaElement> = ({ ctrlKey, key }) => {
		if (ctrlKey && key === 'Enter') {
			createEntryForm.submit();
		}
	};

	afterNavigate(() => {
		const textarea: HTMLTextAreaElement | null = document.querySelector('#journal-entry');
		textarea?.focus();
	});
</script>

<div class="content">
	<form bind:this={createEntryForm} class="new-journal-form" id="create-journal-entry-form"
		  name="create-journal-entry-form"
		  method="post" action="?/journal" use:enhance>
		<fieldset>
			<legend>Create new journal entry</legend>
			<label for="journal-entry">Event</label>
			<textarea id="journal-entry" name="entry" class="entry-area" on:keydown={shouldSubmit} />
			<button type="submit">Create</button>
		</fieldset>
	</form>
	<!-- List journal entries -->
	<table class="journal">
		<caption>Operation Journal</caption>
		<thead>
		<tr>
			<th>Seq. Nr.</th>
			<th>Time</th>
			<th>Component</th>
			<th>Text</th>
			<th>Author</th>
		</tr>
		</thead>
		<tbody>
		{#each journalEntries as entry (entry.id)}
			<tr>
				<td>{entry.journalEntryId}</td>
				<td><Time time={entry.createdAt || "0"} /></td>
				<td>{entry.component}</td>
				<td>{entry.text}</td>
				<td><User id={entry.createdBy || ""} /></td>
			</tr>
		{/each}
		</tbody>
	</table>
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

    .journal {
        margin-top: 2rem;
    }

    .journal caption {
        font-weight: bold;
    }

</style>
