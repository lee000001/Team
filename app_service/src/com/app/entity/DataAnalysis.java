package com.app.entity;


public class DataAnalysis {
    private int state;
    private int value;
    private String label;

    public DataAnalysis(int state, int value) {
        this.state = state;

        this.value = value;
        switch (state)
        {
            case 0:
                label="δ��ʼ";
                break;
            case 1:
                label="������";
                break;
            case 2:
                label="�����";
                break;
            case 3:
                label="�ѹ���";
                break;
            default:
                label="δ֪";
                break;

        }

    }

    public DataAnalysis() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    
    @Override
    public String toString() {
        return "DataAnalysis{" +
                "state=" + state +
                ", value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}

