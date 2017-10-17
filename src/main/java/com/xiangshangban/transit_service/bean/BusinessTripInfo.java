package com.xiangshangban.transit_service.bean;

/**
 * Created by liuguanglong on 2017/8/28.
 */

public class BusinessTripInfo {

    private String proposerId;          //出差人ID（当前登录用户的ID）
    private String proposerName;        //出差人姓名
    private String processInstanceId;   //流程实例ID
    private String firstAssigneeId;     //一级审批人ID（字符串拼接多个审批人）
    private String secondAssigneeId;    //二级审批人ID
    private String thirdAssigneeId;     //三级审批人ID
    private String fourthAssigneeId;    //四级审批人ID
    private String fifthAssigneeId;     //五级审批人ID
    private String startTime;           //出差起始时间
    private String endTime;             //出差结束时间
    private String effectiveTimeLength; //出差有效时长
    private String tripDestination;     //出差地点
    private String unit;                //出差的计算单位
    private String judge;               //审批级数
    private String reason;              //出差原因
    private String applicationStatus;   //申请状态，0：待处理，1：处理中，2：已通过，3：已驳回，4：已撤回，5：已抛弃
    private String applicationType;     //申请类型，0：加班，1：请假，2：出差，3：外出
    private String createTime;          //流程创建时间

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getFirstAssigneeId() {
        return firstAssigneeId;
    }

    public void setFirstAssigneeId(String firstAssigneeId) {
        this.firstAssigneeId = firstAssigneeId;
    }

    public String getSecondAssigneeId() {
        return secondAssigneeId;
    }

    public void setSecondAssigneeId(String secondAssigneeId) {
        this.secondAssigneeId = secondAssigneeId;
    }

    public String getThirdAssigneeId() {
        return thirdAssigneeId;
    }

    public void setThirdAssigneeId(String thirdAssigneeId) {
        this.thirdAssigneeId = thirdAssigneeId;
    }

    public String getFourthAssigneeId() {
        return fourthAssigneeId;
    }

    public void setFourthAssigneeId(String fourthAssigneeId) {
        this.fourthAssigneeId = fourthAssigneeId;
    }

    public String getFifthAssigneeId() {
        return fifthAssigneeId;
    }

    public void setFifthAssigneeId(String fifthAssigneeId) {
        this.fifthAssigneeId = fifthAssigneeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEffectiveTimeLength() {
        return effectiveTimeLength;
    }

    public void setEffectiveTimeLength(String effectiveTimeLength) {
        this.effectiveTimeLength = effectiveTimeLength;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
