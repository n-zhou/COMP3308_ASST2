public class Data {

    private double[] values;
    private boolean yes;

    public Data(double[] values, boolean yes) {
        this.values = values;
        this.yes = yes;
    }

    public Data(double[] values) {
        this.values = values;
    }

    public double[] getValues() {
        return values;
    }

    public double getValue(int index) {
        return values[index];
    }

    public boolean class_() {
        return yes;
    }

}
