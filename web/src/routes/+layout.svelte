<script lang="ts">
	// TODO: Use pico
	import 'chota';
	import { createOperationStore, createPageStore, createUserStore } from '$lib/store';

	createOperationStore()
	createUserStore()
	const pageStore = createPageStore()
</script>

<svelte:head>
	<title>{$pageStore ? $pageStore.name + ' | Einsatzleitplatz' : 'Einsatzleitplatz'}</title>
</svelte:head>
<slot></slot>

<style>
    :global(:root) {
        height: auto;
        min-height: 100%;
    }

    :global(body) {
        height: auto;
        min-height: 100%;
        overflow-x: hidden;
        overflow-y: auto;
        background-color: grey;
    }
</style>
