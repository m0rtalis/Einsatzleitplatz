import type { AreaInfo } from '$lib/utility/svg';

export const TrafficType = {
	strassenfahig: ({sign}: {sign: AreaInfo}) => [
		`<circle cx="${sign.topLeft.x + 10}" cy="${sign.bottomRight.y + 4}" r="4" fill="white" />`,
		`<circle cx="${sign.bottomRight.x - 10}" cy="${sign.bottomRight.y + 4}" r="4" fill="white" />`
	]
}