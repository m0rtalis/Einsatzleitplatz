<script lang="ts">
	import { getSseStore, setPageName } from '$lib/state';
	import { goto, invalidate } from '$app/navigation';
	import CreateJournal from './CreateJournal.svelte';
	import type { JournalEntry } from '$lib/api';
	import JournalTable, { type Pagination, type Sorting } from './JournalTable.svelte';
	import type { PageProps } from './$types';
	import { page } from '$app/state';

	setPageName('Journal');
	let { data }: PageProps = $props();
	const sseStore = getSseStore();
	let entryForEdit: JournalEntry | undefined = $state();

	$effect(() => {
		const eventName = $sseStore?.name;
		if (eventName === 'CHANGED_JOURNAL_ENTRY') {
			// TODO: Check if changed journal entry id is displayed and only invalidate then
			// 	Technically we could also invalidate on edit/delete/restoreEntry but this would cause two fetches
			//  unless we make sure to double check the event message
			invalidate('journal');
		}
	});

	let paginationSettings = $state({
		currentPage: Number(page.url.searchParams.get('page') || 1),
		elementsPerPage: Number(page.url.searchParams.get('size') || 5),
	});

	let pagination: Pagination = $derived({
		totalPages: data.journalData.pagination.totalPages,
		totalElements: data.journalData.pagination.totalElements,
	});

	let sorting: Sorting[] = $state([]);

	$effect(() => {
		const params = new URLSearchParams(page.url.searchParams);
		if (
			params.get('page') === paginationSettings.currentPage.toString() &&
			params.get('size') === paginationSettings.elementsPerPage.toString()
		) {
			// Seems stupid. Svelte should be able to recognize that the search params haven't changed
			// and then 1. Don't run the effect at all and 2. Make goto a noop
			return;
		}
		params.set('page', paginationSettings.currentPage.toString());
		params.set('size', paginationSettings.elementsPerPage.toString());
		goto(`?${params}`, { replaceState: false, noScroll: true, keepFocus: true });
	});

	const editEntry = (id: string) =>
		(entryForEdit = data.journalData?.data.find((e: JournalEntry) => e.id === id));

	const deleteEntry = async (id: string) => fetch(`/journal/${id}`, { method: 'DELETE' });

	// I could send a body with isDeleted false but since PATCH isn't used for anything else I don't have to.
	const restoreEntry = async (id: string) => fetch(`/journal/${id}`, { method: 'PATCH' });
</script>

<section>
	<!--
	I think editEntry works as the prop is updated ephemeral inside of CreateJournal
	 to be undefined again after cancel or save.
	 Maybe the more robust (cleaner) method is to update entryForEdit from inside CreateJournal if it is canceled.
	  -->
	<CreateJournal editEntry={entryForEdit} />
</section>
<!-- List journal entries -->
<section>
	<JournalTable
		data={data.journalData?.data}
		{pagination}
		bind:paginationSettings
		bind:sorting
		{editEntry}
		{deleteEntry}
		{restoreEntry}
	/>
</section>
