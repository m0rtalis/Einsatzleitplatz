import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vitest/config';
import Icons from 'unplugin-icons/vite';
import devtoolsJson from 'vite-plugin-devtools-json';

export default defineConfig({
	plugins: [sveltekit(), Icons({ compiler: 'svelte' }), devtoolsJson()],
	test: {
		include: ['src/**/*.{test,spec}.{js,ts}']
	},
});
