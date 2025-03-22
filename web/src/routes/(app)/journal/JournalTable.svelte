<script lang="ts">
	import Time from '$lib/component/Time.svelte';
	import EditIcon from 'virtual:icons/mdi/edit-outline';
	import DeleteIcon from 'virtual:icons/mdi/delete-outline';
	import RestoreIcon from 'virtual:icons/mdi/delete-restore';
	import User from '$lib/component/User.svelte';
	import type { JournalEntry } from '$lib/server/api';
	// import {Tabulator} from 'tabulator-tables'; Wait for typings to catch up :( ... or create own :) ?

	export let journalEntries: readonly JournalEntry[] = [];
	export let editEntry: (id: string) => void;
	export let deleteEntry: (id: string) => void;
	export let restoreEntry: (id: string) => void;
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
				<Time time={entry.createdAt || "0"} />
			</td>
			<td>
				<User id={entry.createdBy || ""} />
			</td>
			<td class="grow">{entry.text}</td>
			<td>
				<div class="button-group">
					{#if !entry.isDeleted}
						<button class="icon-only" on:click={() => editEntry(entry.id)}>
							<EditIcon />
						</button>
						<button class="icon-only" on:click={() => deleteEntry(entry.id)}>
							<DeleteIcon />
						</button>
					{:else }
						<button class="icon-only" on:click={() => restoreEntry(entry.id)}>
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
        width: 100%
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
        background-color: var(--color-grey)
    }

    table th:not(:last-child), td:not(:last-child) {
        padding-right: 2rem;
    }

    table td {
        position: relative;
    }

    table td.grow {
        width: 100%;
    }

    table tr.deleted td:before {
        content: " ";
        position: absolute;
        left: 0;
        top: 50%;
        width: 100%;
        border-bottom: 1px solid var(--color-darkGrey);

    }

    .button-group {
        float: left;
    }

    .button-group button:not(:last-child) {
        margin-right: .5rem;
    }

    .button-group button {
        padding: 1rem;
        background-color: transparent;
    }

    .button-group button:hover {
        background-color: var(--color-lightGrey);
    }
</style>
