import ec.EvolutionState;
import ec.Individual;
import ec.Subpopulation;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import func.StringData;

import java.io.*;


public class SampleProblem extends GPProblem implements SimpleProblemForm {

    public void evaluate(final EvolutionState state,
                         final Individual ind,
                         final int subpopulation,
                         final int threadnum)
    {
        if (ind.evaluated) return;

        //TODO remove this...
        Subpopulation p = state.population.subpops.get(0);
        if (p.initialSize != p.individuals.size()) {
            state.output.fatal("someone got lost!!! (you might want to comment `breedthreads = auto` in the params file)");
        }

        StringData input = (StringData)(this.input);

        // make sure that we start empty
        input.str = null;

        if (!(ind instanceof GPIndividual))
            state.output.fatal("Whoa!  It's not a GPIndividual!!!",null);

        ((GPIndividual)ind).trees[0].child.eval(state, threadnum, input, stack, (GPIndividual)ind, this);
        state.output.println("----------------", 0);
        state.output.println(input.str, 0);

        int runResult = run(input.str);

        // runResult's scale is StandardizedFitness: 0 is best, infinity is worst
        // Koza fitness: 0 is worst, 1 is best
        KozaFitness f = ((KozaFitness)ind.fitness);
        f.setStandardizedFitness(state, runResult);
        f.printFitnessForHumans(state, 0);

        ind.evaluated = true;
    }

    private int run(String s) {
        try {
            String result = execCmd("python -c \"" + s + "\"");

            // the details of fitness calculation here aren't really important...
            // ... the important thing here is that we analyze 'result' and decide what's the fitness
            String lines[] = result.split("\\r?\\n");
            int numOfHelloWorlds = 0;
            final int expectedHelloWorlds = 6;
            int fitness = expectedHelloWorlds * 10;
            for (String line : lines) {
                if (line.equals("hello world")) {
                    if (numOfHelloWorlds < expectedHelloWorlds) {
                        fitness -= 10;
                        numOfHelloWorlds++;
                    } else
                        fitness += 3;
                } else
                    fitness += 1;
            }
            return fitness;
        } catch (IOException e) {
            e.printStackTrace();
            // if it fails to run - it's very bad
            return 1000000;
        }
    }

    // taken from https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program
    public static String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    // not crucial. called at end, for stats
    public void describe(
        final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum,
        final int log)
    {
        StringData input = (StringData)(this.input);
        ((GPIndividual)ind).trees[0].child.eval(state, threadnum, input, stack, (GPIndividual)ind, this);
        state.output.println("\n\nBest Individual's code\n======================", log);
        state.output.println(((StringData) input).str, log);
    }


}
