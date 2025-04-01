<script lang="ts">
	import 'chota';
	import './global.css';
	import { createOperationStore, createPageStore, createUserStore } from '$lib/state';
	import type { LayoutProps } from './$types';
	import { onMount } from 'svelte';
	import { redirect } from '@sveltejs/kit';

	let { children }: LayoutProps = $props();

	createOperationStore();
	createUserStore();
	const pageStore = createPageStore();

	const isUserLoggedIn = () => true;
	onMount(() => {
		// TODO: Implement isUserLoggedIn
		if (!isUserLoggedIn()) {
			redirect(307, '/login');
		}
	});
</script>

<svelte:head>
	<title>{$pageStore ? $pageStore.name + ' | Einsatzleitplatz' : 'Einsatzleitplatz'}</title>
</svelte:head>
{@render children?.()}
