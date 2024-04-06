<script lang="ts">
	import { getSseStore, setPageName } from '$lib/store';
	import { invalidate } from '$app/navigation';
	import CreateJournal from './CreateJournal.svelte';
	import JournalTable from './JournalTable.svelte';

	setPageName('Journal');
	export let data;
	const sseStore = getSseStore();

	$: if ($sseStore?.name === 'NEW_JOURNAL_ENTRY') {
		invalidate('journal');
	}

	function editEntry(id: string) {

	}

	async function deleteEntry(id: string) {
		console.log("Delete entry")
		await fetch(`/journal/${id}`, { method: 'DELETE' });
	}

	function restoreEntry(id: string) {
	}

</script>

<div class="content">
	<section>
		<CreateJournal />
	</section>
	<!-- List journal entries -->
	<section>
		<JournalTable journalEntries={data.journalData?.data ?? []} {editEntry} {deleteEntry} {restoreEntry} />
	</section>
</div>
