/* COSC 445 Project 2: Binary Heap Data Structure
 * By Bobby Beall, Rose Davidson, Caroline Goyco, 
 * Caleb Jardeleza, and Thomas Zack.
 * We used https://goo.gl/Z7eKph to understand the binary heaps data
 * structure.
 */
package binaryheap;

import java.util.*;

public class BinaryHeap {

    /* generate_heap makes a binary heap with 4 levels of depth, 
     * populated with random values between 1 - 99.
     */
    public ArrayList<Integer> generate_heap(ArrayList<Integer> bin_heap) {
       bin_heap.clear();
        Random rand = new Random();
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);
        insert_element(bin_heap, rand.nextInt(99) + 1);

        return bin_heap;
    }   

    /* insert_element inserts an element into a binary heap.
     * the element begins at the bottom, then "bubbles" upwards.
     * if it is larger than its parent, it swaps with the parent.
     */
    public ArrayList<Integer> insert_element(ArrayList<Integer> array, int new_element) {
        array.add(new_element);

        //if there are at least 2 elements:
        if (array.size() > 1) {
            bubble_up(array, array.size() - 1);
        }
        return array;
    }

    /* bubble_up takes a newly inserted element of an array at
     * the specified position, and checks if it is larger than its 
     * parent. if so, it switches place with his parent.
     */
    public ArrayList<Integer> bubble_up(ArrayList<Integer> array, int position) {
        //if the position is an odd number:
        int parent = (position - (2 - (position % 2))) / 2;
        if (array.get(position) > array.get(parent)) {
            int bubbled_element = array.get(position);

            //switch elements
            array.set(position, array.get(parent));
            array.set(parent, bubbled_element);
            try {
                return bubble_up(array, parent);
            } catch (ArrayIndexOutOfBoundsException a) {
            }
        }
        return array;
    }

    /* get_depth will determine how many levels are in the binary heap. 
     * searches breadth first.
     */
    public int get_depth(ArrayList<Integer> array) {
        int q = 1;
        int count = 0;

        while (array.size() >= q) {
            q = q * 2;
            count++;
        }

        return count;
    }

    /* get_maximum returns the largest element in the binary heap. 
     * this is just the topmost element in the heap, found at index 0.
     */
    public int get_maximum(ArrayList<Integer> array) {
        return array.get(0);
    }

    /* get_minimum returns the smallest element in the binary heap.
     * this requires a search of the heap. since we know that the 
     * smallest element cannot have children, we start the search from 
     * the latter half of the array.
     */
    public int get_minimum(ArrayList<Integer> array) {
        int smallest_element;
        smallest_element = array.get((array.size() - (array.size() % 2)) / 2);

        for (int i = (array.size() - 1) / 2; i < array.size() - 1; i++) {
            if (array.get(i) < smallest_element) {
                smallest_element = array.get(i);
            }
        }
        return smallest_element;
    }


    /* delete_element deletes an element at a specific position from
     * the binary heap. the given element switches positions with the last
     * element in the array, then deletes the element. the switched element
     * then "trickles" downward, swapping with the larger of the two children.
     */
    public ArrayList<Integer> delete_element(ArrayList<Integer> array, int position) {
        int deleted_element = array.get(position);
        int replacement_element = array.get(array.size() - 1);

        /* "replaces" element. this really just changes value to last element. 
         * since last element will be removed anyways we do not need to set the 
         * value of last element.
         */
        array.set(position, replacement_element);
        array.remove(array.size() - 1);

        if (position < array.size() - 1) {
            trickle_down(array, position);
        }
        System.out.println(deleted_element + " has been removed.");
        return array;
    }


    /* trickle_down recursively checks kids and compares to see which child is larger.
     */
    public ArrayList<Integer> trickle_down(ArrayList<Integer> array, int position) {
        int trickled_element = array.get(position);
        int child_node = 0;
        int child_node_position = 0;

        //see if there are children eligible for comparison:
        if (array.size() > (position * 2) + 2) {
            child_node = (array.get((position * 2) + 1) > array.get((position * 2) + 2))
                    ? array.get((position * 2) + 1) : array.get((position * 2) + 2);
            child_node_position = (array.get((position * 2) + 1) > array.get((position * 2) + 2))
                    ? (position * 2) + 1 : (position * 2) + 2;

            //if not, switch with the only child node
        } else if (array.size() > (position * 2) + 1) {
            child_node = array.get((position * 2) + 1);
            child_node_position = array.get((position * 2) + 1);
        }
        //if the parent node weighs less than the child node:
        if (array.get(position) < child_node) {

            //switch elements
            array.set(position, child_node);
            array.set(child_node_position, trickled_element);
            try {
                return trickle_down(array, (position * 2) + 1);
            } catch (ArrayIndexOutOfBoundsException a) {
            }
        }
        return array;
    }

    public static void main(String[] args) {
        ArrayList<Integer> bin_heap = new ArrayList<Integer>();
        BinaryHeap bh = new BinaryHeap();

        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        long startTime = System.nanoTime();
        bh.generate_heap(bin_heap);
        long endTime = System.nanoTime();
        System.out.println("run-time of bin_heap creation: " + (endTime - startTime));

        startTime = System.nanoTime();
        System.out.println(bh.insert_element(bin_heap, 100));
        endTime = System.nanoTime();
        System.out.println("run-time of adding one element: " + (endTime - startTime));

        startTime = System.nanoTime();
        System.out.println("Maximum: " + bh.get_maximum(bin_heap));
        endTime = System.nanoTime();
        System.out.println("run-time of finding maximum: " + (endTime - startTime));

        startTime = System.nanoTime();
        System.out.println("Minimum: " + bh.get_minimum(bin_heap));
        endTime = System.nanoTime();
        System.out.println("run-time of finding minimum: " + (endTime - startTime));

        startTime = System.nanoTime();
        int element = rand.nextInt(bin_heap.size());
        System.out.println("Removing element at " + element);
        bh.delete_element(bin_heap, element);
        endTime = System.nanoTime();
        System.out.println(bin_heap);
        System.out.println("run-time of removing element: " + (endTime - startTime));
        
        System.out.println();
        System.out.println();

        bin_heap.clear();
        startTime = System.nanoTime();
        bh.generate_heap(bin_heap);
        endTime = System.nanoTime();
        System.out.println("run-time of bin_heap creation: " + (endTime - startTime));
        
        startTime = System.nanoTime();
        System.out.println(bh.insert_element(bin_heap, 100));
        endTime = System.nanoTime();
        System.out.println("run-time of adding one element: " + (endTime - startTime));
        
        startTime = System.nanoTime();
        System.out.println("Maximum: " + bh.get_maximum(bin_heap));
        endTime = System.nanoTime();
        System.out.println("run-time of finding maximum: " + (endTime - startTime));
        
        startTime = System.nanoTime();
        System.out.println("Minimum: " + bh.get_minimum(bin_heap));
        endTime = System.nanoTime();
        System.out.println("run-time of finding minimum: " + (endTime - startTime));
        
        startTime = System.nanoTime();
        element = rand.nextInt(bin_heap.size());
        System.out.println("Removing element at " + element);
        bh.delete_element(bin_heap, element);
        endTime = System.nanoTime();
        System.out.println(bin_heap);
        System.out.println("run-time of removing element: " + (endTime - startTime));

    }
}
