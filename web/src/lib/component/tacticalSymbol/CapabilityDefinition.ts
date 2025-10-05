import type { BaseSymbolType } from '$lib/component/tacticalSymbol/BaseSymbolDefinition';

export type CapabilityType = {  };

export const Capability = {
	sanitaetsdienst: ({ size }: BaseSymbolType) =>
		[
			`<line x1="0" y1="${size.height / 2}" x2="${size.width}" y2="${size.height / 2}"/>`,
			`<line x1="${size.width / 2}" y1="0" x2="${size.width / 2}" y2="${size.height}"/>`
		]
} as const satisfies Record<string, (_: BaseSymbolType) => readonly string[]>;

export type CapabilityKey = keyof typeof Capability;