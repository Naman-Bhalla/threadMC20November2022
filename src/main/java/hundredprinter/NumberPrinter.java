package hundredprinter;

public class NumberPrinter implements Runnable {
    private int numberToPrint;

    public NumberPrinter(int numberToPrint) {
        this.numberToPrint = numberToPrint;
    }

    @Override
    public void run() {
        System.out.println(this.numberToPrint + " Printed By Thread: " + Thread.currentThread().getName());
    }
}
