package day12

import readInput
import java.util.*

private class UndirectedGraph {
    // Graph representation  - Adjacency list
    private val graph = hashMapOf<String, MutableList<String>>()

    private fun addVertex(vertex: String) {
        graph[vertex] = LinkedList<String>()
    }

    fun addEdge(source: String, destination: String) {
        if (!graph.containsKey(source)) addVertex(source)
        if (destination != "end") { // end should not have out-going edges
            if (!graph.containsKey(destination)) addVertex(destination)
        }
        graph[source]?.add(destination)
        if (source != "start") { // start should should have only outgoing edges
            graph[destination]?.add(source)
        }
    }

    fun get(key: String): List<String> = graph[key]!!

}

fun main() {

    // All paths using BFS from source to destination
    fun getTotalPathUsingBFS(source: String, destination: String, graph: UndirectedGraph): Int {
        val queue: Queue<List<String>> = LinkedList()
        val paths: MutableList<List<String>> = mutableListOf()

        val adjacentVertices = graph.get(source)
        for (vertex in adjacentVertices) {
            queue.add(listOf(source, vertex))
        }

        while (queue.isNotEmpty()) {
            val frontElement = queue.remove()
            graph.get(frontElement.last()).forEach { vertex ->
                if (vertex == destination) {
                    paths.add(mutableListOf<String>().also {
                        it.addAll(frontElement)
                        it.add(vertex)
                    })
                } else {
                    if (!(vertex[0].isLowerCase() && frontElement.contains(vertex))) {
                        queue.add(mutableListOf<String>().also {
                            it.addAll(frontElement)
                            it.add(vertex)
                        })
                    }
                }
            }
        }

        return paths.size
    }

    // All paths using BFS from source to destination
    fun getTotalPathUsingBFSPart2(source: String, destination: String, graph: UndirectedGraph): Int {
        val queue: Queue<List<String>> = LinkedList()
        val paths: MutableList<List<String>> = mutableListOf()

        val adjacentVertices = graph.get(source)
        for (vertex in adjacentVertices) {
            queue.add(listOf(source, vertex))
        }

        val addInPath: (List<String>, String) -> Unit = { frontElement, vertex ->
            paths.add(mutableListOf<String>().also {
                it.addAll(frontElement)
                it.add(vertex)
            })
        }

        val addInQueue: (List<String>, String) -> Unit = { frontElement, vertex ->
            queue.add(mutableListOf<String>().also {
                it.addAll(frontElement)
                it.add(vertex)
            })
        }

        while (queue.isNotEmpty()) {
            val frontElement = queue.remove()
            graph.get(frontElement.last()).forEach { vertex ->
                if (vertex == destination) {
                    addInPath(frontElement, vertex)
                } else {
                    if (vertex[0].isLowerCase()) {
                        if (!frontElement.contains(vertex)) {
                            addInQueue(frontElement, vertex)
                        } else {
                            frontElement.filter { it[0].isLowerCase() }
                                .let { list ->
                                    val (matching, others) = list.partition { it == vertex }
                                    if (matching.count() == 1 &&
                                        others.groupingBy { it }.eachCount().filter { it.value > 1 }.count() == 0
                                    ) {
                                        addInQueue(frontElement, vertex)
                                    }
                                }
                        }
                    } else {
                        addInQueue(frontElement, vertex)
                    }
                }
            }
        }

        return paths.size
    }

    fun part1(inputs: List<String>): Int {
        val graph = UndirectedGraph()
        inputs.forEach { input ->
            val (source, destination) = input.split("-").map { it.trim() }
            // convert end->X to X->end and X->start to start->X
            if (source == "end" || destination == "start") {
                graph.addEdge(destination, source)
            } else {
                graph.addEdge(source, destination)
            }
        }

        // BFS  - find all paths
        return getTotalPathUsingBFS(source = "start", destination = "end", graph = graph)
    }

    fun part2(inputs: List<String>): Int {
        val graph = UndirectedGraph()
        inputs.forEach { input ->
            val (source, destination) = input.split("-").map { it.trim() }
            // convert end->X to X->end and X->start to start->X
            if (source == "end" || destination == "start") {
                graph.addEdge(destination, source)
            } else {
                graph.addEdge(source, destination)
            }
        }


        // BFS  - find all paths
        return getTotalPathUsingBFSPart2(source = "start", destination = "end", graph = graph)
    }

    val testInput = readInput("/day12/Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("/day12/Day12")
    check(part1(input) == 3463)
    check(part2(input) == 91533)
    println(part1(input))
    println(part2(input))
}
