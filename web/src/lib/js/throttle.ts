export const throttle = (fun: Function, throttleMs: number = 500) => {
	let isThrottled = false;
	return (...args: any[]) => {
		if (isThrottled) {
			return;
		}
		isThrottled = true;
		setTimeout(() => {
			fun(...args);
			isThrottled = false;
		}, throttleMs);
	};
};
