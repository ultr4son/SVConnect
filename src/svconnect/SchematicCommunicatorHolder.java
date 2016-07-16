package svconnect;

import ccre.frc.FRCImplementation;
import ccre.verifier.SetupPhase;

/**
 * Container for the currently used SchematicCommunicator. Ensures that the implementation being used is uniform across the entire application.
 * @author tthompso
 *
 */
public class SchematicCommunicatorHolder {
	static SchematicCommunicator impl;
	
    @SetupPhase
    public static SchematicCommunicator getImplementation() {
        if (impl == null) {
            throw new RuntimeException("No SchematicCommunicator implementation!");
        }
        return impl;
    }

    /**
     * @param implementation the implementation to set
     */
    @SetupPhase
    public static void setImplementation(SchematicCommunicator implementation) {
        impl = implementation;
    }

    private SchematicCommunicatorHolder() {
    }

}
