package com.omottec.demoapp.utils;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by gaozhen on 2015/6/19.
 */
public class MathUtil {

    /**
     * 两个点之间的距离
     */
    public static float distance4PointF(PointF pf1, PointF pf2) {
        float disX = pf2.x - pf1.x;
        float disY = pf2.y - pf1.y;
        return (float)Math.sqrt(disX * disX + disY * disY);
    }


    /**
     * 弧度换算成角度
     *
     * @return
     */
    public static double radianToDegree(double radian) {
        return radian * 180 / Math.PI;
    }


    /**
     * 角度换算成弧度
     * @param degree
     * @return
     */
    public static double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    /**
     * 判断旋转方向
     * @param prePoint 前一个触控点坐标
     * @param curPoint 当前触控点坐标
     * @param centerPoint 中心坐标
     * @return 结果大于0标示顺时针，小于0标示逆时针
     */
    public static float getDirection(PointF prePoint,PointF curPoint,PointF centerPoint) {
        //center -> proMove的向量， 我们使用PointF来实现
        PointF centerToProMove = new PointF((prePoint.x - centerPoint.x), (prePoint.y - centerPoint.y));

        //center -> curMove 的向量
        PointF centerToCurMove = new PointF((curPoint.x - centerPoint.x), (curPoint.y - centerPoint.y));

        //向量叉乘结果, 如果结果为负数， 表示为逆时针， 结果为正数表示顺时针
        return centerToProMove.x * centerToCurMove.y - centerToProMove.y * centerToCurMove.x;
    }

    /**
     *计算旋转角度
     * @param prePoint 前一个触控点坐标
     * @param curPoint 当前触控点坐标
     * @param centerPoint 中心坐标
     * @return
     */
    public static float getRotation(PointF prePoint,PointF curPoint,PointF centerPoint) {
        // 角度
        double a = MathUtil.distance4PointF(centerPoint, prePoint);
        double b = MathUtil.distance4PointF(prePoint, curPoint);
        double c = MathUtil.distance4PointF(centerPoint, curPoint);
        double cosb = (a * a + c * c - b * b) / (2 * a * c);
        if (cosb >= 1) {
            cosb = 1f;
        }
        double radian = Math.acos(cosb);
        float newDegree = (float) MathUtil.radianToDegree(radian);
        return newDegree;
    }

    /**
     *  计算缩放比例
     * @param curPoint 当前触控点
     * @param centerPoint 图片中心点
     * @param width 图片宽度
     * @param height 图片高度
     * @return
     */
    public static float getScale(PointF curPoint,PointF centerPoint,int width,int height) {
        //对角线长度
        float diagonal = (float)Math.sqrt(width * width + height * height);

        //移动的点到图片中心的距离
        float moveToCenterDistance = MathUtil.distance4PointF(centerPoint, curPoint);

        //计算缩放比例
        float scale = moveToCenterDistance*2 / diagonal;
        return scale;
    }
    /**
     * 获取旋转某个角度之后的点
     * @param center
     * @param source
     * @param degree
     * @return
     */
    public static Point obtainRoationPoint(Point center, Point source, float degree) {
        //两者之间的距离
        Point disPoint = new Point();
        disPoint.x = source.x - center.x;
        disPoint.y = source.y - center.y;

        //没旋转之前的弧度
        double originRadian = 0;

        //没旋转之前的角度
        double originDegree = 0;

        //旋转之后的角度
        double resultDegree = 0;

        //旋转之后的弧度
        double resultRadian = 0;

        //经过旋转之后点的坐标
        Point resultPoint = new Point();

        double distance = Math.sqrt(disPoint.x * disPoint.x + disPoint.y * disPoint.y);
        if (disPoint.x == 0 && disPoint.y == 0) {
            return center;
            // 第一象限
        } else if (disPoint.x >= 0 && disPoint.y >= 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(disPoint.y / distance);

            // 第二象限
        } else if (disPoint.x < 0 && disPoint.y >= 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(Math.abs(disPoint.x) / distance);
            originRadian = originRadian + Math.PI / 2;

            // 第三象限
        } else if (disPoint.x < 0 && disPoint.y < 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(Math.abs(disPoint.y) / distance);
            originRadian = originRadian + Math.PI;
        } else if (disPoint.x >= 0 && disPoint.y < 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(disPoint.x / distance);
            originRadian = originRadian + Math.PI * 3 / 2;
        }

        // 弧度换算成角度
        originDegree = MathUtil.radianToDegree(originRadian);
        resultDegree = originDegree + degree;

        // 角度转弧度
        resultRadian = MathUtil.degreeToRadian(resultDegree);

        resultPoint.x = (int) Math.round(distance * Math.cos(resultRadian));
        resultPoint.y = (int) Math.round(distance * Math.sin(resultRadian));
        resultPoint.x += center.x;
        resultPoint.y += center.y;

        return resultPoint;
    }


    /**
     * 获取变长参数最大的值
     * @param array
     * @return
     */
    public static int getMaxValue(Integer...array){
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(list.size() -1);
    }


    /**
     * 获取变长参数最大的值
     * @param array
     * @return
     */
    public static int getMinValue(Integer...array){
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(0);
    }

}
