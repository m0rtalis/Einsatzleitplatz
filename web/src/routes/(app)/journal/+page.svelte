<script lang="ts">
	import { getSseStore, setPageName } from '$lib/store';
	import { invalidate } from '$app/navigation';
	import CreateJournal from './CreateJournal.svelte';
	import JournalTable from './JournalTable.svelte';

	setPageName('Journal');
	let { data } = $props();
	const sseStore = getSseStore();

	$effect(() => {
		const event = $sseStore?.name
		if (event && ['CREATE_JOURNAL_ENTRY', 'UPDATE_JOURNAL_ENTRY', 'DELETE_JOURNAL_ENTRY'].includes(event)) {
			invalidate("journal");
		}
	});

	async function editEntry(id: string) {}

	async function deleteEntry(id: string) {
		await fetch(`/journal/${id}`, { method: 'DELETE' });
	}

	async function restoreEntry(id: string) {
		// I could send a body with isDeleted false but since PATCH isn't used for anything else I don't have to.
		await fetch(`/journal/${id}`, { method: 'PATCH' });
	}

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
