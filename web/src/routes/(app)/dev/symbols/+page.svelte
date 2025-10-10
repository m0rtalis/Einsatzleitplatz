<script lang="ts">
	// 	https://banjocode.com/post/svelte/client-side-library
	import { createTacticalSign, TacticalFormation, TacticalUnit } from '$lib/component/tacticalSymbol/TacticalSymbol';
	import TacticalSymbol from '$lib/component/TacticalSymbol.svelte';

	const unit = createTacticalSign({
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		unitName: 'SEG',
		capabilitiesKey: ['sanitaetsdienst'],
		shortname: 'TEL',
		sizeKey: TacticalUnit.Gruppe
	});

	const vehicle = createTacticalSign({
		baseSymbolKey: 'Fahrzeug',
		organisationKey: 'Rettungsdienst',
		unitName: 'SEG',
		capabilitiesKey: ['sanitaetsdienst'],
		shortname: 'TEL'
	});

	const def = {
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		unitName: 'SEG',
		capabilitiesKey: ['sanitaetsdienst'],
		shortname: 'TEL'
	};
	const unitList = [
		createTacticalSign(Object.assign(def, { organisationKey: 'Feuerwehr' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'THW' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'Rettungsdienst' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'Fuehrung' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'Polizei' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'Sonstige' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'Bundeswehr' } as any)),
		createTacticalSign(Object.assign(def, { organisationKey: 'Zivil' } as any))
	];

	const symbol11 = createTacticalSign({
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: []
	});

	const symbol13 = createTacticalSign({
		baseSymbolKey: 'Fahrzeug',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: []
	});

	const f11_mtf = createTacticalSign({
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['arztwesen'],
		unitName: 'MTF',
		sizeKey: TacticalFormation.Abteilung
	});

	const f12_mtfDekon = createTacticalSign({
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['arztwesen', 'dekon'],
		unitName: 'MTF',
		sizeKey: TacticalUnit.Zug
	});

	const f13_btd5000 = createTacticalSign({
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['betreuung', 'unterbringung_schlafen'],
		unitName: '5.000',
		sizeKey: TacticalFormation.Abteilung,
		complement: ['Logistik']
	});

	const f14_einsatzeinheit = createTacticalSign({
		baseSymbolKey: 'Einheit',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['sanitaetsdienst', 'betreuung'],
		sizeKey: TacticalUnit.Zug
	});

	/*
	const f15_sanzug_asb = new UnitBuilder()
		.organisation('Rescue', 'ASB')
		.fahigkeit('sanitatsdienst')
		.groese('Zug');

	const f16_sangruppe = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst')
		.groese('Gruppe');

	const f17_sangruppe_arzt = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('arztwesen')
		.groese('Gruppe');

	const f18_trapo = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['sanitatsdienst', 'transport'], '10')
		.groese('Gruppe');

	const f19_segsan = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'SEG')
		.groese('Gruppe');

	const f110_segrtd = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('rettungswesen', 'SEG')
		.groese('Gruppe');

	const f111_rtd1 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'RettD');

	const f111_rtd2 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('rettungswesen');

	const f112_manv1 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'ÜMANV-S')
		.groese('Gruppe');

	const f112_manv2 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['rettungswesen', 'arztwesen', 'transport'])
		.groese('Gruppe');

	const f113_bhp50 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['arztwesen', 'stelle_fest'], '50')
		.groese('Bereitschaft');

	const f114_evt = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'EVT')
		.groese('Trupp');

	const f115_arzttrupp1 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('arztwesen')
		.groese('Trupp');

	const f115_arzttrupp2 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['arztwesen', 'rettungswesen'])
		.groese('Trupp');

	const f116_drohnentrupp = new UnitBuilder()
		.organisation('Rescue')
		.grundeigenschaft('Drohne')
		.zusatzzeichen('Drehfluegler')
		.groese('Trupp');

	const f117_verpfl = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'verpflegung'], '250')
		.grundeigenschaft('Versorgung')
		.groese('Gruppe');

	const f118_soz = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('betreuung', { name: '100', specification: 'SOZ' })
		.groese('Gruppe');

	const f119_unterkunft = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'unterbringung_schlafen'], '120')
		.groese('Gruppe');

	const f120_betr = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('betreuung', { name: '100', specification: 'SEG' })
		.groese('Gruppe');

	const f121_betr = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('betrstelle', '500')
		.groese('Bereitschaft');

	const f122_trapo = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'transport'], '50')
		.groese('Zug');
*/

	const f21_ktw1 = createTacticalSign({
		baseSymbolKey: 'Fahrzeug',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['sanitaetsdienst'],
		unitName: 'KTW',
		trafficTypeKey: 'strassenfahig'
	});

	const f21_ktw2 = createTacticalSign({
		baseSymbolKey: 'Fahrzeug',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['sanitaetsdienst', 'transport'],
		trafficTypeKey: 'strassenfahig'
	});
	/*
	const f21_ktw1 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'KTW')
		.transportart('Strassenfahig');

	const f21_ktw2 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['sanitatsdienst', 'transport'])
		.transportart('Strassenfahig');

	const f22_nktw1 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'NKTW-B')
		.transportart('Strassenfahig');

	const f22_nktw2 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['sanitatsdienst', 'transport'], '2')
		.transportart('Strassenfahig');

	const f23_rtw1 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'RTW')
		.transportart('Strassenfahig');

	const f23_rtw2 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['rettungswesen', 'transport'])
		.transportart('Strassenfahig');

	const f24_nef1 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'NEF')
		.transportart('Strassenfahig');

	const f24_nef2 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('arztwesen')
		.transportart('Strassenfahig');

	const f25_naw1 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'NAW')
		.transportart('Strassenfahig');
*/
	const f25_naw2 = createTacticalSign({
		baseSymbolKey: 'Fahrzeug',
		organisationKey: 'Rettungsdienst',
		capabilitiesKey: ['arztwesen', 'rettungswesen', 'transport'],
		trafficTypeKey: 'strassenfahig'
	});
	/*
	const f25_naw2 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['rettungswesen', 'arztwesen', 'transport'])
		.transportart('Strassenfahig');

	const f26_rth = new AircraftBuilder()
		.organisation('Rescue')
		.fahigkeit('arztwesen', 'TODO');

	const f27_ith = new AircraftBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'TODO');

	const f28_gwsan50 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst', 'GW-SAN 50')
		.transportart('Strassenfahig');

	const f29_anhangerUHS = new AnhangerBuilder()
		.organisation('Rescue')
		.fahigkeit(['sanitatsdienst', 'stelle'])
		.anhanger("Anhanger")

	const f210_btkombi = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('betreuung', 'BTKombi')
		.transportart('Strassenfahig');

	const f211_btkombiAnlauf = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'anlaufstelle'], 'BTKombi')
		.transportart('Strassenfahig');

	const f212_gwbtr = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('betreuung', 'TODO')
		.transportart('Gelandefahig');

	const f213_btlkwkueche = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'zubereitung_verpflegung'], 'GwBT')
		.grundeigenschaft('Versorgung')
		.transportart('Strassenfahig');

	const f214_gwlog = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('betreuung', 'GwLog')
		.grundeigenschaft('Versorgung')
		.transportart('Strassenfahig');

	const f215_anhbtr = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit('betreuung', 'TODO')
		.transportart('Strassenfahig');

	const f216_bt40 = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'transport'], '40')
		.transportart('Strassenfahig');

	const f217_trinkwa = new VehicleBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'trinkwasser'], 'BtLKW')
		.grundeigenschaft('Versorgung')
		.transportart('Strassenfahig');

	const f31_pa = new SiteBuilder()
		.organisation('Rescue')
		.fahigkeit('sanitatsdienst');
*/
	const f31_pa = createTacticalSign({
		baseSymbolKey: "Stelle",
		organisationKey: "Rettungsdienst",
		capabilitiesKey: []
	})
</script>

<style>
    :global(svg) {
        margin: 2px
    }
</style>

<h1>Taktische Zeichen</h1>
{#await import('$lib/component/TacticalSymbol.svelte')}
	Loading...
{:then _ }
	<TacticalSymbol symbol={unit} />
	<TacticalSymbol symbol={vehicle} />

	<br />
	<br />
	{#each unitList as s}
		<TacticalSymbol symbol={s} />
	{/each}

	<h2>Grundelemente der Taktischen Zeichen</h2>
	<TacticalSymbol symbol={symbol11} />
	<TacticalSymbol symbol={symbol13} />

	<h2>F Sanitäts-, Rettungs-, Betreuungswesen</h2>
	<h3>F.1 Taktische Einheiten</h3>
	<TacticalSymbol symbol={f11_mtf} />
	<TacticalSymbol symbol={f12_mtfDekon} />
	<TacticalSymbol symbol={f13_btd5000} />
	<TacticalSymbol symbol={f14_einsatzeinheit} />
	<!--
		<TacticalSymbol symbol={f15_sanzug_asb} />
		<TacticalSymbol symbol={f16_sangruppe} />
		<TacticalSymbol symbol={f17_sangruppe_arzt} />
		<TacticalSymbol symbol={f18_trapo} />
		<TacticalSymbol symbol={f19_segsan} />
		<TacticalSymbol symbol={f110_segrtd} />
		<TacticalSymbol symbol={f111_rtd1} />
		<TacticalSymbol symbol={f111_rtd2} />
		<TacticalSymbol symbol={f112_manv1} />
		<TacticalSymbol symbol={f112_manv2} />
		<TacticalSymbol symbol={f113_bhp50} />
		<TacticalSymbol symbol={f114_evt} />
		<TacticalSymbol symbol={f115_arzttrupp1} />
		<TacticalSymbol symbol={f115_arzttrupp2} />
		<TacticalSymbol symbol={f116_drohnentrupp} />
		<TacticalSymbol symbol={f117_verpfl} />
		<TacticalSymbol symbol={f118_soz} />
		<TacticalSymbol symbol={f119_unterkunft} />
		<TacticalSymbol symbol={f120_betr} />
		<TacticalSymbol symbol={f121_betr} />
		<TacticalSymbol symbol={f122_trapo} />
-->
	<h3>F.2 Einsatzmittel</h3>
	<TacticalSymbol symbol={f21_ktw1} />
	<TacticalSymbol symbol={f21_ktw2} />
	<!--
		<TacticalSymbol symbol={f22_nktw1} />
		<TacticalSymbol symbol={f22_nktw2} />
		<TacticalSymbol symbol={f23_rtw1} />
		<TacticalSymbol symbol={f23_rtw2} />
		<TacticalSymbol symbol={f24_nef1} />
		<TacticalSymbol symbol={f24_nef2} />
		<TacticalSymbol symbol={f25_naw1} />
		-->
	<TacticalSymbol symbol={f25_naw2} />
	<!--
		<TacticalSymbol symbol={f26_rth} />
		<TacticalSymbol symbol={f27_ith} />
		<TacticalSymbol symbol={f28_gwsan50} />
		<TacticalSymbol symbol={f29_anhangerUHS} />
		<TacticalSymbol symbol={f210_btkombi} />
		<TacticalSymbol symbol={f211_btkombiAnlauf} />
		<TacticalSymbol symbol={f212_gwbtr} />
		<TacticalSymbol symbol={f213_btlkwkueche} />
		<TacticalSymbol symbol={f214_gwlog} />
		<TacticalSymbol symbol={f215_anhbtr} />
		<TacticalSymbol symbol={f216_bt40} />
		<TacticalSymbol symbol={f217_trinkwa} />
-->
		<h3>F.3 Plätze, Stellen und Einrichtungen</h3>
		<TacticalSymbol symbol={f31_pa} />
{/await}
