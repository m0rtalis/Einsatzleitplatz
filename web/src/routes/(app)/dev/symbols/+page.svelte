<script lang="ts">
	import MilitarySymbol, {
		AircraftBuilder,
		SiteBuilder,
		UnitBuilder,
		VehicleBuilder
	} from '$lib/component/MilitarySymbol.svelte';
	import { AnhangerBuilder } from '$lib/component/taktischezeichen/TacticalSignBuilder';

	// 	https://banjocode.com/post/svelte/client-side-library
	const a = new UnitBuilder()
		.organisation('Rescue', 'BRK')
		.fahigkeit('sanitatsdienst', 'SEG')
		.grundeigenschaft(['Fuhrung', 'Versorgung'])
		.groese('Zug');

	const f11_mtf = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit('arztwesen', 'MTF')
		.groese('Abteilung');

	const f12_mtfDekon = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['arztwesen', 'dekon'], 'MTF')
		.groese('Zug');

	const f13_btd5000 = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['betreuung', 'unterbringung_schlafen'], '5.000')
		.grundeigenschaft('Versorgung')
		.groese('Abteilung');

	const f14_einsatzeinheit = new UnitBuilder()
		.organisation('Rescue')
		.fahigkeit(['sanitatsdienst', 'betreuung'])
		.groese('Zug');

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

	const l = [new UnitBuilder().organisation('FireService', 'Fw'),
		new UnitBuilder().organisation('TechnicalRelieve', 'THW'),
		new UnitBuilder().organisation('Rescue', 'BRK'),
		new UnitBuilder().organisation('Command', 'BBK'),
		new UnitBuilder().organisation('Police', 'Landespolizei'),
		new UnitBuilder().organisation('Other', 'AUS'),
		new UnitBuilder().organisation('Army', 'KSK'),
		new UnitBuilder().organisation('Civilian', 'Caritas')];
</script>

<h1>Taktische Zeichen</h1>
{#await import('$lib/component/MilitarySymbol.svelte')}
	Loading...
{:then x }
	<MilitarySymbol symbol={a} />
	{#each l as b}
		<MilitarySymbol symbol={b} />
	{/each}

	<h2>F Sanitäts-, Rettungs-, Betreuungswesen</h2>
	<h3>F.1 Taktische Einheiten</h3>
	<MilitarySymbol symbol={f11_mtf} />
	<MilitarySymbol symbol={f12_mtfDekon} />
	<MilitarySymbol symbol={f13_btd5000} />
	<MilitarySymbol symbol={f14_einsatzeinheit} />
	<MilitarySymbol symbol={f15_sanzug_asb} />
	<MilitarySymbol symbol={f16_sangruppe} />
	<MilitarySymbol symbol={f17_sangruppe_arzt} />
	<MilitarySymbol symbol={f18_trapo} />
	<MilitarySymbol symbol={f19_segsan} />
	<MilitarySymbol symbol={f110_segrtd} />
	<MilitarySymbol symbol={f111_rtd1} />
	<MilitarySymbol symbol={f111_rtd2} />
	<MilitarySymbol symbol={f112_manv1} />
	<MilitarySymbol symbol={f112_manv2} />
	<MilitarySymbol symbol={f113_bhp50} />
	<MilitarySymbol symbol={f114_evt} />
	<MilitarySymbol symbol={f115_arzttrupp1} />
	<MilitarySymbol symbol={f115_arzttrupp2} />
	<MilitarySymbol symbol={f116_drohnentrupp} />
	<MilitarySymbol symbol={f117_verpfl} />
	<MilitarySymbol symbol={f118_soz} />
	<MilitarySymbol symbol={f119_unterkunft} />
	<MilitarySymbol symbol={f120_betr} />
	<MilitarySymbol symbol={f121_betr} />
	<MilitarySymbol symbol={f122_trapo} />

	<h3>F.2 Einsatzmittel</h3>
	<MilitarySymbol symbol={f21_ktw1} />
	<MilitarySymbol symbol={f21_ktw2} />
	<MilitarySymbol symbol={f22_nktw1} />
	<MilitarySymbol symbol={f22_nktw2} />
	<MilitarySymbol symbol={f23_rtw1} />
	<MilitarySymbol symbol={f23_rtw2} />
	<MilitarySymbol symbol={f24_nef1} />
	<MilitarySymbol symbol={f24_nef2} />
	<MilitarySymbol symbol={f25_naw1} />
	<MilitarySymbol symbol={f25_naw2} />
	<MilitarySymbol symbol={f26_rth} />
	<MilitarySymbol symbol={f27_ith} />
	<MilitarySymbol symbol={f28_gwsan50} />
	<MilitarySymbol symbol={f29_anhangerUHS} />
	<MilitarySymbol symbol={f210_btkombi} />
	<MilitarySymbol symbol={f211_btkombiAnlauf} />
	<MilitarySymbol symbol={f212_gwbtr} />
	<MilitarySymbol symbol={f213_btlkwkueche} />
	<MilitarySymbol symbol={f214_gwlog} />
	<MilitarySymbol symbol={f215_anhbtr} />
	<MilitarySymbol symbol={f216_bt40} />
	<MilitarySymbol symbol={f217_trinkwa} />

	<h3>F.3 Plätze, Stellen und Einrichtungen</h3>
	<MilitarySymbol symbol={f31_pa} />
{/await}
