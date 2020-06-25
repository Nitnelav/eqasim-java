package org.eqasim.nantes.mode_choice.parameters;

import org.eqasim.core.simulation.mode_choice.ParameterDefinition;

public class NantesCostParameters implements ParameterDefinition {
	public double carCost_EUR_km = 0.0;

	public static NantesCostParameters buildDefault() {
		NantesCostParameters parameters = new NantesCostParameters();

		parameters.carCost_EUR_km = 0.2;

		return parameters;
	}
}
