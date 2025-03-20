/**
 * Handle the journal.
 * Documents entries in an accountable way.
 */
/*
 * One idea is to document *every* action in the journal including login of users,
 * any changes to resources etc. the other possibility is to create a separate record
 * service which does this.
 *
 * Right now I'm trending towards the latter option as this would generate a lot of entries and it
 * will be difficult to find sth otherwise. Additionally viewed from the standpoint of
 * separation of concerns the second option is also more favorable.
 * The DV 100 says explicitly:
 *
 * Das Einsatztagebuch ist ein Nachweis über die Tätigkeit der Einsatzleitung.
 * Im Einsatztagebuch ist der Einsatzablauf in zeitlicher Folge aufzuzeichnen.
 * Es sollen im Einsatztagebuch nicht nur
 * - die Ergebnisse der Lagefeststellung,
 * - die Befehle an die Einsatzkräfte und
 * - besondere Vorkommnisse und Erkenntnisse,
 * sondern erforderlichenfalls auch die Planung des Einsatzes, das heißt
 * - die Beurteilung und
 * - der Entschluss
 * festgehalten werden.
 * Die Dokumentation aus- und eingehender Meldungen kann im Einsatztagebuch
 * gegebenenfalls unterbleiben, sofern diese in der Eingangs- und Ausgangsnachweis
 * erfolgt.
 *
 * The con is a slight increase in complexity and duplication. Entries in the journal would be
 * 1:1 duplicated in the record although in the grand scheme of things this is probably miniscule.
 *
 * Either way it's something I'll leave open for later.
 */
package de.eisingerf.elp.journal;