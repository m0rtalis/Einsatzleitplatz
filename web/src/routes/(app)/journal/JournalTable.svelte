<script lang="ts">
	import Time from '$lib/component/Time.svelte';
	import EditIcon from 'virtual:icons/mdi/edit-outline';
	import DeleteIcon from 'virtual:icons/mdi/delete-outline';
	import RestoreIcon from 'virtual:icons/mdi/delete-restore';
	import User from '$lib/component/User.svelte';
	import type { JournalEntry } from '$lib/api';

	//  svelte-headless-table: Is awesome but unfortunately not maintained anymore.
	//  @mediakular/gridcraft: Next best thing, currently working on upgrade to svelte-5.
	//  @tanstack/svelte-table: The standard for now it seems. I don't really like it..
	//  ooooorr, and hear me out here, we do it ourself because I like to suffer or sth :)
	//  anyway.. for now let's manually build this out as much as possible just for the fun of it.
	//  orient on gridcraft and maybe a bit on headless-table

	export interface PagingSettings {
		currentPage: number;
		elementsPerPage: number;
		elementsPerPageOptions?: number[];
	}

	export interface Pagination {
		totalElements: number;
		totalPages: number;
	}

	type SortingDirection = 'asc' | 'desc';

	export interface Sorting {
		key: string;
		direction: SortingDirection;
	}

	interface Props {
		data: readonly JournalEntry[];
		paginationSettings: PagingSettings;
		pagination: Pagination;
		sorting?: Sorting[];
		editEntry: (id: string) => void;
		deleteEntry: (id: string) => void;
		restoreEntry: (id: string) => void;
	}

	let {
		data = [],
		paginationSettings = $bindable(),
		pagination,
		sorting = $bindable([] as Sorting[]),
		editEntry,
		deleteEntry,
		restoreEntry,
	}: Props = $props();


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
		{#each data as row (row.id)}
			<tr class:deleted={row.isDeleted}>
				<td>{row.journalEntryId}</td>
				<td>
					<Time time={row.createdAt} />
				</td>
				<td>
					<User id={row.createdBy} />
				</td>
				<td class="grow">{row.text}</td>
				<td>
					<div class="button-group">
						{#if row.isDeleted}
							<!--
This causes the table to jump if all entries are deleted
as we have 2 icons if not deleted and 1 icon if deleted.
We could add a hidden icon here or calculate a padding or sth. to prevent this.
For now it doesn't seem too important.
-->
							<button
								class="icon-only"
								style:float="right"
								onclick={() => restoreEntry(row.id)}
							>
								<RestoreIcon />
							</button>
						{:else}
							<button class="icon-only" onclick={() => editEntry(row.id)}>
								<EditIcon />
							</button>
							<button class="icon-only" onclick={() => deleteEntry(row.id)}>
								<DeleteIcon />
							</button>
						{/if}
					</div>
				</td>
			</tr>
		{/each}
	</tbody>
</table>

<div class="table-navigation">
	<span
		><label for="select-elements-per-page">Rows per page: </label><select
			id="select-elements-per-page"
			name="select-elements-per-page"
			bind:value={paginationSettings.elementsPerPage}
		>
			{#each paginationSettings.elementsPerPageOptions || [5,10,15] as e}
				<option value={e}>{e}</option>
			{/each}
		</select>
	</span>
	<span>Page {paginationSettings.currentPage} of {pagination.totalPages}</span>
	<span
		><button
			disabled={paginationSettings.currentPage === 1}
			onclick={() => (paginationSettings.currentPage -= 1)}>Previous</button
		>
		<button
			disabled={paginationSettings.currentPage === pagination.totalPages}
			onclick={() => (paginationSettings.currentPage += 1)}>Next</button
		></span
	>
</div>

<style>
	table {
		margin-top: 2rem;
		width: 100%;
		max-width: 100%;

		caption {
			font-weight: bold;
		}

		th {
			text-align: center;
		}

		tr {
			white-space: nowrap;
		}

		tr:hover {
			background-color: var(--color-grey);
		}

		tr.deleted {
			td.grow {
				overflow: hidden;
			}

			td:not(:last-child):before {
				content: ' ';
				position: absolute;
				left: 0;
				top: 50%;
				width: 100%;
				border-bottom: 2px solid var(--color-darkGrey);
			}
		}

		td {
			position: relative;
		}

		td.grow {
			width: 100%;
			/* No idea why this max-width works. I should probably look into it.. */
			max-width: 1vw;
			overflow: auto;
			scrollbar-width: thin;
		}

		th:not(:last-child),
		td:not(:last-child) {
			padding-right: 2rem;
		}
	}

	.button-group {
		float: left;
		width: 100%;

		button {
			padding: 1rem;
			background-color: transparent;
		}

		button:hover {
			background-color: var(--color-lightGrey);
		}

		button:not(:last-child) {
			margin-right: 0.5rem;
		}
	}

	.table-navigation {
		display: flex;
		flex-flow: row nowrap;
		justify-content: space-between;
		align-items: center;
		background-color: var(--color-grey);
		padding: 1rem;
		margin: 1rem 0;
		white-space: nowrap;

		#select-elements-per-page {
			display: inline-block;
			margin-left: 1rem;
			/*	TODO: Make a select component like this https://developer.mozilla.org/en-US/docs/Learn_web_development/Extensions/Forms/Customizable_select */
		}
	}
</style>
