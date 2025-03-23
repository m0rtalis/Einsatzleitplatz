export const isClipped = (
	element: HTMLElement,
): {
	top: boolean;
	right: boolean;
	bottom: boolean;
	left: boolean;
} => {
	const boundingBox = element.getBoundingClientRect();
	const winHeight = window.innerHeight;
	const winWidth = window.innerWidth;

	return {
		top: boundingBox.top < 0,
		right: boundingBox.right > winWidth,
		bottom: boundingBox.bottom > winHeight,
		left: boundingBox.left < 0,
	};
};
