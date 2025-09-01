import {
	Grundeigenschaft,
	type GrundeigenschaftKey,
	Grundzeichen,
	type GrundzeichenKey,
	Organisation,
	type OrganisationConfig,
	type OrganisationKey,
	TaktischeGrosse,
	type TaktischeGrosseKey,
	Zusatzzeichen,
	type ZusatzzeichenKey
} from '$lib/component/taktischezeichen/SvgDefinition';
import { Fahigkeit, type FahigkeitKey } from '$lib/component/taktischezeichen/FahigkeitSvgDefinition';
import {
	Anhanger,
	type AnhangerKey,
	Transportart,
	type TransportartKey
} from '$lib/component/taktischezeichen/VehicleSvgDefinition';

export abstract class TacticalSignBuilder {
	protected grundzeichenSvg: readonly string[];
	protected organisationConfig: OrganisationConfig = Organisation.Other;
	protected organisationSvg: readonly string[] = [];
	protected grundeigenschaftSvg: readonly string[] = [];
	protected fahigkeitSvg: string[] = [];
	protected zusatzzeichenSvg: string[] = [];
	protected groeseSvg: readonly string[] = [];

	constructor(zeichen: GrundzeichenKey) {
		this.grundzeichenSvg = Grundzeichen[zeichen];
	}

	organisation(organisation: OrganisationKey, name?: string): this {
		this.organisationConfig = Organisation[organisation];
		if (name) {
			const fontSize = name.length > 4 ? 'x-small' : 'small';
			this.organisationSvg = [`<text x="41" y="20" font-size="${fontSize}" text-anchor="end">${name}</text>`];
		}
		return this;
	}

	grundeigenschaft(eigenschaften: GrundeigenschaftKey | GrundeigenschaftKey[]): this {
		this.grundeigenschaftSvg = Array.isArray(eigenschaften) ? eigenschaften.flatMap(e => Grundeigenschaft[e]) : Array.from(Grundeigenschaft[eigenschaften]);
		return this;
	}

	fahigkeit(fahigkeit: FahigkeitKey | FahigkeitKey[], options?: {
		name?: string,
		specification?: string
	} | string): this {
		const { name, specification } = typeof options === 'string' ? { name: options } : options ?? {};
		this.fahigkeitSvg = Array.isArray(fahigkeit) ? fahigkeit.flatMap(x => Array.from(Fahigkeit[x])) : Array.from(Fahigkeit[fahigkeit]);
		if (name) {
			const fontSize = name.length > 4 ? 'x-small' : 'small';
			this.fahigkeitSvg.push(`<text x="-41" y="-14" font-size="${fontSize}">${name}</text>`);
		}
		if (specification) {
			const fontSize = specification.length > 4 ? 'x-small' : 'small';
			this.fahigkeitSvg.push(`<text x="0" y="25" font-size="${fontSize}" text-anchor="middle">${specification}</text>`);
		}
		return this;
	}

	zusatzzeichen(zusatz: ZusatzzeichenKey | ZusatzzeichenKey[]): this {
		this.zusatzzeichenSvg = Array.isArray(zusatz) ? zusatz.flatMap(x => Array.from(Zusatzzeichen[x])) : Array.from(Zusatzzeichen[zusatz]);
		return this;
	}

	groese(groesse: TaktischeGrosseKey): this {
		this.groeseSvg = TaktischeGrosse[groesse];
		return this;
	}

	protected makeStyle(content: string[], id: string) {
		content.push('<style>');
		content.push(`.military-symbol {stroke:black; stroke-width: 1; user-select: none;}`);
		// Background color: https://stackoverflow.com/a/71160745/6900162
		// Bonus: The text looks way better (thinner)
		content.push('.military-symbol path {fill:none;}');
		content.push(`.military-symbol .Grundzeichen > path {fill:white;}`);
		content.push(`.military-symbol mask {mask-type:alpha}`);
		content.push(`.military-symbol .black-fill {fill:black;}`);

		content.push(`#military-symbol-${id} text {font-family:"Courier new",Verdana,Arial, sans-serif; font-weight:100; stroke:${this.organisationConfig.fill}; fill:${this.organisationConfig.borderAndText}; stroke-width:.3em; paint-order:stroke; stroke-linejoin:round;}`);
		content.push('</style>');
	}

	protected makeDefs(content: string[], id: string) {
		content.push('<defs>');
		content.push(`<g id="Grundzeichen-${id}" class="Grundzeichen" fill="${this.organisationConfig.fill}">`);
		content.push(...this.grundzeichenSvg);
		content.push('</g>');
		content.push(`<mask id="clip-Zeichen-${id}"><use href="#Grundzeichen-${id}" /></mask>`);
		content.push('</defs>');
	}

	protected makeGroesse(content: string[]) {
		content.push(...this.groeseSvg);
	}

	protected makeZusatzzeichen(content: string[]) {
		content.push(...this.zusatzzeichenSvg);
	}

	protected makeFahigkeit(content: string[]) {
		content.push('<g id="Fähigkeit">');
		content.push(...this.fahigkeitSvg);
		content.push('</g>');
	}

	protected makeGrundeigenschaft(content: string[]) {
		content.push('<g id="Grundeigenschaft">');
		content.push(...this.grundeigenschaftSvg);
		content.push('</g>');
	}

	protected makeOrganisation(content: string[]) {
		content.push('<g id="Organisation">');
		content.push(...this.organisationSvg);
		content.push('</g>');
	}

	make(): string {
		const content: string[] = [];
		const id = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
		content.push(`<svg viewBox="0 0 100 100" id="military-symbol-${id}" class="military-symbol" width="3cm" height="3cm" xmlns="http://www.w3.org/2000/svg">`);

		this.makeStyle(content, id);
		this.makeDefs(content, id);

		this.makeGroesse(content);

		content.push(`<svg x="5" y="20" width="90" height="60" viewBox="0 0 100 100" preserveAspectRatio="none">`);
		content.push(`<use href="#Grundzeichen-${id}" />`);

		content.push(`<svg id="middle" x="0" y="0" height="100" width="100" viewBox="0 0 100 100" preserveAspectRatio="non">`);
		this.makeFahigkeit(content);
		this.makeOrganisation(content);
		content.push(`</svg>`);

		content.push(`</svg>`);


		content.push('<g id="Zusatzzeichen" fill="black">');
		this.makeZusatzzeichen(content);
		content.push('</g>');

		content.push('</svg>');

		// TODO: Sanitize output. Danger of DOM XSS attacks
		return content.join('\n');
	}
}

export class UnitBuilder extends TacticalSignBuilder {
	constructor() {
		super('Einheit');
	}
}

export class VehicleBuilder extends TacticalSignBuilder {
	private transportartSvg: readonly string[] = [];
	private anhangerSvg: readonly string[] = [];

	constructor(type: GrundzeichenKey = 'Fahrzeug') {
		super(type);
	}

	transportart(art: TransportartKey) {
		this.transportartSvg = Transportart[art];
		return this;
	}

	anhanger(typ: AnhangerKey) {
		this.anhangerSvg = Anhanger[typ];
		return this;
	}

	protected makeZusatzzeichen(content: string[]) {
		super.makeZusatzzeichen(content);
		content.push(...this.transportartSvg);
		content.push(...this.anhangerSvg);
	}


}

export class AnhangerBuilder extends VehicleBuilder {
	constructor() {
		super('Anhanger');
	}
}

export class AircraftBuilder extends TacticalSignBuilder {
	constructor() {
		super('Luftfahrzeug');
	}
}

export class PersonBuilder extends TacticalSignBuilder {
}

export class BoatBuilder extends TacticalSignBuilder {
}

export class SiteBuilder extends TacticalSignBuilder {
	constructor() {
		super('Stelle');
	}
}