package org.eqasim.nantes.mode_choice.utilities.estimators;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import com.google.inject.Inject;
import org.eqasim.core.simulation.mode_choice.utilities.estimators.CarUtilityEstimator;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.CarPredictor;
import org.eqasim.nantes.mode_choice.parameters.NantesModeParameters;
import org.eqasim.nantes.mode_choice.utilities.predictors.NantesSpatialPredictor;
import org.eqasim.nantes.mode_choice.utilities.variables.NantesSpatialVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import java.util.List;

public class NantesCarUtilityEstimator extends CarUtilityEstimator {
	private final NantesModeParameters parameters;
	private final NantesSpatialPredictor spatialPredictor;

	@Inject
	public NantesCarUtilityEstimator(NantesModeParameters parameters, NantesSpatialPredictor spatialPredictor,
                                     CarPredictor carPredictor) {
		super(parameters, carPredictor);

		this.parameters = parameters;
		this.spatialPredictor = spatialPredictor;
	}

	protected double estimateUrbanUtility(NantesSpatialVariables variables) {
		double utility = 0.0;

		if (variables.hasUrbanOrigin && variables.hasUrbanDestination) {
			utility += parameters.nantesCar.betaInsideUrbanArea;
		}

		if (variables.hasUrbanOrigin || variables.hasUrbanDestination) {
			utility += parameters.nantesCar.betaCrossingUrbanArea;
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
