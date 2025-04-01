<script lang="ts">
	import { getSseStore, setPageName } from '$lib/state';
	import { invalidate } from '$app/navigation';
	import CreateJournal from './CreateJournal.svelte';
	import JournalTable from './JournalTable.svelte';
	import type { JournalEntry } from '$lib/api';

	setPageName('Journal');
	let { data } = $props();
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

	async function editEntry(id: string) {
		entryForEdit = data.journalData?.data.find((e: JournalEntry) => e.id === id);
	}

	async function deleteEntry(id: string) {
		await fetch(`/journal/${id}`, { method: 'DELETE' });
	}

	async function restoreEntry(id: string) {
		// I could send a body with isDeleted false but since PATCH isn't used for anything else I don't have to.
		await fetch(`/journal/${id}`, { method: 'PATCH' });
	}
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
		journalEntries={data.journalData?.data ?? []}
		{editEntry}
		{deleteEntry}
		{restoreEntry}
	/>
</section>
