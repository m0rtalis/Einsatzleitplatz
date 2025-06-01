<script lang="ts">
	import MilitarySymbol from '$lib/component/MilitarySymbol.svelte';

	import { GrundzeichenBuilder } from '$lib/component/MilitarySymbol.svelte';

	const a = new GrundzeichenBuilder('Einheit')
		.organisation('Rescue', 'BRK')
		.fahigkeit('sanitatsdienst', 'SEG')
		.grundeigenschaft(['Fuhrung', 'Sonderfunktion'])
		.groese('Zug');
// 	https://banjocode.com/post/svelte/client-side-library
</script>

{#await import('$lib/component/MilitarySymbol.svelte')}
	Loading...
{:then x }
	<MilitarySymbol symbol={a}/>
{/await}