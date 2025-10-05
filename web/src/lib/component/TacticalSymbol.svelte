<script lang="ts">
	import type { SVG } from '$lib/utility/svg';
	import './tacticalSymbol/TacticalSymbol.css';

	let { symbol }: { symbol: SVG } = $props();

</script>

{@html symbol.toSvgString()}
