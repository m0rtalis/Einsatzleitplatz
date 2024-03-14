export const debounce = (fun: Function, bounceMs: number = 500) => {
	let timer: NodeJS.Timeout;
	return (...args: any[]) => {
		clearTimeout(timer);
		timer = setTimeout(() => {
			fun(...args);
		}, bounceMs)
	}
}
