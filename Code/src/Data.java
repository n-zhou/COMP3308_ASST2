public class Data {

  public enum Class {yes, no}

  private double number_of_times_pregnant; //!< Number of times pregnant
  private double glucose; //!< Plasma glucose concentration a 2 hours in an oral glucose tolerance test
  private double blood_pressure; //!< Diastolic blood pressure (mm Hg)
  private double skin_thickness; //!< Triceps skin fold thickness (mm)
  private double serum; //!< 2-Hour serum insulin (mu U/ml)
  private double body_mass_index; //!< Body mass index (weight in kg/(height in m)^2)
  private double pedigree_function; //!< Diabetes pedigree function
  private double age; //!< Age (years)
  private Class _class; //!< Class variable ("yes" or "no")

  public Data() {

  }

}
