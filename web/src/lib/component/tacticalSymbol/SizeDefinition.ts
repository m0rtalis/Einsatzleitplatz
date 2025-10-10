import type { BaseSymbolType } from '$lib/component/tacticalSymbol/BaseSymbolDefinition';
import type { Point } from '$lib/utility/svg';

const sizePoints = (sign: BaseSymbolType): { left: Point, middle: Point, right: Point } => {
	const size = sign.size;
	return {
		left: { x: size.width / 2 - size.width / 5, y: -7 },
		middle: { x: size.width / 2, y: -7 },
		right: { x: size.width / 2 + size.width / 5 , y: -7}
	};
};

// TODO: Only define the symbol, calculate the rest in Builder
export const Size = (sign: BaseSymbolType) => {
	const points = sizePoints(sign);
	return {
		Trupp: [
			`<circle cx="${points.middle.x}" cy="${points.middle.y}" r="3"/>`
		],
		Gruppe: [
			`<circle cx="${points.left.x}" cy="${points.left.y}" r="3"/>`,
			`<circle cx="${points.right.x}" cy="${points.right.y}" r="3"/>`
		],
		Zug: [
			`<circle cx="${points.left.x}" cy="${points.left.y}" r="3"/>`,
			`<circle cx="${points.middle.x}" cy="${points.middle.y}" r="3"/>`,
			`<circle cx="${points.right.x}" cy="${points.right.y}" r="3"/>`
		],
	} satisfies Record<string, string[]>;
};
export type SizeKey = keyof ReturnType<typeof Size>
