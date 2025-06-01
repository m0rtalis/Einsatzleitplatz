export const Transportart = {
	Strassenfahig: [
		'<circle cx="-40" cy="35" r="5" fill="white"/>',
		'<circle cx="40" cy="35" r="5" fill="white"/>'
	],
	Gelandefahig: [
		'<circle cx="-40" cy="35" r="5" fill="white"/>',
		'<circle cx="0" cy="35" r="5" fill="white"/>',
		'<circle cx="40" cy="35" r="5" fill="white"/>'
	],
	Gelandegangig: [
		'<circle cx="-40" cy="35" r="5" fill="white"/>',
		'<circle cx="0" cy="35" r="5" fill="white"/>',
		'<circle cx="40" cy="35" r="5" fill="white"/>',
		'<line x1="-35" y1="35" x2="-5" y2="35"/>',
		'<line x1="5" y1="35" x2="35" y2="35"/>'
	],
	Amphibienfahrzeug: [],
	Kettenfahrzeug: [],
	Schienenfahrzeug: [],
	Aufgleisbar: [],
	Wechsellader: [],
	Wechselbehalter: []
} as const satisfies Record<string, readonly string[]>;
export type TransportartKey = keyof typeof Transportart;

export const Anhanger = {
	Anhanger: [
		'<rect x="-50" y="-10" width="10" height="5" fill="white" />'
	],
	Anhanger_Abrollbehalter: [],
	Anhanger_Wechselbehalter: [],
	Anhanger_PKW: [],
	Anhanger_LKW: [],
	Abrollbehalter: [],
	Wechselbehalter: [],
	Wechselbrucke: [],
	Zieh_Schiebbar: []
} as const satisfies Record<string, readonly string[]>;
export type AnhangerKey = keyof typeof Anhanger;