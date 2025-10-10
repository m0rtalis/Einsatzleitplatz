import type { Area, Point } from '$lib/utility/svg';

export type BaseSymbolType = {
	svg: string[]
	size: { width: number, height: number }
	printableArea: Area,
	organisationPoint: Point,
	unitPoint: Point,
	shortnamePoint: Point,
}

export const BaseSymbol = {
	Einheit: {
		svg: ['<rect x="0" y="0" width="90" height="60"/>'],
		size: { width: 90, height: 60 },
		printableArea: { topLeft: { x: 0, y: 0 }, bottomRight: { x: 90, y: 60 } },
		organisationPoint: { x: 87, y: 55 },
		unitPoint: { x: 3, y: 13 },
		shortnamePoint: { x: 45, y: 36 }
	},
	Fahrzeug: {
		svg: [
			'<path d="M 0,0 q 45,10 90,0 v 60 h -90 z"/>'
		],
		size: { width: 90, height: 60 },
		printableArea: { topLeft: { x: 0, y: 5 }, bottomRight: { x: 90, y: 60 } },
		organisationPoint: { x: 87, y: 55 },
		unitPoint: { x: 3, y: 15 },
		shortnamePoint: { x: 45, y: 36 }
	},
	Stelle: {
		svg: ['<circle cx="50" cy="50" r="50"/>'],
		size: { width: 60, height: 60 },
		printableArea: { topLeft: { x: 0, y: 0 }, bottomRight: { x: 60, y: 60 } },
		organisationPoint: { x: 50, y: 50 },
		unitPoint: { x: 3, y: 15 },
		shortnamePoint: { x: 45, y: 36 }
	}
	/*
		Anhanger: [
			'<path d="M 10,20 q 45,10 85,0 v 60 h -85 z"/>'
		],
		Luftfahrzeug: ['<path d="M 5,70 a 45,50 180 0,1 90,0 Z"/>'],
		Stelle: ['<circle cx="50" cy="50" r="30"/>']
	*/
} as const satisfies Record<string, BaseSymbolType>;
export type BaseSymbolKey = keyof typeof BaseSymbol;
