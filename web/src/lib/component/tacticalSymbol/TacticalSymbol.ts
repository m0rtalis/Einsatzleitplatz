import {
	BaseSymbol,
	type BaseSymbolKey, type BaseSymbolType
} from '$lib/component/tacticalSymbol/BaseSymbolDefinition';
import {
	Capability,
	type CapabilityKey,
	type CapabilityType
} from '$lib/component/tacticalSymbol/CapabilityDefinition';
import {
	Organisation,
	type OrganisationKey,
	type OrganisationType
} from '$lib/component/tacticalSymbol/OrganisationDefinition';
import { areaInfo, LeafElement, type Point, RawElement, SVG } from '$lib/utility/svg';
import { assertExhaustive } from '$lib/js';
import { TrafficType } from '$lib/component/tacticalSymbol/VehicleDefinition';

// noinspection DuplicatedCode
const sizePoints = (sign: BaseSymbolType): { left: Point, middle: Point, right: Point } => {
	const size = sign.size;
	return {
		left: { x: size.width / 2 - size.width / 8, y: -7 },
		middle: { x: size.width / 2, y: -7 },
		right: { x: size.width / 2 + size.width / 8, y: -7 }
	};
};

export enum TacticalUnit {
	Trupp = 'Trupp',
	Staffel = 'Staffel',
	Gruppe = 'Gruppe',
	Zug = 'Zug'
}

const TacticalUnitSymbol = (point: Point) => ({ svg: SVG.circle(point, 3), height: 6 });

export enum TacticalFormation {
	Bereitschaft = 'Bereitschaft',
	Abteilung = 'Abteilung',
	Grossverband = 'Grossverband'
}

const TacticalFormationSymbol = (point: Point) => ({
	svg: SVG.rect({
		topLeft: { x: point.x, y: point.y - 10 },
		bottomRight: { x: point.x + 3, y: point.y }
	}), height: 12
});

const sizeSymbols = (sign: BaseSymbolType, size: TacticalUnit | TacticalFormation): {
	svg: LeafElement[],
	height: number
} => {
	const symbols: LeafElement[] = [];
	const symbol = Object.keys(TacticalUnit).includes(size) ? TacticalUnitSymbol : TacticalFormationSymbol;
	const points = sizePoints(sign);
	let height: number = 0;

	if (size === TacticalUnit.Trupp || size === TacticalUnit.Staffel || size === TacticalUnit.Zug || size === TacticalFormation.Bereitschaft || size === TacticalFormation.Grossverband) {
		const thisSymbol = symbol(points.middle);
		symbols.push(thisSymbol.svg);
		height = thisSymbol.height;
	}

	if (size === TacticalUnit.Gruppe || size === TacticalUnit.Zug || size === TacticalFormation.Abteilung || size === TacticalFormation.Grossverband) {
		const thisSymbol = symbol(points.left);
		symbols.push(thisSymbol.svg);
		symbols.push(symbol(points.right).svg);
		height = thisSymbol.height;
	}

	return { svg: symbols, height };
};

type CreateTacticalSignBase = {
	baseSymbolKey: BaseSymbolKey,
	organisationKey: OrganisationKey,
	capabilitiesKey: CapabilityKey[],
	sizeKey?: TacticalUnit | TacticalFormation,
	complement?: ('Fuehrung' | 'Logistik')[]
	organisationName?: string,
	unitName?: string,
	shortname?: string,
}

type CreateUnitTacticalSign = CreateTacticalSignBase & { baseSymbolKey: 'Einheit' }
type CreateVehicleTacticalSign = CreateTacticalSignBase & {
	baseSymbolKey: 'Fahrzeug',
	trafficTypeKey?: 'strassenfahig'
}
type CreateSiteTacticalSign = CreateTacticalSignBase & { baseSymbolKey: 'Stelle' }

type CreateTacticalSign = CreateUnitTacticalSign | CreateVehicleTacticalSign | CreateSiteTacticalSign
type Viewport = { x: number, y: number, width: number, height: number }

function addCapabilities(id: string, svg: SVG, capabilities: CapabilityType[]) {
	// ClipGroup
	const capabilityClipGroup = SVG.g()
		.addAttr('clip-path', `url(#clip-printable-base-symbol-${id})`);
	svg.addChild(capabilityClipGroup);

	// Capabilities
	const capabilitiesGroup = SVG.g('capability');
	capabilityClipGroup.addChild(capabilitiesGroup);
	for (const capability of capabilities) {
		if (capability.type === 'Full') {
			capabilitiesGroup.addChild(new RawElement(capability.svg));
		} else if (capability.type === 'Symbol') {
			const symbolSvg = new SVG();
			symbolSvg
				.addAttr('x', 30)
				.addAttr('y', 15)
				.addAttr('width', 30)
				.addAttr('height', 30)
				.addAttr('viewBox', '0 0 100 100')
				.addAttr('preserveAspectRatio', 'none')
				.addStyle('stroke-width', 8)
				.addStyle('fill', `transparent`)
				.addChild(new RawElement(capability.svg));
			capabilitiesGroup.addChild(symbolSvg);
		} else {
			assertExhaustive(capability.type);
		}
	}
}

function setupSvg(svg: SVG, baseSymbol: BaseSymbolType, organisation: OrganisationType): string {
	const id = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
	svg.addAttr('class', `tactical-symbol tactical-symbol-${id}`);
	svg.addAttr('height', `${baseSymbol.size.height}`);
	svg.addAttr('width', `${baseSymbol.size.width}`);
	svg.addAttr('preserveAspectRatio', 'none');
	svg.addStyle('fill', `${organisation.fillColor}`);
	svg.addStyle('stroke', `${organisation.borderAndTextColor}`);
	svg.addStyle('stroke-width', 2);

	svg.addStyleDocument(
		`
		.tactical-symbol-${id} text {
		fill: ${organisation.borderAndTextColor};
		stroke: ${organisation.fillColor};
		}
		`);
	return id;
}

function addSizeIndicator(svg: SVG, baseSymbol: BaseSymbolType, organisation: OrganisationType, viewport: Viewport, sizeKey?: TacticalUnit | TacticalFormation) {
	if (!sizeKey) {
		return;
	}
	const sizeSymbol = sizeSymbols(baseSymbol, sizeKey);
	const sizeGroup = SVG.g('size')
		.addStyle('fill', organisation.borderAndTextColor)
		.addChildren(sizeSymbol.svg);
	svg.addChild(sizeGroup);
	viewport.y = viewport.y - 4 - sizeSymbol.height;
	viewport.height = viewport.height + 4 + sizeSymbol.height;
}

export function createTacticalSign(input: CreateTacticalSign): SVG {
	const {
		baseSymbolKey,
		organisationKey,
		capabilitiesKey,
		organisationName,
		sizeKey,
		complement,
		unitName,
		shortname
	} = input;
	const baseSymbol = BaseSymbol[baseSymbolKey];
	const baseSymbolSvg = baseSymbol.svg;
	const organisation = Organisation[organisationKey];
	// Add a space of one unit on each side for the border
	const viewport: Viewport = { x: -1, y: -1, width: baseSymbol.size.width + 2, height: baseSymbol.size.height + 2 };
	const capabilities = capabilitiesKey.map(key => Capability[key]({
		sign: areaInfo({
			topLeft: { x: 0, y: 0 },
			bottomRight: { x: baseSymbol.size.width, y: baseSymbol.size.height }
		}), printable: areaInfo(baseSymbol.printableArea)
	}));
	const isLeader: boolean = !!complement && complement.includes('Fuehrung');
	const isLogistic = !!complement && complement.includes('Logistik');

	const svg = new SVG();
	const id = setupSvg(svg, baseSymbol, organisation);

	// Def
	const clipDef = SVG.rect(baseSymbol.printableArea);
	svg.addDef(SVG.clipPath(`clip-printable-base-symbol-${id}`).addChild(clipDef));
	svg.addDef(SVG.clipPath(`clip-base-symbol-${id}`).addChild(new RawElement(baseSymbolSvg)));

	// Base Symbol
	const baseSymbolGroup = SVG.g(`base-symbol-${id}`);
	svg.addChild(baseSymbolGroup);
	baseSymbolGroup.addChild(new RawElement(baseSymbolSvg));

	addCapabilities(id, svg, capabilities);

	const baseClipGroup = SVG.g().addAttr('clip-path', `url(#clip-base-symbol-${id})`);
	svg.addChild(baseClipGroup);
	if (isLeader) {
		baseClipGroup.addChild(
			SVG.rect({ topLeft: { x: 0, y: 0 }, bottomRight: { x: baseSymbol.size.width, y: 5 } })
				.addStyle('fill', 'black')
		);
	}

	if (isLogistic) {
		baseClipGroup.addChild(
			SVG.rect({
				topLeft: { x: 0, y: baseSymbol.size.height - 5 },
				bottomRight: { x: baseSymbol.size.width, y: 5 }
			})
				.addStyle('fill', 'black')
		);
	}

	if (organisationName !== undefined) {
		svg.addChild(SVG.text(organisationName)
			.addAttr('x', baseSymbol.organisationPoint.x)
			.addAttr('y', isLogistic ? baseSymbol.organisationPoint.y - 5 : baseSymbol.organisationPoint.y)
			.addAttr('text-anchor', 'end')
			.addAttr('id', 'organisation'));
	}

	if (unitName) {
		svg.addChild(SVG.text(unitName)
			.addAttr('x', baseSymbol.unitPoint.x)
			.addAttr('y', isLeader ? baseSymbol.unitPoint.y + 5 : baseSymbol.unitPoint.y)
			.addAttr('id', 'unit'));
	}

	if (shortname) {
		svg.addChild(SVG.text(shortname)
			.addAttr('x', baseSymbol.shortnamePoint.x)
			.addAttr('y', baseSymbol.shortnamePoint.y)
			.addAttr('text-anchor', 'middle')
			.addAttr('id', 'shortname'));
	}
	addSizeIndicator(svg, baseSymbol, organisation, viewport, sizeKey);

	if (baseSymbolKey === 'Fahrzeug' && input.trafficTypeKey) {
		const trafficTypeKey = input.trafficTypeKey;
		const trafficType = TrafficType[trafficTypeKey]({
			sign: areaInfo({
				topLeft: { x: 0, y: 0 },
				bottomRight: { x: baseSymbol.size.width, y: baseSymbol.size.height }
			})
		});
		svg.addChild(new RawElement(trafficType));
		viewport.height = viewport.height + 12

	}

	svg.addAttr('viewBox', `${viewport.x} ${viewport.y} ${viewport.width} ${viewport.height}`);

	return svg;
}
