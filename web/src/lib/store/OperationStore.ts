import {writable} from "svelte/store";

export const operationStore = writable<{id: string, name: string}>()
