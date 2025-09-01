<script lang="ts">
	import TacticalSymbol from '$lib/component/TacticalSymbol.svelte';

	import { GrundzeichenBuilder } from '$lib/component/TacticalSymbol.svelte';

	const a = new GrundzeichenBuilder('Einheit')
		.organisation('Rescue', 'BRK')
		.fahigkeit('sanitatsdienst', 'SEG')
		.grundeigenschaft(['Fuhrung', 'Sonderfunktion'])
		.groese('Zug');
// 	https://banjocode.com/post/svelte/client-side-library
</script>

{#await import('$lib/component/TacticalSymbol.svelte')}
	Loading...
{:then x }
	<TacticalSymbol symbol={a}/>
{/await}