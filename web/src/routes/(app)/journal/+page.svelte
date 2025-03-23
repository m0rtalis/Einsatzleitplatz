<script lang="ts">
	import { getSseStore, setPageName } from '$lib/store';
	import { invalidate } from '$app/navigation';
	import CreateJournal from './CreateJournal.svelte';
	import JournalTable from './JournalTable.svelte';

	setPageName('Journal');
	let { data } = $props();
	const sseStore = getSseStore();

	$effect(() => {
		if ($sseStore?.name === 'CREATE_JOURNAL_ENTRY') {
			invalidate('journal');
		}
	});

	async function editEntry(id: string) {}

	async function deleteEntry(id: string) {
		await fetch(`/journal/${id}`, { method: 'DELETE' });
	}

	function restoreEntry(id: string) {}
</script>

<div class="content">
	<section>
		<CreateJournal />
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
</div>
