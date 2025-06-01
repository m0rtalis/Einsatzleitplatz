<script module>
	import { TacticalSignBuilder, UnitBuilder, SiteBuilder, VehicleBuilder, AircraftBuilder } from '$lib/component/taktischezeichen/TacticalSignBuilder.js';

	export { UnitBuilder, SiteBuilder, VehicleBuilder, AircraftBuilder };
</script>

<script lang="ts">
	let { symbol }: { symbol: TacticalSignBuilder } = $props();

	const svgString = symbol.make();
</script>

<!--https://stackoverflow.com/questions/62095674/why-does-a-css-svg-rect-have-a-thicker-stroke-on-the-right-and-bottom-sides -->
<!--https://css-tricks.com/scale-svg/-->
{@html svgString}
