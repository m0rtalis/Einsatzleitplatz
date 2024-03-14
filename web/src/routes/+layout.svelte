<script lang="ts">
	// One important property this sets is `* { box-sizing: border-box }`
	// https://www.w3schools.com/css/css3_box-sizing.asp
	import 'chota';
	// TODO: Put ALL icons from all components into one file which is imported everywhere
	import IconMenu from 'virtual:icons/mdi/menu';
	import IconMenuClose from 'virtual:icons/mdi/menu-close';
	import IconHome from 'virtual:icons/mdi/home';
	import IconLogin from 'virtual:icons/mdi/login';
	import IconDiary from 'virtual:icons/uil/diary';
	import IconCommunication from 'virtual:icons/icon-park-outline/communication';
	import IconAmbulance from 'virtual:icons/mdi/ambulance';
	import IconPatient from 'virtual:icons/mdi/swiss-cross-box';
	import IconSettings from 'virtual:icons/mdi/settings';
	import IconArrows from 'virtual:icons/material-symbols-light/double-arrow';
	import { page } from '$app/stores';
	import { isClipped } from '$lib/dom/isClipped';
	import { throttle } from '$lib/js';

	$: path = $page.route.id;
	// TODO: Default should be false but easier to debug for now
	let isSidenavOpen: boolean = false;
	let sidemenu: HTMLElement | undefined;
	let showScrollArrow: boolean = false;
	let expandSidenavTimer: NodeJS.Timeout | null;

	function checkShowNavArrow() {
		showScrollArrow = sidemenu ? isClipped(sidemenu).bottom : false;
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
	<span>Header</span>
</header>

<!-- TODO: Open Sidenav if mouse is over it for 2sec, close on mouse out -->
<nav class="sidenav" class:collapsed={!isSidenavOpen} on:scroll={checkShowNavArrow}
	 on:mouseleave={cancelExpandSidenavTimer}>
	<button type="button" title="Sidebar" class="sidenav-button icon-only"
			on:click={() => isSidenavOpen = !isSidenavOpen}>
		{#if isSidenavOpen}
			<IconMenuClose style="min-width: 1.5em; min-height: 1.5em; rotate: 180deg" />
		{:else }
			<IconMenu style="min-width: 1.5em; min-height: 1.5em;" />
		{/if}
	</button>
	<menu class="sidenav-menu" bind:this={sidemenu} on:mouseenter={setExpandSidenavTimer}
	>
		<!-- To be frank no idea what to put into home. -->
		<li><a href="/" class:active={path === "/"}>
			<IconHome style="min-width: 1.5em; min-height: 1.5em" />
			<span>Home</span></a></li>
		<!-- Temporary -->
		<li class:active={path?.startsWith("/login")}><a href="/login">
			<IconLogin style="min-width: 1.5em; min-height: 1.5em" />
			<span>Login</span></a></li>
		<li class:active={path?.startsWith("/diary")}><a href="/diary">
			<IconDiary style="min-width: 1.5em; min-height: 1.5em" />
			<span>Diary</span></a></li>
		<li class:active={path?.startsWith("/communication")}><a href="/communication">
			<IconCommunication style="min-width: 1.5em; min-height: 1.5em" />
			<span>Communication</span></a></li>
		<li class:active={path?.startsWith("/units")}><a href="/units">
			<IconAmbulance style="min-width: 1.5em; min-height: 1.5em" />
			<span>Units</span></a></li>
		<li class:active={path?.startsWith("/patients")}><a href="/patients">
			<IconPatient style="min-width: 1.5em; min-height: 1.5em" />
			<span>Patients</span></a></li>
		<li class:active={path?.startsWith("/settings")}><a href="/settings">
			<IconSettings style="min-width: 1.5em; min-height: 1.5em" />
			<span>Settings</span></a></li>
	</menu>
	{#if showScrollArrow}
		<div class="sidenav-scroll-arrow">
			<IconArrows style="width: 2em; height: 2em; rotate: 90deg" />
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
        display: block;
        position: fixed;
        top: 0;
        left: 0; /*var(--sidenav-width-collapsed);*/
        width: 100vw;
        max-width: 100vw;
        min-width: 100vw;
        border-bottom: 2px solid var(--font-color);
        box-shadow: 4px 0 8px 0 var(--font-color);
        max-height: var(--header-height);
        min-height: var(--header-height);
        background-color: var(--color-lightGrey);
        z-index: 1;
    }

    .header > *:first-child {
        margin-left: 1.5rem;
    }

    /* /Header */

    /* Sidenav */
    .sidenav {
        display: block;
        position: fixed;
        top: 0; /*var(--header-height);*/
        left: 0;
        background: var(--color-grey);
        min-height: 100vh;
        height: 100vh;
        font-size: var(--nav-font-size);
        transition: max-width .5s ease-in-out;
        max-width: calc(2rem + 1rem + 15em);
        overflow-x: hidden;
        overflow-y: auto;
        border-right: 2px solid var(--font-color);
        box-shadow: 4px 0 8px 0 var(--font-color);
        z-index: 2;
        scrollbar-width: none;
    }

    .sidenav::-webkit-scrollbar {
        /* TODO: Remove as Chrome 121 supports scrollbar-width. */
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
        width: var(--sidenav-width-collapsed);
        background-color: transparent;
        padding-left: 0;
        padding-right: 0;
    }

    .sidenav-menu {
        list-style-type: none;
        padding-left: 0;
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
        bottom: 10px;
        pointer-events: none;
    }

    /* /Sidenav */

    /* Main */
    .main {
        padding-top: var(--header-height);
        padding-left: var(--sidenav-width-collapsed);
        background-color: grey;
        overflow: auto;
    }

    /* /Main */
</style>
