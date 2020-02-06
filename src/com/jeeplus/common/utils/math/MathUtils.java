package com.jeeplus.common.utils.math;

import java.math.BigDecimal;

public class MathUtils {
    /**
     * 此类保存的是数据计算的方法,方便调用
     *
     * 1,在将任何数字转化为bigdecimal的时候使用bigdecimal的string  构造器,否则计算后会产生精度丢失
     */

    /**
     * 对double数据进行取精度.
     * @param value  double数据.
     * @param scale  精度位数(保留的小数位数).
     * @param roundingMode  精度取值方式.
     * @return 精度计算后的数据.
     */
    public  static double round(double value, int scale,
                               int roundingMode) {
        if (scale>0){
            throw new IllegalArgumentException(
                    "精度必须为>=0的整数!");
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }


    /**
     * double 相加
     * @param d1
     * @param d2
     * @return
     */
    public  static double sum(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }


    /**
     * double 相减
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }


    /**
     * double 除法
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public  static double div(double d1,double d2,int scale){
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if (scale<0){
            throw new IllegalArgumentException(
                    "精度必须为>=0的整数!");
        }
        if (d2==0){
            throw new IllegalArgumentException(
                    "分母不能为0!");
        }
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide
                (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static void main(String[] args) {

        MathUtils math=new MathUtils();
        System.out.println(11111111);

        System.out.println(math.div(1,0,3));
    }




}
