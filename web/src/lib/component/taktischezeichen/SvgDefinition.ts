export type OrganisationConfig = {
	fill: string;
	borderAndText: string;
	name: string;
}
export const Organisation = {
	FireService: { fill: '#fa321e', borderAndText: '#000', name: 'Fw' },
	TechnicalRelieve: { fill: '#039', borderAndText: '#fff', name: 'THW' },
	Rescue: { fill: '#fff', borderAndText: '#000', name: 'HiOrg' },
	Command: { fill: '#fafa00', borderAndText: '#000', name: '' },
	Police: { fill: '#64dc32', borderAndText: '#fff', name: 'Pol' },
	Other: { fill: '#fa8c00', borderAndText: '#000', name: 'Sonst.' },
	Army: { fill: '#b4783c', borderAndText: '#000', name: 'Bw' },
	Civilian: { fill: '#bebebe', borderAndText: '#000', name: 'ZIV' }
} as const satisfies Record<string, OrganisationConfig>;
export type OrganisationKey = keyof typeof Organisation;

export const Grundzeichen = {
	Einheit: ['<rect x="5" y="20" width="90" height="60"/>'],
	Fahrzeug: [
		'<path d="M 5,20 q 45,10 90,0 v 60 h -90 z"/>',
	],
	Anhanger: [
		'<path d="M 10,20 q 45,10 85,0 v 60 h -85 z"/>',
	],
	Luftfahrzeug: ['<path d="M 5,70 a 45,50 180 0,1 90,0 Z"/>'],
	Stelle: ['<circle cx="50" cy="50" r="30"/>']
} as const satisfies Record<string, readonly string[]>;
export type GrundzeichenKey = keyof typeof Grundzeichen;

export const Grundeigenschaft = {
	Fuhrung: ['<rect x="5" y="20" width="100%" height="5%" fill="black"/>'],
	Versorgung: ['<rect x="5" y="75" width="100%" height="5%" fill="black"/>'],
	Drohne: [`<path d="M 50,55
				  	   l -20,-15
				  	   v -5
				  	   l 20, 12
				  	   l 20, -12
				  	   v 5
				  	   z
				  	   " class="black-fill" />`
	],
	ortsfest: [],
} as const satisfies Record<string, readonly string[]>;
export type GrundeigenschaftKey = keyof typeof Grundeigenschaft;

export const Zusatzzeichen = {
	Drehfluegler: [
		`<path d="M 50,65
				  l -20,-10
				  v 20
				  l 40,-20
				  v 20
				  z
				  " class="black-fill"
		/>`]
} as const satisfies Record<string, readonly string[]>;
export type ZusatzzeichenKey = keyof typeof Zusatzzeichen;

export const TaktischeGrosse = {
	Trupp: [
		'<circle cx="50" cy="10" r="3"/>'
	],
	Staffel: [],
	Gruppe: [
		'<circle cx="40" cy="10" r="3"/>',
		'<circle cx="60" cy="10" r="3"/>'
	],
	Zug: [
		'<circle cx="30" cy="10" r="3"/>',
		'<circle cx="50" cy="10" r="3"/>',
		'<circle cx="70" cy="10" r="3"/>'
	],
	Bereitschaft: [
		'<line x1="0" y1="-35" x2="0" y2="-45" stroke-width="3"/>'
	],
	Abteilung: [
		'<line x1="-10" y1="-35" x2="-10" y2="-45" stroke-width="3"/>',
		'<line x1="10" y1="-35" x2="10" y2="-45" stroke-width="3"/>'
	],
	Grossverband: [
		'<line x1="-20" y1="-35" x2="-20" y2="-45" stroke-width="3"/>',
		'<line x1="0" y1="-35" x2="0" y2="-45" stroke-width="3"/>',
		'<line x1="20" y1="-35" x2="20" y2="-45" stroke-width="3"/>'
	],
} as const satisfies Record<string, readonly string[]>;
export type TaktischeGrosseKey = keyof typeof TaktischeGrosse;

export const Verwaltungsstufe = {
	Gemeinde: [],
	Kreis: [],
	Bezirk: [],
	Bundesland: [],
	Nationalstaat: [],
	EU: []
}
export type VerwaltungsstufeKey = keyof typeof Verwaltungsstufe;