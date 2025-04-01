<script lang="ts">
	import Time from '$lib/component/Time.svelte';
	import EditIcon from 'virtual:icons/mdi/edit-outline';
	import DeleteIcon from 'virtual:icons/mdi/delete-outline';
	import RestoreIcon from 'virtual:icons/mdi/delete-restore';
	import User from '$lib/component/User.svelte';
	import type { JournalEntry } from '$lib/api';

	interface Props {
		// import {Tabulator} from 'tabulator-tables'; Wait for typings to catch up :( ... or create own :) ?
		journalEntries?: readonly JournalEntry[];
		editEntry: (id: string) => void;
		deleteEntry: (id: string) => void;
		restoreEntry: (id: string) => void;
	}

	let { journalEntries = [], editEntry, deleteEntry, restoreEntry }: Props = $props();
</script>

<table class="journal-table">
	<caption>Operation Journal</caption>
	<thead>
		<tr>
			<th>Seq. Nr.</th>
			<th>Time</th>
			<th>Author</th>
			<th>Text</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		{#each journalEntries as entry (entry.id)}
			<tr class:deleted={entry.isDeleted}>
				<td>{entry.journalEntryId}</td>
				<td>
					<Time time={entry.createdAt} />
				</td>
				<td>
					<User id={entry.createdBy} />
				</td>
				<td class="grow">{entry.text}</td>
				<td>
					<div class="button-group">
						{#if !entry.isDeleted}
							<button class="icon-only" onclick={() => editEntry(entry.id)}>
								<EditIcon />
							</button>
							<button class="icon-only" onclick={() => deleteEntry(entry.id)}>
								<DeleteIcon />
							</button>
						{:else}
							<!--
						This causes the table to jump if all entries are deleted
						 as we have 2 icons if not deleted and 1 icon if deleted.
						We could add a hidden icon here or calculate a padding or sth. to prevent this.
						For now it doesn't seem too important.
						 -->
							<button
								class="icon-only"
								style:float="right"
								onclick={() => restoreEntry(entry.id)}
							>
								<RestoreIcon />
							</button>
						{/if}
					</div>
				</td>
			</tr>
		{/each}
	</tbody>
</table>

<style>
	table {
		margin-top: 2rem;
		width: 100%;
		max-width: 100%;
	}

	table caption {
		font-weight: bold;
	}

	table th {
		text-align: center;
	}

	table tr {
		white-space: nowrap;
	}

	table tr:hover {
		background-color: var(--color-grey);
	}

	table th:not(:last-child),
	td:not(:last-child) {
		padding-right: 2rem;
	}

	table td {
		position: relative;
	}

	table td.grow {
		width: 100%;
		/* No idea why this max-width works. I should probably look into it.. */
		max-width: 1vw;
		overflow: auto;
		scrollbar-width: thin;
	}

	table tr.deleted td.grow {
		overflow: hidden;
	}

	table tr.deleted td:not(:last-child):before {
		content: ' ';
		position: absolute;
		left: 0;
		top: 50%;
		width: 100%;
		border-bottom: 2px solid var(--color-darkGrey);
	}

	.button-group {
		float: left;
		width: 100%;
	}

	.button-group button:not(:last-child) {
		margin-right: 0.5rem;
	}

	.button-group button {
		padding: 1rem;
		background-color: transparent;
	}

	.button-group button:hover {
		background-color: var(--color-lightGrey);
	}
</style>
