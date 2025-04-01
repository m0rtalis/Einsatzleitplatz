<script lang="ts">
	interface Props {
		time: string | Date;
	}

	// TODO: Work with some time library like day.js
	let { time }: Props = $props();

	const timeDate = new Date(time);

	// Tactical Time: TThhmmMMMJJ 13 August 2013, 17:45 Uhr = 131745aug13
	const timeString: string = timeDate.toLocaleString();
</script>

<span>{timeString}</span>
