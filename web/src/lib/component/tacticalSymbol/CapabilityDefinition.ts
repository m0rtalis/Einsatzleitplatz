import type { AreaInfo } from '$lib/utility/svg';

export type CapabilityType = { svg: string[], type: 'Full' | 'Symbol' };
type CapabilityInput = { sign: AreaInfo, printable: AreaInfo };

const Sanitatswesen = {
	sanitaetsdienst: ({ sign: { topLeft, bottomRight, midPoint } }: CapabilityInput) =>
		({
			svg: [
				`<line x1="${topLeft.x}" y1="${midPoint.y}" x2="${bottomRight.x}" y2="${midPoint.y}"/>`,
				`<line x1="${midPoint.x}" y1="${topLeft.x}" x2="${midPoint.x}" y2="${bottomRight.y}"/>`
			], type: 'Full'
		}),
	arztwesen: ({ sign: { topLeft, bottomRight, midPoint, height } }: CapabilityInput) =>
		({
			svg: [
				`<line x1="${topLeft.x}" y1="${midPoint.y}" x2="${bottomRight.x}" y2="${midPoint.y}"/>`,
				`<line x1="${midPoint.x}" y1="${topLeft.x}" x2="${midPoint.x}" y2="${bottomRight.y}"/>`,
				`<line x1="${midPoint.x - 10}" y1="${midPoint.y + height / 3.5}" x2="${midPoint.x + 10}" y2="${midPoint.y + height / 3.5}"/>`
			], type: 'Full'
		}),
	rettungswesen: ({sign: {topLeft, bottomRight, midPoint, width}}) => ({
		svg: [
			`<line x1="${topLeft.x}" y1="${midPoint.y}" x2="${bottomRight.x}" y2="${midPoint.y}"/>`,
			`<line x1="${midPoint.x}" y1="${topLeft.x}" x2="${midPoint.x}" y2="${bottomRight.y}"/>`,
			`<line x1="${midPoint.x + width / 4}" y1="${midPoint.y - 10}" x2="${midPoint.x + width / 4}" y2="${midPoint.y + 10}"/>`
		], type: 'Full'
	}),
	transport: () => ({
		svg: [
			'<circle cx="50" cy="50" r="40" fill="none"/>',
			'<path d="M 50,10 v 80 m -40,-40 h 80 m -9,-30 l -61,61 m 0,-61 l 61,61" />'
		], type: 'Symbol'
	})
} as const satisfies Record<string, (_: CapabilityInput) => CapabilityType>;

const Betreuungswesen = {
	betreuung: ({ printable: { topLeft, bottomRight } }: CapabilityInput) => ({
		svg: [
			`<line x1="${topLeft.x}" y1="${bottomRight.y}" x2="${bottomRight.x / 2}" y2="${topLeft.y}"/>`,
			`<line x1="${bottomRight.x / 2}" y1="${topLeft.y}" x2="${bottomRight.x}" y2="${bottomRight.y}"/>`
		], type: 'Full'
	}),
	unterbringung_schlafen: () => ({
		svg: [
			'<line x1="2" y1="30" x2="2" y2="90" />', // left leg
			'<line x1="98" y1="30" x2="98" y2="90" />', // frame
			'<line x1="2" y1="70" x2="100" y2="70" />', // right leg
			'<path d="M 0,80 a 11,9 1,0,1 100,0"/>' // curve
		], type: 'Symbol'
	})
} as const satisfies Record<string, (_: CapabilityInput) => CapabilityType>;

const Sonstiges = {
	dekon: () => ({
		svg: [
			'<line x1="30" y1="20" x2="80" y2="100" />',
			'<line x1="70" y1="20" x2="20" y2="100" />',
			'<circle cx="20" cy="20" r="10" fill="black" />',
			'<circle cx="80" cy="20" r="10" fill="black" />'
		], type: 'Symbol'
	})
} as const satisfies Record<string, (_: CapabilityInput) => CapabilityType>;
export const Capability = { ...Sanitatswesen, ...Betreuungswesen, ...Sonstiges } as const satisfies Record<string, (_: CapabilityInput) => CapabilityType>;
export type CapabilityKey = keyof typeof Capability;
