package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/26.
 */
public class PearVideoInfoHomeAreaExpInfo implements Serializable {

    private String algorighm_exp_id;
    private String front_exp_id;
    private String s_value;

    public String getAlgorighm_exp_id() {
        return algorighm_exp_id;
    }

    public void setAlgorighm_exp_id(String algorighm_exp_id) {
        this.algorighm_exp_id = algorighm_exp_id;
    }

    public String getFront_exp_id() {
        return front_exp_id;
    }

    public void setFront_exp_id(String front_exp_id) {
        this.front_exp_id = front_exp_id;
    }

    public String getS_value() {
        return s_value;
    }

    public void setS_value(String s_value) {
        this.s_value = s_value;
    }
}
