import {writable} from "svelte/store";

export const operationStore = writable<{id: number, name: string}>()
