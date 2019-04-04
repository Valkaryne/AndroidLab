package com.epam.valkaryne.utils

import java.util.EmptyStackException

class CustomStack<E> {

    companion object {
        /**
         * Assuming maximal size of the stack.
         */
        private const val MAX_STACK_SIZE = Int.MAX_VALUE / 2
    }

    /**
     * The array contains items of the stack.
     */
    var itemsData: Array<Any?> = arrayOfNulls(10)

    /**
     * The number of items in the stack.
     */
    var itemsCount = 0

    override fun toString(): String {
        val it = iterator()
        if (!it.hasNext())
            return "[]"

        val sb = StringBuilder()
        sb.append('[')
        while (true) {
            val e = it.next()
            sb.append(if (e == this) "(this Collection)" else e)
            if (!it.hasNext())
                return sb.append(']').toString()
            sb.append(',').append(' ')
        }
    }

    /**
     * Inserts an item at the top of the stack.
     * @param item the item to be inserted at the top of the stack.
     * @return the item argument.
     */
    fun push(item: E): E {
        checkCapacity(itemsCount + 1)
        itemsData[itemsCount++] = item

        return item
    }

    /**
     * Returns the top item after removing from the stack.
     * @return the top of the stack.
     * @throws EmptyStackException if the stack is empty
     */
    fun pop(): E {
        val item = top()
        itemsData[itemsCount--] = null

        return item
    }

    /**
     * Returns the top item without removing from the stack.
     * @return the top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    fun top(): E {
        if (isEmpty()) throw EmptyStackException()

        return itemsData[itemsCount - 1] as E
    }

    /**
     * Checks if the stack is empty.
     * @return true if and only if the stack contains no items, false otherwise.
     */
    fun isEmpty() = itemsCount == 0

    /**
     * Checks if we need to increase capacity of the stack.
     * @param capacity the minimal desired capacity.
     */
    private fun checkCapacity(capacity: Int) {
        if (capacity - itemsData.size > 0)
            enlarge(capacity)
    }

    /**
     * Enlarges the stack if we filled it.
     * @param capacity the minimal desired capacity
     */
    private fun enlarge(capacity: Int) {
        val oldCapacity = itemsData.size
        var newCapacity = (1.5 * oldCapacity + 1).toInt()
        if (newCapacity - capacity < 0)
            newCapacity = capacity
        if (newCapacity - MAX_STACK_SIZE > 0)
            newCapacity = MAX_STACK_SIZE
        itemsData = itemsData.copyOf(newCapacity)
    }

    /**
     * Returns iterator for the stack.
     * @return iterator for the stack.
     */
    private fun iterator() : Iterator<E> {
        return StackIterator()
    }

    inner class StackIterator<E> : Iterator<E> {
        private var cursor = 0      // index of the next item to return
        private var lastRet = -1    // index of last item returned; -1 if no such

        override fun hasNext(): Boolean = cursor != itemsCount

        override fun next(): E {
            val i = cursor
            if (i >= itemsCount)
                throw NoSuchElementException()
            cursor = i + 1
            lastRet = i
            return itemsData[lastRet] as E
        }
    }
}