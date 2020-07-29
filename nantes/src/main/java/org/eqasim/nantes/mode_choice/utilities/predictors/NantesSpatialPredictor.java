package org.eqasim.nantes.mode_choice.utilities.predictors;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import com.google.inject.Singleton;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.CachedVariablePredictor;
import org.eqasim.nantes.mode_choice.utilities.variables.NantesSpatialVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import java.util.List;

@Singleton
public class NantesSpatialPredictor extends CachedVariablePredictor<NantesSpatialVariables> {
	@Override
	protected NantesSpatialVariables predict(Person person, DiscreteModeChoiceTrip trip,
			List<? extends PlanElement> elements) {
		boolean hasUrbanOrigin = NantesPredictorUtils.isUrbanArea(trip.getOriginActivity());
		boolean hasUrbanDestination = NantesPredictorUtils.isUrbanArea(trip.getDestinationActivity());

		return new NantesSpatialVariables(hasUrbanOrigin, hasUrbanDestination);
	}
}
