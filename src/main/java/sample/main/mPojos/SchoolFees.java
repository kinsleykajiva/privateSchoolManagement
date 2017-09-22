package sample.main.mPojos;

/**
 * This defines the fees object that will be created.<br/>
 *
 */
public final  class SchoolFees {
    private String feeName;
    private String feeAmoount;
    private String whoPays;
    private String feesDatePeriod;
    private String feesPeriodName;
    private String feesDescription;
    private String feesAccountNoumber;
    private String bank;
    private boolean isSelected;
    private String DateCreated;

    private String feespaidBy;
    private String feesPayedOnDate;
    private String fromWhoseAccount;
    private String paidAmount;
    private String studentPaying;
    private String paymentMethod;

    /**
     *  Defining the Create Fee object or class to be used
     * @param feeName   full actual name  of the Fee
     * @param feeAmoount    Amount expected to be paid
     * @param bank          Bank name this fees payment is to be done on or into
     * @param whoPays   Students or stack holders who are to pay this fee
     * @param feesDatePeriod    The date period of which the fees is to be paid in full
     * @param feesPeriodName    The period the fees to be paid say a term 1 or term 2
     * @param feesAccountNoumber The account number where the fees needs to get paid inot
     * @param feesDescription   What the fee means or just a description
     * @param isSelected    This is for home keeping or the table definition properties
     * @param dateCreated   When this fees was created
     */
    public SchoolFees (String feeName, String feeAmoount, String bank, String whoPays, String feesDatePeriod, String feesPeriodName, String feesAccountNoumber, String feesDescription,String dateCreated, boolean isSelected) {
        this.feeName = feeName;
        this.feeAmoount = feeAmoount;
        this.whoPays = whoPays;
        this.feesDatePeriod = feesDatePeriod;
        this.feesPeriodName = feesPeriodName;
        this.isSelected = isSelected;
        this.feesAccountNoumber = feesAccountNoumber;
        this.feesDescription = feesDescription;
        this.bank = bank;
        this.isSelected = isSelected;
        DateCreated = dateCreated;
    }

    /**
     * The paying Fees Object Class
     * @param feeName   full name of the Fee
     * @param feesPeriodName    The period the fees to be paid say a term 1 or term 2
     * @param bank          Bank name this fees payment is to be done on or into
     * @param feespaidBy    Who paid the Fees maybe its a parent, Company ,or Donar
     * @param feesPayedOnDate   Date of payment
     * @param fromWhoseAccount  Paying Account if cash then will default to a cash account
     * @param paidAmount    Amount paid
     * @param studentPaying Which student is making this payment
     * @param paymentMethod Type of payment if its cash or mobile transfer
     * @param isSelected    This is for home keeping or the table definition properties
     * @param dateCreated       When this fees was created
     */
    public SchoolFees (String feeName, String feesPeriodName, String bank, String feespaidBy, String feesPayedOnDate, String fromWhoseAccount, String paidAmount, String studentPaying, String paymentMethod, String dateCreated, boolean isSelected) {
        this.feeName = feeName;
        this.feesPeriodName = feesPeriodName;
        this.bank = bank;
        this.isSelected = isSelected;
        this.feespaidBy = feespaidBy;
        this.feesPayedOnDate = feesPayedOnDate;
        this.fromWhoseAccount = fromWhoseAccount;
        this.paidAmount = paidAmount;
        this.studentPaying = studentPaying;
        this.paymentMethod = paymentMethod;
        DateCreated = dateCreated;
    }

    public String getDateCreated () {
        return DateCreated;
    }

    public void setDateCreated (String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getBank () {
        return bank;
    }

    public void setBank (String bank) {
        this.bank = bank;
    }

    public String getFeespaidBy () {
        return feespaidBy;
    }

    public void setFeespaidBy (String feespaidBy) {
        this.feespaidBy = feespaidBy;
    }

    public String getFeesPayedOnDate () {
        return feesPayedOnDate;
    }

    public void setFeesPayedOnDate (String feesPayedOnDate) {
        this.feesPayedOnDate = feesPayedOnDate;
    }

    public String getFromWhoseAccount () {
        return fromWhoseAccount;
    }

    public void setFromWhoseAccount (String fromWhoseAccount) {
        this.fromWhoseAccount = fromWhoseAccount;
    }

    public String getPaidAmount () {
        return paidAmount;
    }

    public void setPaidAmount (String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getStudentPaying () {
        return studentPaying;
    }

    public void setStudentPaying (String studentPaying) {
        this.studentPaying = studentPaying;
    }

    public String getPaymentMethod () {
        return paymentMethod;
    }

    public void setPaymentMethod (String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFeesAccountNoumber () {
        return feesAccountNoumber;
    }

    public void setFeesAccountNoumber (String feesAccountNoumber) {
        this.feesAccountNoumber = feesAccountNoumber;
    }

    public String getFeesDescription () {
        return feesDescription;
    }

    public void setFeesDescription (String feesDescription) {
        this.feesDescription = feesDescription;
    }

    public String getFeeName () {
        return feeName;
    }

    public void setFeeName (String feeName) {
        this.feeName = feeName;
    }

    public String getFeeAmoount () {
        return feeAmoount;
    }

    public void setFeeAmoount (String feeAmoount) {
        this.feeAmoount = feeAmoount;
    }

    public String getWhoPays () {
        return whoPays;
    }

    public void setWhoPays (String whoPays) {
        this.whoPays = whoPays;
    }

    public String getFeesDatePeriod () {
        return feesDatePeriod;
    }

    public void setFeesDatePeriod (String feesDatePeriod) {
        this.feesDatePeriod = feesDatePeriod;
    }

    public String getFeesPeriodName () {
        return feesPeriodName;
    }

    public void setFeesPeriodName (String feesPeriodName) {
        this.feesPeriodName = feesPeriodName;
    }

    public boolean isSelected () {
        return isSelected;
    }

    public void setSelected (boolean selected) {
        isSelected = selected;
    }
}
