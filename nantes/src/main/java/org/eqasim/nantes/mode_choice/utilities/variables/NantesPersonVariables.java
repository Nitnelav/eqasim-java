package org.eqasim.nantes.mode_choice.utilities.variables;

import org.eqasim.core.simulation.mode_choice.utilities.variables.BaseVariables;

public class NantesPersonVariables implements BaseVariables {
	public final boolean hasSubscription;

	public NantesPersonVariables(boolean hasSubscription) {
		this.hasSubscription = hasSubscription;
	}
}
