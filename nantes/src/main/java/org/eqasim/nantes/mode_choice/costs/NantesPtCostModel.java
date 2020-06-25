package org.eqasim.nantes.mode_choice.costs;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.cost.CostModel;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.PredictorUtils;
import org.eqasim.nantes.mode_choice.utilities.predictors.NantesPersonPredictor;
import org.eqasim.nantes.mode_choice.utilities.variables.NantesPersonVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import com.google.inject.Inject;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;

public class NantesPtCostModel implements CostModel {
	private final NantesPersonPredictor personPredictor;

	@Inject
	public NantesPtCostModel(NantesPersonPredictor personPredictor) {
		this.personPredictor = personPredictor;
	}

	@Override
	public double calculateCost_MU(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		NantesPersonVariables personVariables = personPredictor.predictVariables(person, trip, elements);
		double euclideanDistance_km = PredictorUtils.calculateEuclideanDistance_km(trip);

		if (personVariables.hasSubscription) {
			return 0.0;
		}

		if (euclideanDistance_km < 3.0) {
			return 3.0;
		} else if (euclideanDistance_km < 5.0) {
			return 3.0;
		} else if (euclideanDistance_km < 10.0) {
			return 3.5;
		} else {
			return Math.ceil(euclideanDistance_km / 5.0) * 2.0;
		}
	}
}
