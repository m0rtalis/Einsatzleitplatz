<script lang="ts">
	import 'sanitize.css';
	import 'sanitize.css/forms.css';
	import 'sanitize.css/typography.css';
	import 'sanitize.css/assets.css';
	import 'sanitize.css/ui-monospace.css';
	import 'sanitize.css/reduce-motion.css';
	import 'chota';
	import './global.css';
	import { createOperationStore, createPageStore, createUserStore } from '$lib/state';
	import type { LayoutProps } from './$types';
	import { onMount } from 'svelte';
	import { redirect } from '@sveltejs/kit';
	import { StatusCode } from '$lib/utility/statusCode';

	let { children }: LayoutProps = $props();

	createOperationStore();
	createUserStore();
	const pageStore = createPageStore();

	const isUserLoggedIn = () => true;
	onMount(() => {
		// TODO: Implement isUserLoggedIn
		if (!isUserLoggedIn()) {
			redirect(StatusCode.TEMPORARY_REDIRECT, '/login');
		}
	});
</script>

<svelte:head>
	<title>{$pageStore ? $pageStore.name + ' | Einsatzleitplatz' : 'Einsatzleitplatz'}</title>
</svelte:head>

{@render children?.()}
