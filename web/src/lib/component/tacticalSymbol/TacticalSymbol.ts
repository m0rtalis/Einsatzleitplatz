import {
	BaseSymbol,
	type BaseSymbolKey
} from '$lib/component/tacticalSymbol/BaseSymbolDefinition';
import { Capability, type CapabilityKey } from '$lib/component/tacticalSymbol/CapabilityDefinition';
import { Organisation, type OrganisationKey } from '$lib/component/tacticalSymbol/OrganisationDefinition';
import { RawElement, SVG } from '$lib/utility/svg';

export function createTacticalSign({
									   baseSymbolKey,
									   organisationKey,
									   capabilitiesKey,
									   organisationName,
									   unitName,
									   shortname
								   }: {
	baseSymbolKey: BaseSymbolKey,
	organisationKey: OrganisationKey,
	capabilitiesKey: CapabilityKey[],
	organisationName?: string
	unitName?: string,
	shortname?: string,
}): SVG {
	const baseSymbol = BaseSymbol[baseSymbolKey];
	const baseSymbolSvg = baseSymbol.svg;
	const organisation = Organisation[organisationKey];
	const capabilities = capabilitiesKey.map(key => Capability[key](baseSymbol));

	const svg = new SVG();
	const id = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
	svg.addAttr('class', `tactical-symbol tactical-symbol-${id}`);
	svg.addAttr('height', `${baseSymbol.size.height}`);
	svg.addAttr('width', `${baseSymbol.size.width}`);
	// Add a space of one unit on each side for the border
	svg.addAttr('viewBox', `-1 -1 ${baseSymbol.size.width + 2} ${baseSymbol.size.height + 2}`);
	svg.addAttr('preserveAspectRatio', 'none');
	svg.addStyle('fill', `${organisation.fillColor}`);
	svg.addStyle('stroke', `${organisation.borderAndTextColor}`);

	svg.addStyleDocument(
		`
		.tactical-symbol-${id} text {
		fill: ${organisation.borderAndTextColor};
		stroke: ${organisation.fillColor};
		}
		`);

	svg.addDef(SVG.g('base-symbol').addChild(new RawElement(baseSymbolSvg)));
	svg.addDef(SVG.clipPath('clip-base-symbol').addChild(new RawElement(baseSymbolSvg)));
	svg.addChild(SVG.use('#base-symbol'));
	const capabilitiesGroup = svg.addChild(SVG.g()
		.addAttr('clip-path', 'url(#clip-base-symbol)')
		.addChild(SVG.g('capability')));

	for (const capability of capabilities) {
		capabilitiesGroup.addChild(new RawElement(capability));
	}

	svg.addChild(SVG.text(organisationName ?? organisation.name)
		.addAttr('x', baseSymbol.organisationPoint.x)
		.addAttr('y', baseSymbol.organisationPoint.y)
		.addAttr('text-anchor', 'end')
		.addAttr('id', 'organisation'));

	if (unitName) {
		svg.addChild(SVG.text(unitName)
			.addAttr('x', baseSymbol.unitPoint.x)
			.addAttr('y', baseSymbol.unitPoint.y)
			.addAttr('id', 'unit'));
	}

	if (shortname) {
		svg.addChild(SVG.text(shortname)
			.addAttr('x', baseSymbol.shortnamePoint.x)
			.addAttr('y', baseSymbol.shortnamePoint.y)
			.addAttr('text-anchor', 'middle')
			.addAttr('id', 'shortname'));
	}

	return svg;
}
