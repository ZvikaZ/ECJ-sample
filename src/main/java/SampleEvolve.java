import ec.EvolutionState;

public class SampleEvolve {
    public static void main(String[] args) {
        String[] evolveArgs = {"-file", getResourceFileName("sample.params")};
        evolve(evolveArgs);
    }


    private static String getResourceFileName(String resource) {
        ClassLoader classLoader = SampleEvolve.class.getClassLoader();
        return classLoader.getResource(resource).getPath();
    }


    // based on ec.Evolve - has 'simple main' in comments:
    private static void evolve(String[] args) {
        EvolutionState state = ec.Evolve.possiblyRestoreFromCheckpoint(args);
        if (state != null)  // loaded from checkpoint
            state.run(EvolutionState.C_STARTED_FROM_CHECKPOINT);
        else {
            state = ec.Evolve.initialize(ec.Evolve.loadParameterDatabase(args), 0);
            state.run(EvolutionState.C_STARTED_FRESH);
        }
        ec.Evolve.cleanup(state);
    }
}