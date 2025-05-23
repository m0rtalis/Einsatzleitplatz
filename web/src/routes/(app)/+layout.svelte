<script lang="ts">
	// One important property this sets is `* { box-sizing: border-box }`
	// https://www.w3schools.com/css/css3_box-sizing.asp
	import 'chota';
	// TODO: Export ALL icons from all components into one file from where they are imported everywhere
	import MenuIcon from 'virtual:icons/mdi/menu';
	import MenuCloseIcon from 'virtual:icons/mdi/menu-open'; // It's weird but open looks more like close and vv
	import HomeIcon from 'virtual:icons/mdi/home';
	import LoginIcon from 'virtual:icons/mdi/login';
	import JournalIcon from 'virtual:icons/mdi/journal-outline';
	import CommunicationIcon from 'virtual:icons/icon-park-outline/communication';
	import AmbulanceIcon from 'virtual:icons/mdi/ambulance';
	import PatientIcon from 'virtual:icons/mdi/swiss-cross-box';
	import SettingsIcon from 'virtual:icons/mdi/settings';
	import ArrowsIcon from 'virtual:icons/material-symbols-light/double-arrow';
	import DamageAccountIcon from 'virtual:icons/material-symbols/team-dashboard-outline';
	import SimpleIconsDevbox from 'virtual:icons/simple-icons/devbox';
	import { isClipped } from '$lib/dom/isClipped';
	import { debounce, throttle } from '$lib/js';
	import {
		createSseStore,
		getOperationStore,
		getPageStore,
		setFromMessageEvent,
	} from '$lib/state';
	import { onMount } from 'svelte';
	import { type SseEventName } from '$lib/api';
	import { EventName } from '$lib/api/elp';
	import type { LayoutProps } from './$types';
	import { page } from '$app/state';
	import { EventSource } from 'eventsource';

	let { children }: LayoutProps = $props();

	let path = $derived(page.route.id);
	let isSidenavOpen: boolean = $state(false);
	let sidemenu: HTMLElement | undefined = $state();
	let showScrollUpArrow: boolean = $state(false);
	let showScrollDownArrow: boolean = $state(false);
	let expandSidenavTimer: number | null = $state(null);
	let main: HTMLElement | undefined = $state();

	createSseStore();
	const operationStore = getOperationStore();
	const pageStore = getPageStore();

	function checkShowNavArrow() {
		showScrollUpArrow = sidemenu ? isClipped(sidemenu).top : false;
		showScrollDownArrow = sidemenu ? isClipped(sidemenu).bottom : false;
	}

	function setExpandSidenavTimer() {
		if (!isSidenavOpen) {
			expandSidenavTimer = setTimeout(() => {
				isSidenavOpen = true;
			}, 700);
		}
	}

	function cancelExpandSidenavTimer() {
		if (expandSidenavTimer) {
			clearTimeout(expandSidenavTimer);
			expandSidenavTimer = null;
			isSidenavOpen = false;
		}
	}

	// TODO: Save scroll position https://stackoverflow.com/questions/65548728/how-to-remember-scroll-position-of-overflowscroll-div
	const sseEventNames: SseEventName[] = [EventName.CHANGED_JOURNAL_ENTRY];
	onMount(() => {
		const eventSource = new EventSource('/api/events');
		eventSource.onmessage = (event: MessageEvent) => {
			console.log('SSE Message', event);
		};
		eventSource.onerror = (error) => {
			console.warn(eventSource, error, 'Error in SSE');
		};
		sseEventNames.forEach((name) =>
			eventSource.addEventListener(name, (evt) => {
				setFromMessageEvent(evt);
			}),
		);
		// TODO: Build debug page which shows the event stream
		return () => {
			console.log('Close source');
		};
	});
</script>

<svelte:window onresize={throttle(checkShowNavArrow)} />

<!-- TODO: header with operation name, operation switcher, search, notifications, profile -->
<header>
	<button
		type="button"
		title="Sidebar"
		class="sidenav-button icon-only"
		onclick={() => (isSidenavOpen = !isSidenavOpen)}
	>
		{#if isSidenavOpen}
			<MenuCloseIcon style="min-width: 1.5em; min-height: 1.5em" />
		{:else}
			<MenuIcon style="min-width: 1.5em; min-height: 1.5em;" />
		{/if}
	</button>
	<span class="header-group">
		<span>{$operationStore?.name ?? ''}</span>
		<span>{$pageStore?.name ?? 'Einsatzleitplatz'}</span>
		<span>Profile</span>
	</span>
</header>

<nav
	class:collapsed={!isSidenavOpen}
	onscroll={checkShowNavArrow}
	onmouseleave={cancelExpandSidenavTimer}
>
	{#if showScrollUpArrow}
		<div class="sidenav-scroll-arrow up-arrow">
			<ArrowsIcon style="width: 2em; height: 2em; rotate: -90deg" />
		</div>
	{/if}
	<menu class="sidenav-menu" bind:this={sidemenu} onmouseenter={setExpandSidenavTimer}>
		<!-- To be frank no idea what to put into home. -->
		<li class:active={path === '/(app)'}>
			<a href="/">
				<HomeIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Home</span></a
			>
		</li>
		<!-- Temporary -->
		<li class:active={path?.startsWith('/login')}>
			<a href="/login">
				<LoginIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Login</span></a
			>
		</li>
		<li class:active={path?.startsWith('/(app)/journal')}>
			<a href="/journal">
				<JournalIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Journal</span></a
			>
		</li>
		<li class:active={path?.startsWith('/(app)/communication')}>
			<a href="/communication">
				<CommunicationIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Communication</span></a
			>
		</li>
		<li class:active={path?.startsWith('/(app)/damage')}>
			<a href="/damage">
				<DamageAccountIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Damage Account</span>
			</a>
		</li>
		<li class:active={path?.startsWith('/(app)/units')}>
			<a href="/units">
				<AmbulanceIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Units</span></a
			>
		</li>
		<li class:active={path?.startsWith('/(app)/patients')}>
			<a href="/patients">
				<PatientIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Patients</span></a
			>
		</li>
		<li class:active={path?.startsWith('/(app)/settings')}>
			<a href="/settings">
				<SettingsIcon style="min-width: 1.5em; min-height: 1.5em" />
				<span>Settings</span></a
			>
		</li>
		<li class:active={path?.startsWith('/(app)/dev')}>
			<a href="/dev">
				<SimpleIconsDevbox style="min-width: 1.5em; min-height: 1.5em" />
				<span>Developer</span></a
			>
		</li>
	</menu>
	{#if showScrollDownArrow}
		<div class="sidenav-scroll-arrow down-arrow">
			<ArrowsIcon style="width: 2em; height: 2em; rotate: 90deg" />
		</div>
	{/if}
</nav>

<!-- TODO: This is preparation for saving scroll position on site reload. Need to migrate stores to state before this. -->
<main bind:this={main} onscrollend={debounce(() => main && main.scrollTop, 200)}>
	{@render children?.()}
</main>

<style>
	:root {
		--sidenav-width-collapsed: 3em;
		--nav-font-size: 2rem;
		--header-height: 5rem;
	}

	/* Header */
	header {
		display: flex;
		font-size: var(--nav-font-size);
		justify-content: space-between;
		align-items: stretch;
		position: fixed;
		top: 0;
		left: 0;
		width: 100vw;
		border-bottom: 2px solid var(--font-color);
		box-shadow: 4px 0 8px 0 var(--font-color);
		max-height: var(--header-height);
		min-height: var(--header-height);
		background-color: var(--color-grey);
		z-index: 20;
		padding-right: 1rem;
	}

	.header-group {
		display: flex;
		width: 100%;
		justify-content: space-between;
		align-items: center;
	}

	.header-group span {
		width: auto;
	}

	/* /Header */

	/* Sidenav */
	nav {
		display: block;
		position: fixed;
		top: var(--header-height);
		left: 0;
		background: var(--color-grey);
		min-height: 100vh;
		padding-bottom: 5rem;
		height: 100vh;
		font-size: var(--nav-font-size);
		transition: max-width 0.5s ease-in-out;
		max-width: calc(2rem + 1rem + 15em);
		overflow-x: hidden;
		overflow-y: auto;
		border-right: 2px solid var(--font-color);
		box-shadow: 4px 0 8px 0 var(--font-color);
		scrollbar-width: none;
		z-index: 10;
	}

	nav::-webkit-scrollbar {
		/* Remove as Chrome 121 supports scrollbar-width. */
		display: none;
	}

	nav.collapsed {
		max-width: var(--sidenav-width-collapsed);

		span {
			transition-property: visibility;
			transition-delay: 0.4s;
			transition: visibility 0s step-end 0.45s;
			visibility: hidden;
		}
	}

	.sidenav-button {
		font-size: var(--nav-font-size);
		width: var(--sidenav-width-collapsed);
		background-color: transparent;
		padding-left: 0;
		padding-right: 0;
		margin-left: 0;
	}

	.sidenav-menu {
		list-style-type: none;
		padding-left: 0;
		margin-top: 0;
	}

	.sidenav-menu .active {
		background: var(--color-primary);
	}

	.sidenav-menu li {
		border-bottom: 4px solid var(--font-color);

		a {
			width: 100%;
			padding: 2rem 2rem 2rem 1rem;
			display: flex;
			align-items: center;
			color: var(--font-color);

			span {
				padding-left: 1rem;
				white-space: nowrap;
			}
		}

		a:hover {
			background: var(--color-lightGrey);
		}
	}

	.sidenav-scroll-arrow {
		display: block;
		margin: auto;
		width: min-content;
		height: min-content;
		position: sticky;
		pointer-events: none;
	}

	.sidenav-scroll-arrow.up-arrow {
		top: 10px;
	}

	.sidenav-scroll-arrow.down-arrow {
		bottom: 10px;
	}

	/* /Sidenav */

	/* Main */
	main {
		margin-top: var(--header-height);
		margin-left: calc(var(--sidenav-width-collapsed) + 1rem);
		background-color: grey;
		overflow: auto;
		padding-left: 2rem;
		height: calc(100vh - var(--header-height));
		width: calc(100vw - var(--sidenav-width-collapsed) - 1rem);
		scroll-behavior: smooth;
	}

	/* /Main */
</style>
