package com.zhph.commonlibrary.utils.app;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/9.
 */

public class TimeLineNodesUtil {
    public static ArrayList<String> getTimeLineNodes(int nodeId) {
        ArrayList<String> timeLineNodes = new ArrayList<String>();
        String[] nodes = new String[0];
        switch (nodeId) {
            //正常流程
            case 0:
            case 1:
            case 2:
            case 6:
            case 7:
            case 8:
            case 9:
                nodes = new String[]{"新建", "待确认", "待审核", "已审核", "已签约", "还款中", "已结清"};
                break;
            //驳回流程
            case 4:
                nodes = new String[]{"新建", "待确认", "待审核", "已驳回", "待审核", "已审核", "已签约", "还款中", "已结清"};
                break;
            //拒绝流程
            case 5:
            case 10:
            case 11:
            case 12:
                nodes = new String[]{"已拒绝"};
                break;
            //撤销流程
            case 13:
            case 3:
                nodes = new String[]{"已撤销"};
                break;
        }
        for (String item : nodes) {
            timeLineNodes.add(item);
        }
        return timeLineNodes;
    }


    public static String getNodeName(int nodeId) {
        String nadeName = "";
        switch (nodeId) {
            case 0:
                nadeName = "新建";
                break;
            case 1:
                nadeName = "待确认";
                break;
            case 2:
                nadeName = "待审核";
                break;
            case 3:
                nadeName = "已撤销";
                break;
            case 4:
                nadeName = "已驳回";
                break;
            case 5:
                nadeName = "已拒绝";
                break;
            case 6:
                nadeName = "已审核";
                break;
            case 7:
                nadeName = "已签约";
                break;
            case 8:
                nadeName = "还款中";
                break;
            case 9:
                nadeName = "已结清";
                break;
            case 10:
                nadeName = "已拒绝";
                break;
            case 11:
                nadeName = "已拒绝";
                break;
            case 12:
                nadeName = "已拒绝";
                break;
            case 13:
                nadeName = "已撤销";
                break;

        }
        return nadeName;
    }

    /**
     * 根据状态返回不同的节点树
     *
     * @param isReject 是否被驳回过
     * @return 节点树数组
     */
    public static Integer[] getNodes(boolean isReject) {
        if (isReject) {
            return new Integer[]{0, 1, 2, 4, 2, 6, 7, 8, 9};//驳回过节点树
        } else {
            return new Integer[]{0, 1, 2, 6, 7, 8, 9};//正常节点树
        }
    }

}
