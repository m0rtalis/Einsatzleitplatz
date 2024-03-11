<script lang="ts">
	// One important property this sets is box-sizing: border-box for all elements
	// https://www.w3schools.com/css/css3_box-sizing.asp
	import 'chota';
	// TODO: Put ALL icons from all components into one file which is imported everywhere
	import IconMenu from 'virtual:icons/mdi/menu';
	import IconHome from 'virtual:icons/mdi/home';
	import IconLogin from 'virtual:icons/mdi/login';
	import IconDiary from 'virtual:icons/uil/diary';
	import IconCommunication from 'virtual:icons/icon-park-outline/communication';
	import IconAmbulance from 'virtual:icons/mdi/ambulance';
	import IconPatient from 'virtual:icons/mdi/swiss-cross-box';
	import IconSettings from 'virtual:icons/mdi/settings';
	import { page } from '$app/stores';

	$: path = $page.route.id;
	let isSidenavShown: boolean = true;
</script>

<!-- TODO: Top bar with operation name, operation switcher, search, notifications, profile -->
<nav id="sidenav" >
	<button id="sidenav-control" type="button" title="Sidebar" class="icon-only"
			on:click={() => isSidenavShown = !isSidenavShown}>
		<IconMenu />
	</button>
	<menu id="sidenav-menu">
		<!-- TODO!!: Center icons to align with center of text -->
		<!-- To be frank no idea what to put into home. -->
		<li><a href="/" class:active={path === "/"}>
			<IconHome />
			Home</a></li>
		<!-- Temporary -->
		<li><a href="/login" class:active={path?.startsWith("/login")}>
			<IconLogin />
			Login</a></li>
		<li><a href="/diary" class:active={path?.startsWith("/diary")}>
			<span><IconDiary />
			Diary</span></a></li>
		<li><a href="/communication" class:active={path?.startsWith("/communication")}>
			<IconCommunication />
			Communication</a></li>
		<li><a href="/units" class:active={path?.startsWith("/units")}>
			<IconAmbulance />
			Units</a></li>
		<li><a href="/patients" class:active={path?.startsWith("/patients")}>
			<IconPatient />
			Patients</a></li>
		<li><a href="/settings" class:active={path?.startsWith("/settings")}>
			<IconSettings />
			Settings</a></li>
	</menu>
</nav>
<!-- On left side: Avatar with name.
 Navigation on left side. Open with menu button. -->

<slot></slot>

<style>

    #sidenav {
        display: block;
        position: fixed;
        top: 0;
        left: 0;
        background: var(--color-grey);
        height: 100vh;
        margin: 0;
        padding: 0;

    }

    #sidenav-control {
        margin-top: 1rem;
        margin-bottom: 1rem;
        width: 100%;
    }

    #sidenav-menu {
        list-style-type: none;
        margin: 0;
        padding-left: 0;
    }

    #sidenav-menu > li {
        border-style: solid;
        border-width: 0 0 2px 0;
        border-color: black;
    }

    #sidenav-menu > li > a {
        display: block;
        width: 100%;
        padding-right: 2rem;
        padding-left: 1rem;
        color: black;

    }

    #sidenav-menu > li > a.active {
        background: var(--color-primary);
    }

    #sidenav-menu > li > a:hover {
        background: var(--color-lightGrey);
    }
</style>