package multithreadedmergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Sorter implements Callable<List<Integer>> {
    private List<Integer> arrayToSort;
    private ExecutorService executorService;

    public Sorter(List<Integer> arrayToSort, ExecutorService executorService) {
        this.arrayToSort = arrayToSort;
        this.executorService = executorService;
    }

    @Override
    public List<Integer> call() throws Exception {
        if (this.arrayToSort.size() <= 1) {
            return this.arrayToSort;
        }

        List<Integer> leftArrayToSort = new ArrayList<>();
        for (int i = 0; i < arrayToSort.size()/ 2; ++i) {
            leftArrayToSort.add(arrayToSort.get(i));
        }

        List<Integer> rightArrayToSort = new ArrayList<>();
        for (int i = arrayToSort.size()/ 2; i < arrayToSort.size(); ++i) {
            rightArrayToSort.add(arrayToSort.get(i));
        }

        Future<List<Integer>> leftSortedArrayFuture = executorService.submit(new Sorter(leftArrayToSort, executorService));

        Future<List<Integer>> rightSortedArrayFuture = executorService.submit(new Sorter(rightArrayToSort, executorService));

        List<Integer> leftSortedArray = leftSortedArrayFuture.get();
        List<Integer> rightSortedArray = rightSortedArrayFuture.get();

        List<Integer> sortedArray = new ArrayList<>();

        // [ 1, 3, 4, 5, 7]|
        // [ 2, 5, 6, |8]

        // [1, 2, 3, 4, 5, 5, 6, 7, 8]

        int i = 0;
        int j = 0;

        while (i < leftSortedArray.size() && j < rightSortedArray.size()) {
            if (leftSortedArray.get(i) <= rightSortedArray.get(j)) {
                sortedArray.add(leftSortedArray.get(i));
                i++;
            } else {
                sortedArray.add(rightSortedArray.get(j));
                j++;
            }
        }

        while (i < leftSortedArray.size()) {
            sortedArray.add(leftSortedArray.get(i));
            i++;
        }

        while (j < rightSortedArray.size()) {
            sortedArray.add(rightSortedArray.get(j));
            j++;
        }

        return sortedArray;
    }
}
