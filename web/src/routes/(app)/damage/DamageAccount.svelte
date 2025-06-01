<script lang="ts">
</script>

<div class="container">
	<div class="header">
		<!-- Header -->
		<div class="circle" style:grid-area="icon"></div>
		<label for="name">Name: </label><input style:grid-area="name" name="name" />
		<label for="location">Location: </label><input style:grid-area="location" name="location" />
		<label for="leader">Leader: </label><input name="leader" />
		<label for="radio">Radio: </label><input name="radio" />
	</div>
	<div class="content">
		<!-- Body https://www.w3schools.com/howto/howto_css_two_columns.asp -->
		<div class="left">
			<!-- Left side: Damage -->
			<span><b>Damage</b></span>
			<div></div>
		</div>
		<div class="right">
			<!-- Right side: Units -->
			<span><b>Deployed forces</b></span>
			<div></div>
		</div>
	</div>
</div>

<style>
	* {
		/*border: black solid 1px;*/
	}

	.container {
		border: black solid 3px;
		padding: 1rem 0;
		box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.3);

		.header {
			display: grid;
			grid-template-areas:
				'icon lable-name name name name'
				'icon lable-location location location location'
				'icon lable-leader leader label-radio radio';
			grid-template-columns: min-content min-content auto min-content auto;
			grid-column-gap: 1rem;
			grid-row-gap: 0.3rem;

			align-items: center;
			border-bottom: black solid 3px;
			padding-bottom: 1rem;
			margin-bottom: 1rem;
			padding-right: 1rem;

			.circle {
				height: 50px;
				width: 50px;
				margin-left: 1rem;
				background-color: orange;
				border-radius: 50%;
				display: inline-block;
				justify-self: center;
				align-self: center;
			}

			label {
				margin-right: 1rem;
			}

			input {
				background-color: transparent;
				padding: 0;
				border: none;
				border-bottom: 2px solid black;
			}
		}

		.content {
            width: 100%;
			display: flex;
			flex: 1 0 auto;

			div {
				min-height: 50rem;
				width: 100%;

				span {
					display: block;
					font-size: 2.5rem;
					text-align: center;
					text-decoration: underline;
				}
			}

            .left {
                margin-left: 1rem;
                border-right: black solid 2px;
            }
            .right {
                margin-right: 1rem;
                border-left: black solid 2px;
            }
        }
	}
</style>
