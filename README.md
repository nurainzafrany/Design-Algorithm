# Design-Algorithm

Problem statement:

The challenge is to design an efficient delivery system using drones to transport emergency supplies to flood-affected towns with physical access cut off. The goal is to optimize delivery plans considering drone limitations and town urgency.

Objectives:

-Model the affected region as a graph.

-Analyze various algorithm paradigms for route optimization.

-Develop an efficient Java-based solution.

-Prove the algorithm's correctness and analyze its performance.

Expected output:

Java program that outputs optimized delivery routes and visualization or textual output of town coverage, time, and energy use.

Problem scenario description:

A severe flood has hit a region in Southeast Asia, damaging infrastructure and cutting off several rural towns. Emergency supplies need to be delivered using drones, but drones have limited capacity and battery life. The goal is to determine the optimal set of delivery routes to maximize coverage while minimizing energy usage.

Why it is important?

-Helps in quick and efficient emergency response.

-Optimizes limited resources (drones, fuel, time).

-Supports critical decision-making to save lives.

Potential solutions:

Solution Paradigm
Strength
Weakness
Sorting
-Is useful in preprocessing, e.g. sorting the towns based on urgency, distance, or supply requirements.
-Fast algorithm, e.g. Merge Sort and Quick Sort have a fast algorithmic time of O(n log n).
-May be used in decision making with regard to delivery order.
-Does not do route planning and does not graph, it is based delivery optimization.
-Doesn’t consider limiting properties, like drone battery or range.
-COmplex spatial relations among the towns cannot be modeled.
Divide and Conquer (DAC)
-Divides jam-packed issues into smaller and solvable sub-regions.
-Enables paralleling of drone flight paths in various areas.
-Makes it possible to deal with large geographical areas.
-Sub-solution merging can generate non-optima global routes.
-Merging becomes complicated because of overlapping constraints such as battery life, range.
-Needs vigilance in zone allocation so as to be efficient.
Dynamic Programming (DP)
-Makes the best solutions in case of overlapping subproblems.
Is able to model tract battery life and battery range and deliveries.
-Applicable in the solution of complex problems such as word Traveling Salesman Problem (TSP).
-Large graphs like exponentials have high time and space complexity.
-Hard to scale to real time or large-scale emergencies.
-Difficult to apply as opposed to greedy or graph methods.
Greedy
-Easy and quick, appropriate during emergency cases.
-Derives satisfactorily good approximation solutions in time.
-COmpetent in situations where speed of reaction is an important concern.
-Nothing ensures global optimality.
Able to deliver poor local decisions, which impact the entire route.
-Can be inefficient with complex constraints.
Graph Algorithm
-Towns and paths (nodes and edges) are naturally modeled.
-Such well-known algorithms like Dijkstra, A*, MST, TSP aid in route optimization.
-Makes it easy to visualize, and periodically update things as conditions vary.
-The solution of some problems(e.g., TSP) is NP-hard, i.e. there exists no feasible solution to the problem on a large graph.
Have to do with scalability with heuristic or approximate solutions.
The more the towns and redistribution routes, the higher the complexity.











