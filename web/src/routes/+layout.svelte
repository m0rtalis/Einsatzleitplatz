<script lang="ts">
	// TODO: Use pico
	import 'chota';
	import './global.css';
	import { createOperationStore, createPageStore, createUserStore } from '$lib/state';

	interface Props {
		children?: import('svelte').Snippet;
	}

	let { children }: Props = $props();

	createOperationStore();
	createUserStore();
	const pageStore = createPageStore();
</script>

<svelte:head>
	<title>{$pageStore ? $pageStore.name + ' | Einsatzleitplatz' : 'Einsatzleitplatz'}</title>
</svelte:head>
{@render children?.()}
