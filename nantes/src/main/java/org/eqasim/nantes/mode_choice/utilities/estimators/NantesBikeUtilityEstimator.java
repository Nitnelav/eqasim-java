package org.eqasim.nantes.mode_choice.utilities.estimators;

import com.google.inject.Inject;
import org.eqasim.core.simulation.mode_choice.utilities.estimators.BikeUtilityEstimator;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.BikePredictor;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.PersonPredictor;
import org.eqasim.nantes.mode_choice.parameters.NantesModeParameters;
import org.eqasim.nantes.mode_choice.utilities.predictors.NantesSpatialPredictor;
import org.eqasim.nantes.mode_choice.utilities.variables.NantesSpatialVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import java.util.List;

public class NantesBikeUtilityEstimator extends BikeUtilityEstimator {
	private final NantesModeParameters parameters;
	private final NantesSpatialPredictor spatialPredictor;

	@Inject
	public NantesBikeUtilityEstimator(NantesModeParameters parameters, NantesSpatialPredictor spatialPredictor,
                                      PersonPredictor personPredictor, BikePredictor bikePredictor) {
		super(parameters, personPredictor, bikePredictor);

		this.parameters = parameters;
		this.spatialPredictor = spatialPredictor;
	}

	protected double estimateUrbanUtility(NantesSpatialVariables variables) {
		double utility = 0.0;

		if (variables.hasUrbanOrigin && variables.hasUrbanDestination) {
			utility += parameters.nantesBike.betaInsideUrbanArea;
		}

		return utility;
	}

	@Override
	public double estimateUtility(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		NantesSpatialVariables variables = spatialPredictor.predictVariables(person, trip, elements);

		double utility = 0.0;

		utility += super.estimateUtility(person, trip, elements);
		utility += estimateUrbanUtility(variables);

		return utility;
	}
}
