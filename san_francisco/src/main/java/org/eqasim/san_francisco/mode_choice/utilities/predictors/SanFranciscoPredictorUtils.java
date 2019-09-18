package org.eqasim.san_francisco.mode_choice.utilities.predictors;

import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;

public class SanFranciscoPredictorUtils {
	static public boolean hasSubscription(Person person) {
		Boolean hasSubscription = (Boolean) person.getAttributes().getAttribute("ptSubscription");
		return hasSubscription != null && hasSubscription;
	}
	
	static public boolean startsEndsinCity(DiscreteModeChoiceTrip trip) {
		boolean startInCity = (boolean) trip.getOriginActivity().getAttributes().getAttribute("city");
		boolean endInCity = (boolean) trip.getDestinationActivity().getAttributes().getAttribute("city");
		return startInCity & endInCity;
	}
}
