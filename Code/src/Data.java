public class Data {

    private double[] values;
    private boolean yes;

    /**
     * The Constructor makes and object with these given values.
     * @param values a double array which contains the data.
     * @param yes    the class outcome.
     */
    public Data(double[] values, boolean yes) {
        this.values = values;
        this.yes = yes;
    }

    /**
     * This overloaded constuctor is not used.
     * @Deprecated
     * @param values a double array which contains the data.
     */
    public Data(double[] values) {
        this.values = values;
    }

    /**
     * @Deprecated
     * @return returns the array containing the values of the attributes stored.
     */
    public double[] getValues() {
        return values;
    }

    /**
     * retrives the value of an attribute at a given index.
     * @param  index The index of the attribute which we are trying to access.
     * The valid indexes are in the range [0,1].
     * @return       returns the value at the given index.
     */
    public double getValue(int index) {
        return values[index];
    }

    /**
     * This method returns true or false if the class is yes or no respectively.
     * @return false if the class in no, true if the class is yes.
     */
    public boolean class_() {
        return yes;
    }

}
