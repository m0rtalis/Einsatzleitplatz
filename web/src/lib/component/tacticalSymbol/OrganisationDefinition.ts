export type OrganisationType = {
	fillColor: string;
	borderAndTextColor: string;
	name: string;
}
export const Organisation = {
	Feuerwehr: { fillColor: '#fa321e', borderAndTextColor: '#000', name: 'Fw' },
	THW: { fillColor: '#039', borderAndTextColor: '#fff', name: 'THW' },
	Rettungsdienst: { fillColor: '#fff', borderAndTextColor: '#000', name: 'HiOrg' },
	Fuehrung: { fillColor: '#fafa00', borderAndTextColor: '#000', name: '' },
	Polizei: { fillColor: '#64dc32', borderAndTextColor: '#fff', name: 'Pol' },
	Sonstige: { fillColor: '#fa8c00', borderAndTextColor: '#000', name: 'Sonst.' },
	Bundeswehr: { fillColor: '#b4783c', borderAndTextColor: '#000', name: 'Bw' },
	Zivil: { fillColor: '#bebebe', borderAndTextColor: '#000', name: 'ZIV' }
} as const satisfies Record<string, OrganisationType>;
export type OrganisationKey = keyof typeof Organisation;
