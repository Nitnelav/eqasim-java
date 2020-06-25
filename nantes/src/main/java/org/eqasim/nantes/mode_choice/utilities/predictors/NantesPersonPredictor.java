package org.eqasim.nantes.mode_choice.utilities.predictors;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.utilities.predictors.CachedVariablePredictor;
import org.eqasim.nantes.mode_choice.utilities.variables.NantesPersonVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;

public class NantesPersonPredictor extends CachedVariablePredictor<NantesPersonVariables> {
	@Override
	protected NantesPersonVariables predict(Person person, DiscreteModeChoiceTrip trip,
			List<? extends PlanElement> elements) {
		boolean hasSubscription = NantesPredictorUtils.hasSubscription(person);
		return new NantesPersonVariables(hasSubscription);
	}
}
