<script lang="ts">
	import { setPageName } from '$lib/store';

	setPageName('Journal');
</script>

<div class="content">
	<form class="new-journal-form">
		<fieldset>
			<legend>Create new journal entry</legend>
			<label for="journal-type">Type</label>
			<select id="journal-type" name="type"></select>
			<label for="journal-entry">Event</label>
			<textarea id="journal-entry" name="entry" class="entry-area" />
			<button type="submit">Create</button>
		</fieldset>
	</form>
	<!-- List journal entries -->
</div>

<style>
    .new-journal-form {
        padding-right: 15px;
        padding-left: 15px;
    }

    .entry-area {
        height: 20rem;
    }

    .new-journal-form button[type=submit] {
        width: 100%;
        margin-top: 1rem;
        cursor: pointer;
    }

</style>
