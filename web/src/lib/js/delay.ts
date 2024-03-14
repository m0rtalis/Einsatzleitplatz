import { debounce } from '$lib/js/debounce';

/**
 * Call the function after delayMs only if it was continuously called within timeoutMs.
 * Prevent calls to it until timeoutMs are over.
 *
 * @param fun Function to execute after delay
 * @param delayMs Delay in ms
 * @param timeoutMs Timeout after which the execution gets aborted if the function is not called
 */
export const delay = (fun: Function, delayMs: number = 1000, timeoutMs: number = 500) => {
	let timer: NodeJS.Timeout | undefined;
	if (timeoutMs > delayMs) {
		throw new Error(`TimeoutMs ${timeoutMs} must be smaller then delayMs ${delayMs} for delay function`);
	}
	console.log('Delay created');
	return (...args: any[]) => {
		if (!timer) {
			console.log('No timer, create');
			timer = setTimeout(() => {
				console.log('Execute');
				fun(...args);
				timer = undefined
			}, delayMs);
		}
		console.log('Debounce');
		debounce(() => {
			console.log("Clear Timeout")
			clearTimeout(timer);
		}, timeoutMs);
	};
};
