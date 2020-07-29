package org.eqasim.nantes.mode_choice.utilities.variables;

import org.eqasim.core.simulation.mode_choice.utilities.variables.BaseVariables;

public class NantesSpatialVariables implements BaseVariables {
	public final boolean hasUrbanOrigin;
	public final boolean hasUrbanDestination;

	public NantesSpatialVariables(boolean hasUrbanOrigin, boolean hasUrbanDestination) {
		this.hasUrbanOrigin = hasUrbanOrigin;
		this.hasUrbanDestination = hasUrbanDestination;
	}
}
