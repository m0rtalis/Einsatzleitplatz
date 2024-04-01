<script lang="ts">
	export let time: string | Date

	const timeDate = typeof time === 'string' ? new Date(time) : time

	// Tactical Time: TThhmmMMMJJ 13 August 2013, 17:45 Uhr = 131745aug13
	const timeString: string = timeDate.toLocaleString()

</script>

<span>{timeString}</span>