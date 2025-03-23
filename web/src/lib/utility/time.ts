export interface TacticalTimeOptions {
	/**
	 * full: 31221756Bmar24
	 * long: 312217Bmar24
	 * short: 212217
	 */
	form: 'full' | 'long' | 'short';
	includeTimezone: boolean;
	useJTimezone: boolean;
}

const someDate = new Date();

const getTimezone = (date: Date, useJTimezone: boolean): string => {
	// https://archives.nato.int/uniform-time-indication
	if (useJTimezone && date.getTimezoneOffset() === someDate.getTimezoneOffset()) {
		return 'J';
	}

	const fullOffset = Math.trunc(date.getTimezoneOffset() / 60);
	let letter;
	if (fullOffset === 0) {
		letter = 'Z';
	} else if (fullOffset === -13) {
		letter = 'N';
	} else if (fullOffset > 0) {
		// A: 65
		letter = String.fromCharCode(65 + Math.max(fullOffset, 12) - 1);
	} else if (fullOffset < 0) {
		// N: 78
		letter = String.fromCharCode(78 + Math.max(Math.abs(fullOffset), 12) - 1);
	}
	let minuteOffset = date.getTimezoneOffset() % 60;
	let symbol = '';
	if (minuteOffset !== 0) {
		// No real information right now how to handle the different offsets
		symbol = '*';
	}
	return letter + symbol;
};

export const toTacticalTime = (date: Date, options: TacticalTimeOptions): string => {
	// Oh boy: https://de.wikipedia.org/wiki/Datum/Zeit-Gruppe
	// It's actually called date-time-group
	const format = new Intl.NumberFormat(undefined, { minimumIntegerDigits: 2 });
	const day: string = format.format(date.getDate());
	const hours: string = format.format(date.getHours());
	const minutes: string = format.format(date.getMinutes());
	const seconds: string = format.format(date.getSeconds());
	const timezone: string = options.includeTimezone ? getTimezone(date, options.useJTimezone) : '';
	const month: string = format.format(date.getMonth() + 1);
	const year: string = format.format(date.getFullYear() % 100);

	let timeString;
	switch (options.form) {
		case 'full':
			timeString = day + hours + minutes + seconds + timezone + month + year;
			break;
		case 'long':
			timeString = day + hours + minutes + timezone + month + year;
			break;
		case 'short':
			timeString = day + hours + minutes;
			break;
	}

	return timeString;
};
