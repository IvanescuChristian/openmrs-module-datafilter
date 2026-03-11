/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.datafilter.impl;

import static org.openmrs.module.datafilter.DataFilterConstants.DISABLED;
import static org.openmrs.module.datafilter.DataFilterConstants.ENABLED;
import static org.openmrs.module.datafilter.DataFilterConstants.MODULE_ID;

//import java.util.HashSet; not used
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.module.datafilter.DataFilterConstants;

public final class ImplConstants {
	/**
	 * Helps centralize all data types and their categories
	 */
	public enum DataFilter {
		LOCATION_PATIENT("locationBased", "Patient", Category.LOCATION),
		LOCATION_VISIT("locationBased", "Visit", Category.LOCATION),
		LOCATION_ENCOUNTER("locationBased", "Encounter", Category.LOCATION),
		LOCATION_OBS("locationBased", "Obs", Category.LOCATION),
		LOCATION_USER("locationBased", "User", Category.LOCATION),
		LOCATION_PROVIDER("locationBased", "Provider", Category.LOCATION),
		LOCATION_DIAGNOSIS("locationBased", "Diagnosis", Category.LOCATION),
		LOCATION_CONDITION("locationBased", "Condition", Category.LOCATION),
		LOCATION_GENERIC(MODULE_ID + "_locationFilter", Category.LOCATION),
		PROG_USER("programBased", "User", Category.PROGRAM),
		PROG_PROVIDER("programBased", "Provider", Category.PROGRAM),
		ENC_PRIV_ENCOUNTER("encTypePrivBased", "Encounter", Category.ENC_TYPE_PRIV),
		ENC_PRIV_ENCOUNTER_TYPE("encTypePrivBased", "EncounterType", Category.ENC_TYPE_PRIV),
		ENC_PRIV_OBS("encTypePrivBased", "Obs", Category.ENC_TYPE_PRIV),
		ENC_PRIV_DIAGNOSIS("encTypePrivBased", "Diagnosis", Category.ENC_TYPE_PRIV),
		ENC_PRIV_CONDITION("encTypePrivBased", "Condition", Category.ENC_TYPE_PRIV);

		public enum Category {
			LOCATION, PROGRAM, ENC_TYPE_PRIV
		}
		private final String filterName;
		private final Category category;

		DataFilter(String prefix, String suffix, Category category) {
			this.filterName = MODULE_ID + "_" + prefix + suffix + "Filter";
			this.category = category;
		}

		DataFilter(String exactName, Category category) {
			this.filterName = exactName;
			this.category = category;
		}

		public String getFilterName() {
			return filterName;
		}
		public String generatePropertyAlias() {
			return filterName + DISABLED;
		}
		public Category getCategory() {
			return category;
		}
	}
	public static final Set<String> LOCATION_BASED_FILTER_NAMES = getFiltersByCategory(DataFilter.Category.LOCATION);
	public static final Set<String> PROGRAM_BASED_FILTER_NAMES = getFiltersByCategory(DataFilter.Category.PROGRAM);
	public static final Set<String> ENC_TYPE_VIEW_PRIV_FILTER_NAMES = getFiltersByCategory(DataFilter.Category.ENC_TYPE_PRIV);
	public static final Set<String> FILTER_NAMES = Stream.of(DataFilter.values())
			.map(DataFilter::getFilterName)
			.collect(Collectors.toSet());
	private static Set<String> getFiltersByCategory(DataFilter.Category category) {
		return Stream.of(DataFilter.values())
				.filter(f -> f.getCategory() == category)
				.map(DataFilter::getFilterName)
				.collect(Collectors.toSet());
	}
	public static final String PARAM_NAME_BASIS_IDS = "basisIds";
	public static final String PARAM_NAME_ROLES = "roles";
	public static final String PARAM_NAME_AUTHENTICATED_PERSON_ID = "authenticatedPersonId";
	public static final String PARAM_NAME_USER_PROG_ROLES = "userProgramRoles";
	public static final String PARAM_NAME_ALL_PROG_ROlES = "allProgramRoles";

	public static final String LOCATION_BASED_FULL_TEXT_FILTER_NAME_PATIENT = MODULE_ID + "_locationBasedPatientFullTextFilter";

	public final static String BASIS_IDS_PLACEHOLDER = ":" + PARAM_NAME_BASIS_IDS;

	public static final String PERSON_ID_QUERY = "SELECT DISTINCT entity_identifier FROM " + DataFilterConstants.MODULE_ID
			+ "_entity_basis_map WHERE entity_type = '" + Patient.class.getName() + "' AND basis_type = '"
			+ Location.class.getName() + "' AND basis_identifier IN (" + BASIS_IDS_PLACEHOLDER + ")";

	public static final String GP_LOCATION_BASED_FULL_TEXT_FILTER_PATIENT = LOCATION_BASED_FULL_TEXT_FILTER_NAME_PATIENT + DISABLED;
	public static final String GP_RUN_IN_STRICT_MODE = MODULE_ID + ".runInStrictMode";
	public static final String GP_PAT_LOC_INTERCEPTOR_ENABLED = MODULE_ID + ".patientLocationLinkingInterceptor" + ENABLED;

	public static final String ILLEGAL_RECORD_ACCESS_MESSAGE = "Illegal Record Access";
}

