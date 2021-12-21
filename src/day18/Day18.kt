package day18

import readInput
import java.lang.RuntimeException
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class Node(
    var value: Int?,
    var left: Node? = null,
    var right: Node? = null,
    var parent: Node? = null
){
    fun isPair() = this.value == null
    fun isReg() = this.value!=null
    fun toRegNode(value:Int){
        this.value = value
        this.left = null
        this.right = null
    }
    fun toPairNode(left:Node?, right: Node?){
        this.value = null
        this.left = left
        this.right = right
    }
}

class BinTree {

    private var root: Node? = null

    private fun explode():Boolean {
        var nestingLevel = 0
        var explodingNode: Node? = null
        val traversalList = mutableListOf<Node>()
        fun traverse(root: Node?) {
            if (root != null) {
                ++nestingLevel
                traverse(root.left)
                traverse(root.right)
                if (root.isReg()) traversalList.add(root)
                if (nestingLevel == 5 && root.isPair() && explodingNode == null) {
                    explodingNode = root
                }
                --nestingLevel
            }
        }
        traverse(root)
        if (explodingNode != null) { // explode
            val leftIndex = traversalList.indexOf(explodingNode!!.left)
            val rightIndex = leftIndex+1
            if (leftIndex - 1 >= 0) {
                val leftNode = traversalList[leftIndex - 1]
                leftNode.value = leftNode.value?.plus(explodingNode!!.left!!.value!!)
            }
            if (rightIndex + 1 < traversalList.size) {
                val rightNode = traversalList[rightIndex + 1]
                rightNode.value = rightNode.value?.plus(explodingNode!!.right!!.value!!)
            }
            explodingNode!!.toRegNode(0)
        }

        return explodingNode != null
    }

    fun create(sFNumber: String){
        fun create(): Node {
            val root = Node(null)
            val stack = Stack<Node>().also { it.push(root) }
            var index = 1
            while (index < sFNumber.length) {
                when {
                    sFNumber[index] == '[' -> {
                        val newNode = Node(null)
                        newNode.parent = stack.peek()
                        stack.peek().also { if (it.left == null) it.left = newNode else it.right = newNode }
                        stack.push(newNode)
                    }
                    sFNumber[index].isDigit() -> {
                        var endIndex = index
                        while (sFNumber[endIndex + 1].isDigit()) endIndex++
                        val number = sFNumber.substring(index, endIndex + 1).toInt()
                        stack.peek().also { sN ->
                            val newNode = Node(number).also { it.parent = sN }
                            if (sN.left == null) sN.left = newNode else sN.right = newNode
                        }
                        index = endIndex + 1
                        continue
                    }
                    sFNumber[index] == ']' -> stack.pop()
                }
                index++
            }
            return root
        }
        if (root == null) {
            root = create()
            return
        }
        // Add new-tree if a tree exists
        val newTreeRoot = create()
        root = Node(null).also {
            it.left = root
            it.right = newTreeRoot
        }
    }

    private fun split(): Boolean {
        var splitDone = false
        fun split(root: Node?) {
            if (root != null) {
                split(root.left)
                split(root.right)
                if (root.isReg() && root.value!! >= 10) {
                    splitDone = true
                    val regValue = root.value!!
                    val (leftVal, rightVal) = if (regValue % 2 == 0) listOf(regValue/2,regValue/2) else listOf(floor(regValue/2.0).toInt(), ceil(regValue/2.0).toInt())
                    root.toPairNode(Node(leftVal), Node(rightVal))
                    throw RuntimeException("Split done")
                }
            }
        }
        try { split(root) } catch (ex: RuntimeException) { }
        return splitDone
    }

    fun reduce() {
        while (true) {
            if (!explode()) { if (!split()) break }
        }
    }

    fun magnitude(): Int {
        fun magnitude(root: Node?) {
            if (root != null && root.isPair()) {
                magnitude(root.left)
                magnitude(root.right)
                val first = if (root.left != null && root.left!!.value != null) 3 * root.left!!.value!! else 0
                val second = if (root.right != null && root.right!!.value != null) 2 * root.right!!.value!! else 0
                root.toRegNode(value = first+second)
            }
        }
        magnitude(root)
        return root!!.value!!
    }
}

fun main() {
    fun part1(inputs: List<String>): Int {
        val binTree = BinTree()
        binTree.create(inputs[0])
        binTree.create(inputs[1])
        binTree.reduce()
        for (index in 2 until inputs.size) {
            binTree.create(inputs[index])
            binTree.reduce()
        }
        return binTree.magnitude()
    }

    fun part2(inputs: List<String>): Int {
        var max = Int.MIN_VALUE
        for (first in inputs.indices) {
            for (second in first + 1 until inputs.size) {
                var tree = BinTree().also {
                    it.create(inputs[first])
                    it.create(inputs[second])
                    it.reduce()
                }
                var magnitude = tree.magnitude()
                if (magnitude > max) max = magnitude

                tree = BinTree().also {
                    it.create(inputs[second])
                    it.create(inputs[first])
                    it.reduce()
                }
                magnitude = tree.magnitude()
                if (magnitude > max) max = magnitude
            }
        }
        return max
    }

    val testInput = readInput("/day18/Day18_test")
    check(part1(testInput) == 4140)
    check(part2(testInput) == 3993)

    val input = readInput("/day18/Day18")
    println(part1(input))
    println(part2(input))
}
