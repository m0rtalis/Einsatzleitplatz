const Betreuungswesen = {
	betreuung: [
		'<line x1="-45" y1="30" x2="0" y2="-30"/>',
		'<line x1="0" y1="-30" x2="45" y2="30"/>'
	],
	verpflegung: [
		'<path d="M 0,0 l 10,-5 a 10,10 0,1,0 0,10 Z" />'
	],
	zubereitung_verpflegung: [
		'<path d="M 5,5 l 10,-5 a 10,10 0,1,0 0,10 Z"/>',
		`<path d="M -10,15
				  v -15
				  c 3,0 2,-5 -1.25,-5
				  c -3.5,0 -4.25,5 -1.25,5
				  v 15
				  a 1,1 180 1,0 2.5,0
				  "
			  class="black-fill"
		/>`
	],
	unterbringung_schlafen: [
		'<line x1="-15" y1="0" x2="-15" y2="15"/>',
		'<line x1="15" y1="0" x2="15" y2="15"/>',
		'<line x1="-15" y1="10" x2="15" y2="10" />',
		'<path d="M -15,10 a 4,2 1,0,1 30,0" />'
	],
	betrstelle: [
		'<circle cx="0" cy="0" r="18" fill="none" />',
		'<line x1="-12.727" y1="12.727" x2="0" y2="-18" />',
		'<line x1="12.727" y1="12.727" x2="0" y2="-18" />',
		'<line x1="-40" y1="10" x2="0" y2="-25"/>',
		'<line x1="40" y1="10" x2="0" y2="-25"/>'
	],
	anlaufstelle: [
		'<circle cx="0" cy="5" r="18" fill="none" />',
		`<path d="M 0,-10
                      v 25
                      l -5,5
                      m 5,-5
                      l 5,5
                      m -15,-25
                      h 20
                      l -5,-5
                      m 5,5
                      l -5,5
                      m -15,-5
                      l 5,-5
                      m -5,5
                      l 5,5"
		/>`
	],
	trinkwasser: [
		`<path d="M -15,0
				  h 25
				  a 5,5 90 0,1 5,5
				  m -10,-3
				  v -7
				  m -3,0
				  h 6
				  "
		/>`
	],
}

const Sanitatswesen = {
	sanitatsdienst: [
		'<path d="M 0,-30 v 60 m -45,-30 h 90" />'
	],
	rettungswesen: [
		'<path d="M 0,-30 v 60 m -45,-30 h 90" />',
		'<line x1="20" y1="-10" x2="20" y2="10"/>'
	],
	arztwesen: [
		'<path d="M 0,-30 v 60 m -45,-30 h 90" />',
		'<line x1="-10" y1="20" x2="10" y2="20"/>'
	],
	transport: [
		'<circle cx="0" cy="0" r="15" fill="none" />',
		'<line x1="15" y1="0" x2="-15" y2="0"/>',
		'<line x1="0" y1="15" x2="0" y2="-15"/>',
		'<line x1="10.606" y1="-10.606" x2="-10.606" y2="10.606"/>',
		'<line x1="-10.606" y1="-10.606" x2="10.606" y2="10.606"/>'
	],
}

export const Fahigkeit = {
	...Betreuungswesen,
	...Sanitatswesen,
	dekon: [
		'<line x1="10" y1="10" x2="-10" y2="-10" />',
		'<line x1="10" y1="-10" x2="-10" y2="10" />',
		'<circle cx="-11" cy="-7" r="3" />',
		'<circle cx="11" cy="-7" r="3" />'
	],
	stelle: [
		'<circle cx="0" cy="0" r="18" fill="none" />'
	],
	stelle_fest: [
		'<circle cx="0" cy="0" r="18" fill="none" />',
		'<line x1="-40" y1="10" x2="0" y2="-25"/>',
		'<line x1="40" y1="10" x2="0" y2="-25"/>'
	],
} as const satisfies Record<string, readonly string[]>;
export type FahigkeitKey = keyof typeof Fahigkeit;