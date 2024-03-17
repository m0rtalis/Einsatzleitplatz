<script lang="ts">
	// One important property this sets is `* { box-sizing: border-box }`
	// https://www.w3schools.com/css/css3_box-sizing.asp
	import 'chota';
	// TODO: Export ALL icons from all components into one file from where they are imported everywhere
	import MenuIcon from 'virtual:icons/mdi/menu';
	import MenuCloseIcon from 'virtual:icons/mdi/menu-close';
	import HomeIcon from 'virtual:icons/mdi/home';
	import LoginIcon from 'virtual:icons/mdi/login';
	import JournalIcon from 'virtual:icons/mdi/journal';
	import CommunicationIcon from 'virtual:icons/icon-park-outline/communication';
	import AmbulanceIcon from 'virtual:icons/mdi/ambulance';
	import PatientIcon from 'virtual:icons/mdi/swiss-cross-box';
	import SettingsIcon from 'virtual:icons/mdi/settings';
	import ArrowsIcon from 'virtual:icons/material-symbols-light/double-arrow';
	import { page } from '$app/stores';
	import { isClipped } from '$lib/dom/isClipped';
	import { throttle } from '$lib/js';
	import { pageStore } from '$lib/store';

	$: path = $page.route.id;
	let isSidenavOpen: boolean = false;
	let sidemenu: HTMLElement | undefined;
	let showScrollUpArrow: boolean = false;
	let showScrollDownArrow: boolean = false;
	let expandSidenavTimer: NodeJS.Timeout | null;

	function checkShowNavArrow() {
		showScrollUpArrow = sidemenu ? isClipped(sidemenu).top : false;
		showScrollDownArrow = sidemenu ? isClipped(sidemenu).bottom : false;
	}

	function setExpandSidenavTimer() {
		if (!isSidenavOpen) {
			expandSidenavTimer = setTimeout(() => {
				isSidenavOpen = true;
			}, 750);
		}
	}

	function cancelExpandSidenavTimer() {
		if (expandSidenavTimer) {
			clearTimeout(expandSidenavTimer);
			expandSidenavTimer = null;
			isSidenavOpen = false;
		}
	}
</script>

<svelte:window on:resize={throttle(checkShowNavArrow)} />

<!-- TODO: header with operation name, operation switcher, search, notifications, profile -->
<header class="header">
	<button type="button" title="Sidebar" class="sidenav-button icon-only"
			on:click={() => isSidenavOpen = !isSidenavOpen}>
		{#if isSidenavOpen}
			<MenuCloseIcon style="min-width: 1.5em; min-height: 1.5em; rotate: 180deg" />
		{:else }
			<MenuIcon style="min-width: 1.5em; min-height: 1.5em;" />
		{/if}
	</button>
	<span>{$pageStore?.name ?? 'Einsatzleitplatz'}</span>
	<span>Profile</span>
</header>

<nav class="sidenav" class:collapsed={!isSidenavOpen} on:scroll={checkShowNavArrow}
	 on:mouseleave={cancelExpandSidenavTimer}>
	{#if showScrollUpArrow}
		<div class="sidenav-scroll-arrow up-arrow">
			<ArrowsIcon style="width: 2em; height: 2em; rotate: -90deg" />
		</div>
	{/if}
	<menu class="sidenav-menu" bind:this={sidemenu} on:mouseenter={setExpandSidenavTimer}
	>
		<!-- To be frank no idea what to put into home. -->
		<li class:active={path === "/(app)"}><a href="/">
			<HomeIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Home</span></a></li>
		<!-- Temporary -->
		<li class:active={path?.startsWith("/login")}><a href="/login">
			<LoginIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Login</span></a></li>
		<li class:active={path?.startsWith("/(app)/journal")}><a href="/journal">
			<JournalIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Journal</span></a></li>
		<li class:active={path?.startsWith("/(app)/communication")}><a href="/communication">
			<CommunicationIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Communication</span></a></li>
		<li class:active={path?.startsWith("/(app)/units")}><a href="/units">
			<AmbulanceIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Units</span></a></li>
		<li class:active={path?.startsWith("/(app)/patients")}><a href="/patients">
			<PatientIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Patients</span></a></li>
		<li class:active={path?.startsWith("/(app)/settings")}><a href="/settings">
			<SettingsIcon style="min-width: 1.5em; min-height: 1.5em" />
			<span>Settings</span></a></li>
	</menu>
	{#if showScrollDownArrow}
		<div class="sidenav-scroll-arrow down-arrow">
			<ArrowsIcon style="width: 2em; height: 2em; rotate: 90deg" />
		</div>
	{/if}
</nav>

<main class="main">
	<slot></slot>
</main>

<style>
    :root {
        --sidenav-width-collapsed: 3em;
        --nav-font-size: 2rem;
        --header-height: 5rem;
    }

    /* Header */
    .header {
        font-size: var(--nav-font-size);
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: fixed;
        top: 0;
        left: 0;
        width: 100vw;
        border-bottom: 2px solid var(--font-color);
        box-shadow: 4px 0 8px 0 var(--font-color);
        max-height: var(--header-height);
        min-height: var(--header-height);
        background-color: var(--color-grey);
        z-index: 1;
		padding-right: 1rem;
    }

    /* /Header */

    /* Sidenav */
    .sidenav {
        display: block;
        position: fixed;
        top: var(--header-height);
        left: 0;
        background: var(--color-grey);
        min-height: 100vh;
        padding-bottom: 5rem;
        height: 100vh;
        font-size: var(--nav-font-size);
        transition: max-width .5s ease-in-out;
        max-width: calc(2rem + 1rem + 15em);
        overflow-x: hidden;
        overflow-y: auto;
        border-right: 2px solid var(--font-color);
        box-shadow: 4px 0 8px 0 var(--font-color);
        scrollbar-width: none;
        z-index: 1;
    }

    .sidenav::-webkit-scrollbar {
        /* Remove as Chrome 121 supports scrollbar-width. */
        display: none;
    }

    .sidenav.collapsed {
        max-width: var(--sidenav-width-collapsed);
    }

    .sidenav.collapsed span {
        transition-property: visibility;
        transition-delay: .4s;
        transition: visibility 0s step-end .45s;
        visibility: hidden;
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
    }

    .sidenav-menu li a {
        width: 100%;
        padding: 2rem 2rem 2rem 1rem;
        display: flex;
        align-items: center;
        color: var(--font-color);
    }

    .sidenav-menu li a:hover {
        background: var(--color-lightGrey);
    }

    .sidenav-menu li a span {
        padding-left: 1rem
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
        top: 10px
    }

    .sidenav-scroll-arrow.down-arrow {
        bottom: 10px;
    }

    /* /Sidenav */

    /* Main */
    .main {
        margin-top: var(--header-height);
        margin-left: calc(var(--sidenav-width-collapsed) + 1rem);
        background-color: grey;
        overflow: auto;
		padding-left: 2rem;
        height: calc(100vh - var(--header-height));
        width: calc(100vw - var(--sidenav-width-collapsed) - 1rem);
    }

    /* /Main */
</style>
