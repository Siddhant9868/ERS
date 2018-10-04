package singh.siddhant.project.ers;

public class Contact {


    public Long getQTY() {
        return QTY;
    }

    public void setQTY(Long QTY) {
        this.QTY = QTY;
    }

    public Long getT_ID() {
        return T_ID;
    }

    public void setT_ID(Long t_ID) {
        T_ID = t_ID;
    }

    public String getS_ID() {
        return S_ID;
    }

    public void setS_ID(String s_ID) {
        S_ID = s_ID;
    }

    public Long getBILL_ID() {
        return BILL_ID;
    }

    public void setBILL_ID(Long BILL_ID) {
        this.BILL_ID = BILL_ID;
    }

    public Float getRATE() {
        return RATE;
    }

    public void setRATE(Float RATE) {
        this.RATE = RATE;
    }

    public Long getTAX() {
        return TAX;
    }

    public void setTAX(Long TAX) {
        this.TAX = TAX;
    }

    public String getDOR() {
        return DOR;
    }

    public void setDOR(String DOR) {
        this.DOR = DOR;
    }

    public String getE_ID() {
        return E_ID;
    }

    public void setE_ID(String e_ID) {
        E_ID = e_ID;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    private Long QTY;
    private Long T_ID;
    private String S_ID;
    private Long BILL_ID;
    private Float RATE;
    private Long TAX;
    private String DOR;
    private String E_ID;
    private String ITEM_ID;
    private String location_circle;

    public String getFORMATED_DOR() {
        return FORMATED_DOR;
    }

    public void setFORMATED_DOR(String FORMATED_DOR) {
        this.FORMATED_DOR = FORMATED_DOR;
    }

    private String FORMATED_DOR;

    public String getLocation_circle() {
        return location_circle;
    }

    public void setLocation_circle(String location_circle) {
        this.location_circle = location_circle;
    }








}

