package org.eqasim.nantes.mode_choice;

import java.io.File;
import java.io.IOException;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.mode_choice.AbstractEqasimExtension;
import org.eqasim.core.simulation.mode_choice.ParameterDefinition;
import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;
import org.eqasim.nantes.mode_choice.costs.NantesCarCostModel;
import org.eqasim.nantes.mode_choice.costs.NantesPtCostModel;
import org.eqasim.nantes.mode_choice.parameters.NantesCostParameters;
import org.eqasim.nantes.mode_choice.parameters.NantesModeParameters;
import org.eqasim.nantes.mode_choice.utilities.estimators.NantesBikeUtilityEstimator;
import org.eqasim.nantes.mode_choice.utilities.estimators.NantesCarUtilityEstimator;
import org.eqasim.nantes.mode_choice.utilities.predictors.NantesPersonPredictor;
import org.eqasim.nantes.mode_choice.utilities.predictors.NantesSpatialPredictor;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.CommandLine.ConfigurationException;

import com.google.inject.Provides;
import com.google.inject.Singleton;

public class NantesModeChoiceModule extends AbstractEqasimExtension {
	private final CommandLine commandLine;

	public static final String MODE_AVAILABILITY_NAME = "NantesModeAvailability";

	public static final String CAR_COST_MODEL_NAME = "NantesCarCostModel";
	public static final String PT_COST_MODEL_NAME = "NantesPtCostModel";

	public static final String CAR_ESTIMATOR_NAME = "NantesCarUtilityEstimator";
	public static final String BIKE_ESTIMATOR_NAME = "NantesBikeUtilityEstimator";

	public NantesModeChoiceModule(CommandLine commandLine) {
		this.commandLine = commandLine;
	}

	@Override
	protected void installEqasimExtension() {
		bindModeAvailability(MODE_AVAILABILITY_NAME).to(NantesModeAvailability.class);

		bind(NantesPersonPredictor.class);

		bindCostModel(CAR_COST_MODEL_NAME).to(NantesCarCostModel.class);
		bindCostModel(PT_COST_MODEL_NAME).to(NantesPtCostModel.class);

		bindUtilityEstimator(CAR_ESTIMATOR_NAME).to(NantesCarUtilityEstimator.class);
		bindUtilityEstimator(BIKE_ESTIMATOR_NAME).to(NantesBikeUtilityEstimator.class);
		bind(NantesSpatialPredictor.class);

		bind(ModeParameters.class).to(NantesModeParameters.class);
	}

	@Provides
	@Singleton
	public NantesModeParameters provideModeChoiceParameters(EqasimConfigGroup config)
			throws IOException, ConfigurationException {
		NantesModeParameters parameters = NantesModeParameters.buildDefault();

		if (config.getModeParametersPath() != null) {
			ParameterDefinition.applyFile(new File(config.getModeParametersPath()), parameters);
		}

		ParameterDefinition.applyCommandLine("mode-choice-parameter", commandLine, parameters);
		return parameters;
	}

	@Provides
	@Singleton
	public NantesCostParameters provideCostParameters(EqasimConfigGroup config) {
		NantesCostParameters parameters = NantesCostParameters.buildDefault();

		if (config.getCostParametersPath() != null) {
			ParameterDefinition.applyFile(new File(config.getCostParametersPath()), parameters);
		}

		ParameterDefinition.applyCommandLine("cost-parameter", commandLine, parameters);
		return parameters;
	}
}
